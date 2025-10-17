/**
 * Parses JSON strings from markdown code blocks
 * @param {string} jsonString - Raw markdown text containing JSON
 * @returns {object} Parsed JSON object
 * @throws {Error} If JSON parsing fails
 */
export const jsonMdParser = (jsonString) => {
  console.log('[jsonMdParser] Starting to parse');

  jsonString = jsonString
    .replace(/^```json\s*/i, '')
    .replace(/\s*```\s*$/, '')
    .trim();

  try {
    let jsonParse = JSON.parse(jsonString);
    console.log('[jsonMdParser] Successfully parsed JSON:');
    return jsonParse;
  } catch (error) {
    console.log('[jsonMdParser] JSON parsing error:', jsonString);
    throw new Error('Failed to parse JSON: ' + error.message);
  }
};
