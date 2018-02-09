/**
 * 
 */
package edu.ncsu.csc216.howlondemand.ui.util;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.Test;

import edu.ncsu.csc216.howlondemand.ui.util.Properties;

/**
 * Testing Properties Interface.
 * 
 * @author kavitpatel
 */
public class PropertiesTest {

	/**
	 * properties interface constants test
	 */
	@Test
	public void propertiesPrimitivesTest() {

		// same station selection
		assertSame(Properties.STATION_SELECTION, 0);
		// now check same station play
		assertSame(Properties.STATION_PLAY, 1);
		// now test colour is in colour array
		assertNotSame(Properties.LIST.length, 0);
	}

	/**
	 * Properties interface colour value test.
	 */
	@Test
	public void propertiesColourValueTest() {

		// instance of colour
		assertTrue(Properties.BLUE instanceof Color);
		// check color value
		assertTrue(Properties.BLUE.equals(Color.decode("#427E93")));
	}

	/**
	 * Properties interface colour value test.
	 */
	@Test
	public void propertiesColourListValueTest() {

		// instance of colour
		assertTrue(Properties.BLUE instanceof Color);
		final Color blue = Color.decode("#427E93");
		final Color blueFromList = Properties.LIST[4];
		// check color value
		assertTrue(blue.equals(blueFromList));
	}

}