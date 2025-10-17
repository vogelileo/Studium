export const jsonMdParser = (jsonString) => {
  console.log('[jsonMdParser] Starting to parse');

  jsonString = jsonString
    .replace(/^```json\s*/i, '') // Remove starting fence (case-insensitive for 'json')
    .replace(/\s*```\s*$/, '') // Remove ending fence
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
