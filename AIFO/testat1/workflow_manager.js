import { Runner } from '@openai/agents';

import { itemSortingAgent } from './agents/sort_agent.js';
import { processActionAgent } from './agents/action_processor_agent.js';
import { jsonMdParser } from './tools/json_md_parser.js';
import { generateImage } from './agents/image_agent.js';

const ACTIONS = {
  APPROVE: 'APPROVE',
  DENY: 'DENY',
  CONFIRM_PICK: 'CONFIRM_PICK',

  SKIP_ITEM: 'SKIP_ITEM',
  QUANTITY_CHECK: 'QUANTITY_CHECK',
  READ_AGAIN: 'READ_AGAIN',
};

/**
 * Manages the state and execution of the picker workflow.
 */
export class WorkflowManager {
  /**
   * Creates a new WorkflowManager with initial state and an optional Runner.
   * @param {string} workflowInputText - The raw input text representing items to process.
   * @param {Runner} [runner] - The agent runner instance used to execute agents.
   */
  constructor(
    workflowInputText,
    runner = new Runner({
      traceMetadata: {},
    })
  ) {
    this.state = {
      status: 'INITIALIZING',
      input_as_text: workflowInputText,
      conversationHistory: [],
      sorted_items: [],
      skipped_items: [],
      currentItem: null,
      currentAction: null,
      traceMetadata: {
        __trace_source__: 'agent-builder',
        workflow_id: 'wf_68f2218ffe248190a9a1af8127ddb9270dea7af265e708ff',
      },
    };
    this.runner = runner;
  }

  /**
   * Prepares the workflow to request initial approval from the user.
   * @returns {object} The updated workflow state awaiting approval.
   */
  _requestInitialApproval() {
    this.state.status = 'AWAITING_APPROVAL';
    this.state.message =
      'Do you have all required certifications to collect the items?';
    return this.state;
  }

  /**
   * Prompts the user to pick a specific item and confirms when complete.
   * @param {object} item - The item object containing name and location details.
   * @returns {object} The updated workflow state awaiting pick confirmation.
   */
  _requestItemPicked(item) {
    this.state.status = 'AWAITING_PICK_CONFIRMATION';
    this.state.currentItem = item;

    this.state.message = `Please pick ${item.name} at aisle ${item.hallway}, shelf ${item.shelf}. Say 'confirm' when complete.`;
    return this.state;
  }

  /**
   * Determines and advances to the next step in the workflow.
   * @returns {object} The updated workflow state after advancing.
   */
  _nextStep() {
    if (this.state.sorted_items.length > 0) {
      const next_item = this.state.sorted_items.shift();
      return this._requestItemPicked(next_item);
    }

    if (this.state.skipped_items.length > 0) {
      const next_item = this.state.skipped_items.shift();
      this.state.message = `Re-picking skipped item: ${next_item.name}.`;
      return this._requestItemPicked(next_item);
    }

    this.state.status = 'COMPLETED';
    this.state.message = 'All items have been picked. Workflow finished.';
    return this.state;
  }

  /**
   * Initializes the workflow and primes the conversation history.
   * @returns {Promise<object>} The initial workflow state requesting approval.
   */
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
   * Processes user input to infer an action and continues the workflow.
   * @param {string} rawUserInput - The raw transcribed text from the user.
   * @returns {Promise<object>} The updated workflow state after executing the inferred action.
   */
  async continue(rawUserInput) {
    const { action, payload } = await this._processUserActionAgent(
      rawUserInput
    );

    this.state.conversationHistory.push({
      role: 'user',
      content: [{ type: 'input_text', text: rawUserInput }],
    });

    return this._executeAction(action, payload);
  }

  /**
   * Executes a standardized action with an optional payload to update the state.
   * @param {string} action - The standardized action to execute.
   * @param {object} [payload={}] - The data required to perform the action.
   * @returns {object} The updated workflow state after executing the action.
   */
  _executeAction(action, payload = {}) {
    const { status, currentItem } = this.state;
    this.state.currentAction = action;

    if (status === 'AWAITING_APPROVAL') {
      if (action === ACTIONS.APPROVE) {
        return this._runAgentAndStartPicking();
      }
      if (action === ACTIONS.DENY) {
        this.state.status = 'STOPPED';
        this.state.message = 'Workflow stopped by user denial.';
        return this.state;
      }

      this.state.message = `I did not understand. Please say 'continue' or 'deny'.`;
      return this.state;
    }

    if (status === 'AWAITING_PICK_CONFIRMATION' && currentItem) {
      switch (action) {
        case ACTIONS.CONFIRM_PICK:
          console.log(`[BACKEND] Item confirmed picked: ${currentItem.name}`);
          this.state.currentItem = null;
          return this._nextStep();

        case ACTIONS.SKIP_ITEM:
          console.log(`[BACKEND] Item skipped: ${currentItem.name}`);
          this.state.skipped_items.push(currentItem);
          this.state.currentItem = null;
          return this._nextStep();

        case ACTIONS.QUANTITY_CHECK:
          const quantity = payload.quantity || 'unknown';
          this.state.message = `There are currently ${this.state.sorted_items}`;

          return this.state;

        case ACTIONS.READ_AGAIN:
          return this._requestItemPicked(currentItem);

        default:
          this.state.message = `Invalid command. Please say 'confirm', 'skip', or 'quantity check'.`;
          return this.state;
      }
    }

    if (status === 'COMPLETED' || status === 'STOPPED') {
      this.state.message = `Workflow is ${status}. Cannot continue.`;
      return this.state;
    }

    throw new Error(
      `Invalid action: ${action} for status: ${this.state.status}`
    );
  }

  /**
   * Runs the sorting agent, enriches items with images, and begins picking.
   * @returns {Promise<object>} The next workflow state after preparing items.
   */
  async _runAgentAndStartPicking() {
    this.state.status = 'RUNNING_AGENT';
    console.log(
      '[WorkflowManager] Running Item Sorting Agent...',
      this.state.input_as_text
    );
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

    for (let i = 0; i < this.state.sorted_items.length; i += 10) {
      const batch = this.state.sorted_items.slice(i, i + 10);
      const imagePromises = batch.map((item) => generateImage(item.name));
      const images = await Promise.all(imagePromises);

      batch.forEach((item, index) => {
        item.image_url = images[index].path;
      });
    }

    console.log('[WorkflowManager] Added images to items:');

    return this._nextStep();
  }

  /**
   * Calls the action processor agent to convert user text into an action and payload.
   * @param {string} rawUserInput - The raw transcribed text to interpret.
   * @returns {Promise<{action: string, payload: object}>} The standardized action and associated payload.
   */
  async _processUserActionAgent(rawUserInput) {
    const historyForAgent = [
      ...this.state.conversationHistory,
      { role: 'user', content: [{ type: 'input_text', text: rawUserInput }] },
    ];

    const runResult = await this.runner.run(
      processActionAgent,
      historyForAgent,
      { context: { current_state: this.state.status } }
    );

    if (!runResult.finalOutput) {
      return { action: ACTIONS.READ_AGAIN, payload: {} };
    }

    let result;
    try {
      console.log('----------');
      console.log(runResult.finalOutput);
      result = jsonMdParser(runResult.finalOutput);
      console.log('[WorkflowManager] Action Processor Agent output:', result);

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
