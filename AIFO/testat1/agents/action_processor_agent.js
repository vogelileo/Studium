import { Agent } from '@openai/agents';

// --- 1. Define the Desired JSON Output Schema (Response Schema) ---
// This schema enforces the exact structure and types the agent MUST output.
const ProcessActionOutputSchema = {
  type: 'object',
  properties: {
    action: {
      type: 'string',
      description:
        "The standardized internal command derived from the user's input.",
      enum: [
        'APPROVE', // Initial flow control (e.g., "yes", "start now")
        'DENY', // Initial flow control (e.g., "no", "stop")
        'CONFIRM_PICK', // Confirm item pick (e.g., "done", "got it")
        'SKIP_ITEM', // Skip item (e.g., "skip it", "move on")
        'QUANTITY_CHECK', // Check quantity (e.g., "I see three")
        'READ_AGAIN', // Repeat prompt (e.g., "repeat", "what was that")
        'WRONG_LOCATION', // Report wrong location (e.g., "wrong spot")
      ],
    },
    payload: {
      type: 'object',
      description:
        "Optional data extracted from the user's input, relevant to the action.",
      properties: {
        quantity: {
          type: 'integer',
          description:
            'The numerical quantity mentioned by the user (only for QUANTITY_CHECK).',
        },
        location_code: {
          type: 'string',
          description:
            'The location code or bin ID mentioned by the user (only for WRONG_LOCATION).',
        },
      },
      // Allows the model to use an empty object {} if no payload data is needed
      additionalProperties: true,
    },
  },
  required: ['action'],
  additionalProperties: false,
};

// --- 2. Agent Instructions ---
const actionProcessorInstructions = (runContext, _agent) => {
  // The current state is passed from the WorkflowManager in the context
  const { current_state } = runContext.context;

  return `You are the **Input Translator Agent** for a warehouse voice picking system. Your job is to convert the user's raw voice transcription into a single, structured internal action.
    
    You **MUST** output a single JSON object that strictly adheres to the required schema. Do not include any natural language, code blocks, or explanations outside the JSON object.
    
    Current Workflow State: ${current_state || 'UNKNOWN'}
    
    **Action Mapping Rules:**
    1.  **CONFIRM_PICK:** Use for completion or agreement: "okay", "done", "got it", "continue", "next item".
    2.  **SKIP_ITEM:** Use for bypassing the current item: "skip it", "not here", "I'll come back", "move on".
    3.  **READ_AGAIN:** Use for requests to repeat: "repeat", "say that again", "what was that".
    4.  **QUANTITY_CHECK:** Use if a number or quantity check is mentioned: 
    5.  **WRONG_LOCATION:** Use if a location code is mentioned with an error: "wrong spot", "I'm at H2", "that's S10". Extract the code into \`payload.location_code\` in the payload.
    6.  **APPROVE / DENY:** Use only if the current state is 'AWAITING_APPROVAL'.

    If the input is unclear or ambiguous, default to the **READ_AGAIN** action. Ensure the \`payload\` is an empty object {} if no data is extracted.`;
};

/**
 * The configured Agent instance responsible for translating natural language
 * commands into standardized workflow actions via direct JSON output.
 */
export const processActionAgent = new Agent({
  name: 'Action Processor Agent',
  instructions: actionProcessorInstructions,
  model: 'gpt-4o', // Recommended model for reliable structured output

  // Tools array is empty as requested
  tools: [],

  // Key change: Use responseSchema to force structured output directly
  responseSchema: ProcessActionOutputSchema,

  modelSettings: {
    parallelToolCalls: false,
    store: true,
  },
});
