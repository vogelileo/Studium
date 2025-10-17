// src/agent.js (MODIFIED)

import { Agent } from '@openai/agents';
// Assuming the path to your tool file is correct
import { sortItemsTool } from '../tools/sort_tool.js';

const ItemSchema = {
  type: 'object',
  description: 'The single item object the user should pick next.',
  properties: {
    name: { type: 'string', description: 'The name of the item.' },
    hallway: {
      type: 'string',
      description: 'The hallway location (e.g., "H1").',
    },
    shelf: {
      type: 'string',
      description: 'The shelf number (e.g., "S10").',
    },
  },
  required: ['name', 'hallway', 'shelf'],
  additionalProperties: false,
};
// --- 1. Define the Desired JSON Output Schema ---
const ItemPickingOutputSchema = {
  type: 'object',
  properties: {
    next_items: {
      type: 'array',
      description:
        'The list of the remaining items, sorted and ready for subsequent picks.',
      items: ItemSchema,
    },
    required: ['next_items'],
    additionalProperties: false,
  },
};

// --- 2. Agent Instructions (Updated) ---
const sortItemsInstructions = (runContext, _agent) => {
  const { stateItemsString } = runContext.context;
  return `You are a warehouse assistant agent. Your job is to guide the user through picking items in an optimal order. 
When you are given a list of items, you MUST call the 'sort_items' function to get them sorted. 
Once you receive the sorted list, you MUST **parse the list** and provide your final output in the required **JSON schema format**.
The 'next_items' key must contain the rest of the sorted list (all items). Do not include any additional text outside the JSON structure.

The items you need to start with are: ${stateItemsString}`;
};

/**
 * The configured Agent instance.
 */
export const itemSortingAgent = new Agent({
  name: 'Item Sorter Agent',
  instructions: sortItemsInstructions,
  model: 'gpt-4o',
  tools: [sortItemsTool],
  // --- 3. Add the output schema to enforce JSON structure ---
  responseSchema: ItemPickingOutputSchema,
  modelSettings: {
    parallelToolCalls: true,
    store: true,
  },
});
