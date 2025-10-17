import { tool } from '@openai/agents';

// --- RAW JSON Schema ---
// This is the working JSON object for the tool's parameters.
/**
 * JSON Schema for validating warehouse item sorting parameters
 * @type {Object}
 */
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
 * Tool for sorting warehouse items by location
 * Sorts items first by hallway and then by shelf number
 * @param {Object} input - Input object containing items array
 * @returns {string} Formatted string of sorted items
 */
export const sortItemsTool = tool({
  name: 'sort_items',
  description:
    'Sort a list of warehouse items first by hallway and then by shelf in ascending order. The items must be provided as an array of objects.',
  parameters: SortItemsRawJsonSchema,

  execute: async (input) => {
    const sorted_items = input.items.sort((a, b) => {
      if (a.hallway === b.hallway) {
        return a.shelf.localeCompare(b.shelf);
      }
      return a.hallway.localeCompare(b.hallway);
    });

    const outputString = sorted_items
      .map(
        (item) =>
          `- Item: ${item.name}, Hallway: ${item.hallway}, Shelf: ${item.shelf}`
      )
      .join('\n');

    return `The sorted list is:\n${outputString}`;
  },
});
