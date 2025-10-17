import * as readline from 'readline';

// --- Configuration ---
const BASE_URL = 'http://localhost:3001/api';

// A mock input string representing the initial list of items for the sorter
const MOCK_INPUT = JSON.stringify([
  { name: 'Tool Box', hallway: 'H2', shelf: 'S5' },
  { name: 'Safety Vest', hallway: 'H1', shelf: 'S3' },
  { name: 'Power Drill', hallway: 'H2', shelf: 'S1' },
  { name: 'Gloves', hallway: 'H1', shelf: 'S4' },
]);

// --- Utility Functions ---

/**
 * Creates an interactive interface to read a single line of input from the user.
 * @param {string} query - The prompt text.
 * @returns {Promise<string>} The user's input.
 */
function readInput(query) {
  const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout,
  });

  return new Promise((resolve) =>
    rl.question(query, (answer) => {
      rl.close();
      resolve(answer.trim());
    })
  );
}

/**
 * Makes a standardized POST request to the server API.
 * @param {string} endpoint - The API path (e.g., 'start-workflow').
 * @param {object} body - The JSON payload.
 * @returns {Promise<object>} The server's response data.
 */
async function apiPost(endpoint, body) {
  const url = `${BASE_URL}/${endpoint}`;

  console.log(`\n--- API CALL: ${endpoint} ---`);
  console.log(`> Request Body: ${JSON.stringify(body)}`);

  try {
    // NOTE: This assumes you are running Node.js version 18+ which has native global fetch.
    // If using an older version, you may need to 'npm install node-fetch' and require it here.
    const response = await fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(body),
    });

    const data = await response.json();

    if (!response.ok) {
      throw new Error(
        data.error || `Server error (Status: ${response.status})`
      );
    }

    return data;
  } catch (error) {
    console.error('API Call Failed:', error.message);
    throw error;
  }
}

/**
 * Displays the current workflow state in a readable format.
 * @param {object} state - The workflow state object.
 */
function displayState(state) {
  console.log('\n===========================================');
  console.log(`STATUS: ${state.status}`);
  console.log(`CURRENT ACTION: ${state.currentAction || 'N/A'}`);
  console.log(`RESPONSE: ${state.message}`);

  if (state.currentItem) {
    console.log(
      `NEXT PICK: ${state.currentItem.name} (Aisle: ${state.currentItem.hallway}, Shelf: ${state.currentItem.shelf})`
    );
  }
  console.log(
    `ITEMS LEFT: ${state.sorted_items ? state.sorted_items.length : 0}`
  );
  console.log(
    `SKIPPED ITEMS LEFT: ${
      state.skipped_items ? state.skipped_items.length : 0
    }`
  );
  console.log('===========================================\n');
}

// --- Main Execution Logic ---

async function main() {
  console.log('--- Warehouse Picker Test Client ---');
  console.log(`Connecting to server at ${BASE_URL}\n`);

  let currentState = {};

  try {
    // 1. START WORKFLOW
    console.log('Step 1: Starting Workflow...');
    currentState = await apiPost('start-workflow', {
      input_as_text: MOCK_INPUT,
    });
    displayState(currentState);

    // 2. INTERACTIVE LOOP
    while (
      currentState.status !== 'COMPLETED' &&
      currentState.status !== 'STOPPED'
    ) {
      // Get raw transcribed voice command from user
      const rawInput = await readInput('Voice Command (type "exit" to quit): ');

      if (rawInput.toLowerCase() === 'exit') break;

      // Send raw input for the Action Processor Agent to translate
      currentState = await apiPost('continue-workflow', {
        user_input: rawInput,
      });

      displayState(currentState);
    }
  } catch (error) {
    console.error('\n*** TEST Halted ***\n', error.message);
  } finally {
    console.log('--- Client Session Ended ---');
  }
}

// Run the main function
main();
