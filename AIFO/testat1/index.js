// index.js (Express server)

import express from 'express';
import bodyParser from 'body-parser';
import cors from 'cors';
import 'dotenv/config';

import { WorkflowManager } from './workflow_manager.js';
import { Runner } from '@openai/agents';

const app = express();
app.use(cors());
app.use(bodyParser.json());

// A single real Runner instance for the entire application to reuse.
const sharedRunner = new Runner({
  // Configure your trace metadata here if needed
  traceMetadata: { __trace_source__: 'voice-picker-server' },
});

// Global storage for the current workflow instance.
let currentWorkflowManager = null;

// --- API Endpoints ---

/**
 * POST /api/start-workflow
 * Initializes a new picking workflow.
 * Expected body: { "input_as_text": "list of items to pick..." }
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
 * POST /api/continue-workflow
 * Accepts the raw voice transcription and uses the Action Processor Agent
 * to translate it into a structured action for the WorkflowManager.
 * Expected body: { "user_input": "raw transcribed text, e.g., 'skip this one'" }
 * * NOTE: The body parameter name is changed from 'action' to 'user_input' to reflect
 * that it is raw text, not a pre-defined action code.
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

// Export the app instance for supertest
export default app;

// --- Server Startup ---

const PORT = 3001;
app.listen(PORT, () => {
  console.log(`Server running on http://localhost:${PORT}`);
});

// const input =
//   '["[ { \\"name\\": \\"Item A\\", \\"hallway\\": \\"H1\\", \\"shelf\\": \\"S3\\" }, { \\"name\\": \\"Item B\\", \\"hallway\\": \\"H2\\", \\"shelf\\": \\"S1\\" }, { \\"name\\": \\"Item C\\", \\"hallway\\": \\"H1\\", \\"shelf\\": \\"S5\\" } ]"]';

// let state = '';

// currentWorkflowManager = new WorkflowManager(input, sharedRunner);

// await currentWorkflowManager.start();

// state = await currentWorkflowManager.continue('APPROVE');

// while (state.status !== 'COMPLETED') {
//   state = await currentWorkflowManager.continue('CONFIRM_PICK');

//   console.log('Items left:', state.sorted_items.length);

//   console.log('Current item:', state.currentItem);
// }
