package edu.ncsu.csc216.howlondemand.platform;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.ncsu.csc216.howlondemand.platform.enums.CommandValue;

/**
 * Testing Command class.
 * 
 * @author kavitpatel
 *
 */
public class CommandTest {

	/**
	 * Testing command constructor.
	 */
	@Test
	public void commandConstructorTest() {
		Command command = new Command(CommandValue.SELECT_STATION);
		// Should not null.
		assertNotNull(command);
		// Same value as passed should be there.
		assertTrue(CommandValue.SELECT_STATION.equals(command.getCommand()));
	}

	/**
	 * Test instantiation of {@link edu.ncsu.csc216.howlondemand.platform.Command}
	 * with null argument.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void commandConstructorWithNullArgumentTest() {
		Command command = new Command(CommandValue.PLAY);
		assertNotNull(command);
		new Command(null);
	}

	/**
	 * Test getCommand of {@link edu.ncsu.csc216.howlondemand.platform.Command} with
	 * null argument.
	 */
	@Test()
	public void getCommandTest() {
		Command command = new Command(CommandValue.PLAY);
		// Should not null.
		assertNotNull(command);
		// Different value as passed should be there.
		assertFalse(CommandValue.SELECT_STATION.equals(command.getCommand()));
	}

	/**
	 * Test toString of {@link edu.ncsu.csc216.howlondemand.platform.Command}.
	 */
	@Test()
	public void toStringTest() {
		Command command = new Command(CommandValue.PLAY);
		// Should not null.
		assertNotNull(command);
		// To string.
		assertTrue(command.toString().contains("PLAY"));
	}

}
