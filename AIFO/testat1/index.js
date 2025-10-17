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

const sharedRunner = new Runner({
  traceMetadata: { __trace_source__: 'voice-picker-server' },
});

let currentWorkflowManager = null;

/**
 * Handles starting a new picking workflow from provided text input.
 * @param {import('express').Request} req - Express request containing input_as_text in the body
 * @param {import('express').Response} res - Express response used to return workflow state or errors
 * @returns {Promise<void>} Sends JSON response with initial workflow state
 */
app.post('/api/start-workflow', async (req, res) => {
  try {
    const workflowInput = req.body.input_as_text;

    if (!workflowInput) {
      return res.status(400).json({ error: 'Missing input_as_text' });
    }

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
 * Processes a transcribed voice command to advance the active workflow.
 * @param {import('express').Request} req - Express request containing user_input text in the body
 * @param {import('express').Response} res - Express response used to return updated workflow state
 * @returns {Promise<void>} Sends JSON response with the updated workflow state
 */
app.post('/api/continue-workflow', async (req, res) => {
  if (!currentWorkflowManager) {
    return res
      .status(400)
      .json({ error: 'Workflow not started. Call /start-workflow first.' });
  }

  try {
    const rawUserInput = req.body.user_input;

    if (!rawUserInput) {
      return res.status(400).json({
        error: 'User input (transcribed text) is required in the request body.',
      });
    }

    const state = await currentWorkflowManager.continue(rawUserInput);
    res.json(state);
  } catch (error) {
    console.error('Continue workflow error:', error);
    res
      .status(500)
      .json({ error: 'Failed to continue workflow', details: error.message });
  }
});

/**
 * Generates an MP3 audio stream from a provided text prompt.
 * @param {import('express').Request} req - Express request containing the prompt text in the body
 * @param {import('express').Response} res - Express response used to return the generated MP3 audio
 * @returns {Promise<void>} Sends an MP3 audio buffer in the response
 */
app.post('/api/generate-voice', async (req, res) => {
  const { prompt } = req.body;
  if (!prompt) {
    return res.status(400).json({ error: 'Missing prompt in request body' });
  }
  let audioBuffer = await generateVoice(prompt);

  res.setHeader('Content-Type', 'audio/mpeg');
  res.setHeader('Content-Disposition', 'inline; filename="speec_output.mp3"');

  res.send(audioBuffer);
});

export default app;

const PORT = 3001;
/**
 * Logs a message indicating the server is listening on the configured port.
 * @returns {void} No return value
 */
app.listen(PORT, () => {
  console.log(`Server running on http://localhost:${PORT}`);
});
