/**
 * 
 */

package edu.ncsu.csc216.howlondemand.platform.enums;

/**
 * Enumeration containing commandValues.
 * 
 * @author kavitpatel
 */
public enum CommandValue {

	/** Initial State, Quit then Selection. */
	SELECT_STATION,
	/** Buffering State. */
	BUFFERING,
	/** Not buffering State. */
	NOT_BUFFERING,
	/** Stop buffering State. */
	STOP,
	/** Play buffering State. */
	PLAY,
	/** Finish track State. */
	FINISH_TRACK,
	/** Finish station State. */
	FINISH_STATION,
	/** Return State. */
	RETURN,
	/** Skip forward State. */
	SKIP_FORWARD,
	/** Skip backward State. */
	SKIP_BACKWARD
}