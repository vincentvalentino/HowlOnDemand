/**
 * 
 */

package edu.ncsu.csc216.howlondemand.platform;

/**
 * Abstract state of interface HowlOnDemandSystemState.
 * 
 * @author kavitpatel
 */
public interface HowlOnDemandSystemState {

	/**
	 * Update the HowlOnDemandSystem based on the given Command. An
	 * UnsupportedOperationException is throw if the CommandValue is not a valid
	 * action for the given state.
	 * 
	 * @param command
	 *            describing the action that will update the HowlOnDemandSystem.
	 * @throws UnsupportedOperationException
	 *             if the CommandValue is not a valid action for the given state.
	 */
	void updateState(Command command);

	/**
	 * Given getStateName.
	 * 
	 * @return state name.
	 */
	String getStateName();
}
