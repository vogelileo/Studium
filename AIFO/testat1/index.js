/**
 * Main Express server setup for the voice-enabled warehouse picking system.
 * Handles API endpoints for workflow management and text-to-speech functionality.
 */

import express from 'express';
import bodyParser from 'body-parser';
import cors from 'cors';
import 'dotenv/config';

import { WorkflowManager } from './workflow_manager.js';
import { Runner } from '@openai/agents';
import generate from '@babel/generator';
import { generateVoice } from './agents/voice_agent.js';

const app = express();
app.use(cors());
app.use(bodyParser.json());

// Shared Runner instance to handle all agent operations efficiently
const sharedRunner = new Runner({
  traceMetadata: { __trace_source__: 'voice-picker-server' },
});

// Maintains the active workflow state throughout the session
let currentWorkflowManager = null;

/**
 * API endpoint to initialize a new picking workflow.
 * @route POST /api/start-workflow
 * @param {object} req.body.input_as_text - JSON string containing the list of items to pick
 * @returns {object} Initial workflow state
 */
app.post('/api/start-workflow', async (req, res) => {
  try {
    const workflowInput = req.body.input_as_text;

    if (!workflowInput) {
      return res.status(400).json({ error: 'Missing input_as_text' });
    }

    // Initialize WorkflowManager with the REAL sharedRunner
    currentWorkflowManager = new WorkflowManager(workflowInput, sharedRunner);

    const state = await currentWorkflowManager.start();
    res.json(state);
  } catch (error) {
    console.error('Start workflow error:', error);
    res
      .status(500)
      .json({ error: 'Failed to start workflow', details: error.message });
  }
});

/**
 * API endpoint to process voice commands and advance the workflow.
 * @route POST /api/continue-workflow
 * @param {object} req.body.user_input - Raw transcribed voice command
 * @returns {object} Updated workflow state after processing the command
 */
app.post('/api/continue-workflow', async (req, res) => {
  if (!currentWorkflowManager) {
    return res
      .status(400)
      .json({ error: 'Workflow not started. Call /start-workflow first.' });
  }

  try {
    // *** KEY CHANGE: Read the raw transcribed text ***
    const rawUserInput = req.body.user_input;

    if (!rawUserInput) {
      return res.status(400).json({
        error: 'User input (transcribed text) is required in the request body.',
      });
    }

    // Pass the raw text to the WorkflowManager.
    // The manager now handles the Agent/LLM call to translate this into an action.
    const state = await currentWorkflowManager.continue(rawUserInput);
    res.json(state);
  } catch (error) {
    console.error('Continue workflow error:', error);
    res
      .status(500)
      .json({ error: 'Failed to continue workflow', details: error.message });
  }
});

app.post('/api/generate-voice', async (req, res) => {
  const { prompt } = req.body;
  if (!prompt) {
    return res.status(400).json({ error: 'Missing prompt in request body' });
  }
  let audioBuffer = await generateVoice(prompt);

  res.setHeader('Content-Type', 'audio/mpeg');
  res.setHeader('Content-Disposition', 'inline; filename="speec_output.mp3"');

  // 5. Stream the Buffer to the client
  res.send(audioBuffer);
});

// Export the app instance for supertest
export default app;

// --- Server Startup ---

const PORT = 3001;
app.listen(PORT, () => {
  console.log(`Server running on http://localhost:${PORT}`);
});
