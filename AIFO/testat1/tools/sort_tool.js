import { tool } from '@openai/agents';

// --- RAW JSON Schema ---
// This is the working JSON object for the tool's parameters.
const SortItemsRawJsonSchema = {
  type: 'object',
  properties: {
    items: {
      type: 'array',
      description: 'The list of item objects to be sorted.',
      items: {
        type: 'object',
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
      },
    },
  },
  required: ['items'],
  additionalProperties: false,
};

/**
 * Defines the 'sort_items' tool for the Agent.
 */
export const sortItemsTool = tool({
  name: 'sort_items',
  description:
    'Sort a list of warehouse items first by hallway and then by shelf in ascending order. The items must be provided as an array of objects.',
  parameters: SortItemsRawJsonSchema,

  execute: async (input) => {
    // The input is a plain JavaScript object
    const sorted_items = input.items.sort((a, b) => {
      // Primary sort: Hallway
      if (a.hallway === b.hallway) {
        // Secondary sort: Shelf
        return a.shelf.localeCompare(b.shelf);
      }
      return a.hallway.localeCompare(b.hallway);
    });

    // Format the result as a string for the Agent to read
    const outputString = sorted_items
      .map(
        (item) =>
          `- Item: ${item.name}, Hallway: ${item.hallway}, Shelf: ${item.shelf}`
      )
      .join('\n');

    // The tool returns a simple string message
    return `The sorted list is:\n${outputString}`;
  },
});
