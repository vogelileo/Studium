import { Agent } from '@openai/agents';

/**
 * Schema definition for standardizing voice command processing output.
 * Enforces strict typing and validation for agent responses.
 */
const ProcessActionOutputSchema = {
  type: 'object',
  properties: {
    action: {
      type: 'string',
      description:
        "The standardized internal command derived from the user's input.",
      enum: [
        'APPROVE',
        'DENY',
        'CONFIRM_PICK',
        'SKIP_ITEM',
        'QUANTITY_CHECK',
        'READ_AGAIN',
        'WRONG_LOCATION',
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

      additionalProperties: true,
    },
  },
  required: ['action'],
  additionalProperties: false,
};

/**
 * Builds the instruction string passed to the action-processing agent.
 * @param {Object} runContext - Runner context; contains workflow state
 * @param {Agent} _agent - Agent instance (unused here but available)
 * @returns {string} Instructions guiding the agent to emit a strict JSON action
 */
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
 * OpenAI agent configuration for processing voice commands.
 * Translates natural language into standardized workflow actions.
 */
export const processActionAgent = new Agent({
  name: 'Action Processor Agent',
  instructions: actionProcessorInstructions,
  model: 'gpt-4o',
  tools: [],
  responseSchema: ProcessActionOutputSchema,

  modelSettings: {
    parallelToolCalls: false,
    store: true,
  },
});
