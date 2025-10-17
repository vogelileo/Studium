// src/workflow_manager.js

import { Runner } from '@openai/agents';
// Placeholders for your actual files:
import { itemSortingAgent } from './agents/sort_agent.js';
import { processActionAgent } from './agents/action_processor_agent.js'; // NEW AGENT
import { jsonMdParser } from './tools/json_md_parser.js';
import { generateImage } from './agents/image_agent.js';

// Define the standard set of internal actions/intents
const ACTIONS = {
  // General Control
  APPROVE: 'APPROVE',
  DENY: 'DENY',
  CONFIRM_PICK: 'CONFIRM_PICK',

  // New Picker Actions
  SKIP_ITEM: 'SKIP_ITEM',
  QUANTITY_CHECK: 'QUANTITY_CHECK',
  READ_AGAIN: 'READ_AGAIN', // The 'read-again' action

  // Error/Audit Actions
  // ... add WRONG_LOCATION, INCORRECT_QUANTITY later ...
};

/**
 * Manages the state and execution of the picker workflow.
 */
export class WorkflowManager {
  constructor(
    workflowInputText,
    runner = new Runner({
      traceMetadata: {
        /* ... */
      },
    })
  ) {
    this.state = {
      status: 'INITIALIZING',
      input_as_text: workflowInputText,
      conversationHistory: [],
      sorted_items: [],
      skipped_items: [], // NEW: To hold items marked to be skipped
      currentItem: null,
      currentAction: null,
      traceMetadata: {
        __trace_source__: 'agent-builder',
        workflow_id: 'wf_68f2218ffe248190a9a1af8127ddb9270dea7af265e708ff',
      },
    };
    this.runner = runner;
  }

  // --- State-Transition Utilities ---

  _requestInitialApproval() {
    this.state.status = 'AWAITING_APPROVAL';
    this.state.message =
      'Do you have all required certifications to collect the items?';
    return this.state;
  }

  _requestItemPicked(item) {
    this.state.status = 'AWAITING_PICK_CONFIRMATION';
    this.state.currentItem = item;
    // Include location details for the picker
    this.state.message = `Please pick ${item.name} at aisle ${item.hallway}, shelf ${item.shelf}. Say 'confirm' when complete.`;
    return this.state;
  }

  _nextStep() {
    // Prioritize picking regular items
    if (this.state.sorted_items.length > 0) {
      const next_item = this.state.sorted_items.shift();
      return this._requestItemPicked(next_item);
    }

    // If regular list is empty, start re-picking skipped items
    if (this.state.skipped_items.length > 0) {
      const next_item = this.state.skipped_items.shift();
      this.state.message = `Re-picking skipped item: ${next_item.name}.`;
      return this._requestItemPicked(next_item);
    }

    // All done
    this.state.status = 'COMPLETED';
    this.state.message = 'All items have been picked. Workflow finished.';
    return this.state;
  }

  // --- Main Workflow Control ---

  async start() {
    this.state.conversationHistory = [
      {
        role: 'user',
        content: [{ type: 'input_text', text: this.state.input_as_text }],
      },
    ];
    return this._requestInitialApproval();
  }

  /**
   * Central continuation function that processes natural language input.
   * It uses an agent to translate the user's voice transcription into a standardized action.
   * @param {string} rawUserInput - The raw transcribed text (e.g., "I'm ready to go", "skip this one", "I counted four")
   */
  async continue(rawUserInput) {
    // 1. Use the new agent to translate the raw voice input into a standard action and payload.
    const { action, payload } = await this._processUserActionAgent(
      rawUserInput
    );

    // 2. Add the user input to history (important for the action_processor_agent)
    this.state.conversationHistory.push({
      role: 'user',
      content: [{ type: 'input_text', text: rawUserInput }],
    });

    // 3. Execute the standard action
    return this._executeAction(action, payload);
  }

  /**
   * Executes the standardized action with its payload.
   */
  _executeAction(action, payload = {}) {
    const { status, currentItem } = this.state;
    this.state.currentAction = action;
    // --- PHASE 1: AWAITING_APPROVAL ---
    if (status === 'AWAITING_APPROVAL') {
      if (action === ACTIONS.APPROVE) {
        return this._runAgentAndStartPicking();
      }
      if (action === ACTIONS.DENY) {
        this.state.status = 'STOPPED';
        this.state.message = 'Workflow stopped by user denial.';
        return this.state;
      }
      // For other actions, you can return a "Read Again" state or an error
      this.state.message = `I did not understand. Please say 'continue' or 'deny'.`;
      return this.state;
    }

    // --- PHASE 2: AWAITING_PICK_CONFIRMATION ---
    if (status === 'AWAITING_PICK_CONFIRMATION' && currentItem) {
      switch (action) {
        case ACTIONS.CONFIRM_PICK:
          // Log confirmed pick and move to next item
          console.log(`[BACKEND] Item confirmed picked: ${currentItem.name}`);
          this.state.currentItem = null;
          return this._nextStep();

        case ACTIONS.SKIP_ITEM:
          // Add current item back to the list of skipped items
          console.log(`[BACKEND] Item skipped: ${currentItem.name}`);
          this.state.skipped_items.push(currentItem);
          this.state.currentItem = null; // Clear current item
          return this._nextStep(); // Move to the next item in the main list

        case ACTIONS.QUANTITY_CHECK:
          // Quantity check logic: In a real system, this would call an API.
          // Here, we just return a message with the provided quantity.
          const quantity = payload.quantity || 'unknown';
          this.state.message = `There are currently ${this.state.sorted_items}`;
          // Stay in the same state until 'CONFIRM_PICK' is received
          return this.state;

        case ACTIONS.READ_AGAIN:
          // Repeat the instruction for the current item
          return this._requestItemPicked(currentItem);

        default:
          // Handle unknown/invalid action for this phase
          this.state.message = `Invalid command. Please say 'confirm', 'skip', or 'quantity check'.`;
          return this.state;
      }
    }

    // Final state reached
    if (status === 'COMPLETED' || status === 'STOPPED') {
      this.state.message = `Workflow is ${status}. Cannot continue.`;
      return this.state;
    }

    throw new Error(
      `Invalid action: ${action} for status: ${this.state.status}`
    );
  }

  // --- Agent Tools ---

  // Internal function to run the item sorting agent
  async _runAgentAndStartPicking() {
    this.state.status = 'RUNNING_AGENT';
    console.log('[WorkflowManager] Running Item Sorting Agent...');
    const runResult = await this.runner.run(
      itemSortingAgent,
      this.state.conversationHistory,
      { context: { stateItemsString: this.state.input_as_text } }
    );

    if (!runResult.finalOutput) {
      throw new Error('Sorting Agent did not return a final output.');
    }

    const output_parsed = jsonMdParser(runResult.finalOutput);
    this.state.sorted_items = output_parsed.next_items || [];
    console.log('[WorkflowManager] Sorting Agent completed');
    // Process images in batches of 10
    for (let i = 0; i < this.state.sorted_items.length; i += 10) {
      const batch = this.state.sorted_items.slice(i, i + 10);
      const imagePromises = batch.map((item) => generateImage(item.name));
      const images = await Promise.all(imagePromises);

      batch.forEach((item, index) => {
        item.image_url = images[index].path;
      });
    }

    console.log('[WorkflowManager] Added images to items:');

    //Make for every item a link to an image :)

    return this._nextStep();
  }

  // NEW: Agent to convert natural language to a standardized action
  async _processUserActionAgent(rawUserInput) {
    // You'd need to design the processActionAgent to use function calling
    // and return a JSON object like: { action: 'SKIP_ITEM', payload: {} }
    // or { action: 'QUANTITY_CHECK', payload: { quantity: 4 } }

    const historyForAgent = [
      ...this.state.conversationHistory,
      { role: 'user', content: [{ type: 'input_text', text: rawUserInput }] },
    ];

    const runResult = await this.runner.run(
      processActionAgent,
      historyForAgent,
      { context: { current_state: this.state.status } } // Provide context to the agent
    );

    if (!runResult.finalOutput) {
      // Fallback: Default to 'READ_AGAIN' or 'CONFIRM_PICK' if agent fails
      return { action: ACTIONS.READ_AGAIN, payload: {} };
    }

    let result;
    try {
      // The agent's final output should be the JSON from its tool call
      console.log('----------');
      console.log(runResult.finalOutput);
      result = jsonMdParser(runResult.finalOutput);
      console.log('[WorkflowManager] Action Processor Agent output:', result);
      // Validate the action against our standard list
      if (!ACTIONS[result.action]) {
        console.warn(`Agent returned unrecognized action: ${result.action}`);
        return { action: ACTIONS.READ_AGAIN, payload: {} };
      }
      return result;
    } catch (e) {
      console.error('Failed to parse agent output:');
      return { action: ACTIONS.READ_AGAIN, payload: {} };
    }
  }
}
