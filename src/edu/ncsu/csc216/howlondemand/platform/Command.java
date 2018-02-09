/**
 * 
 */
package edu.ncsu.csc216.howlondemand.platform;

import edu.ncsu.csc216.howlondemand.platform.enums.CommandValue;

/**
 * Concrete class that represents a command (selection, play with buffering, and
 * so on) that the user enters from the GUI (or system during iteration) to be
 * handled by the internal FSM.
 * 
 * @author kavitpatel
 */
public class Command {

	private CommandValue c;

	/**
	 * CommandValue.
	 * 
	 * @param c
	 *            command.
	 */
	public Command(CommandValue c) {
		if (null == c) {
			throw new IllegalArgumentException();
		}
		this.c = c;
	}

	/**
	 * Getting command from CommandValue.
	 * 
	 * @return the command.
	 */
	public CommandValue getCommand() {
		return c;
	}

	/**
	 * toString returning command.
	 * 
	 * @return Command command + c.
	 */
	@Override
	public String toString() {
		return c + "";
	}
}
