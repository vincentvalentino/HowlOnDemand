package edu.ncsu.csc216.howlondemand.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.ncsu.csc216.audioxml.xml.MalformedTrackException;

/**
 * Tests TrackChunk class.
 * 
 * @author kavitpatel
 */
public class TrackChunkTest {

	/**
	 * Tests validChunkPositiveValue.
	 */
	@Test
	public void validChunkPositiveValueTest() {
		String chunk = "000555FF";
		try {
			assertTrue(new TrackChunk().validChunk(chunk));
		} catch (MalformedTrackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Tests validChunkNegativeValue.
	 */
	@Test
	public void validChunkNegativeValueTest() {
		String chunk = "aadf85r5";
		try {
			assertFalse(new TrackChunk().validChunk(chunk));
		} catch (MalformedTrackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Tests createTrackChunk.
	 * 
	 * @throws MalformedTrackException.
	 */
	@Test
	public void createTrackChunkTest() throws MalformedTrackException {
		TrackChunk trackChunk = new TrackChunk("FFFF5858");
		String chunk = trackChunk.getChunk();
		assertTrue(trackChunk.validChunk(chunk));
	}

	/**
	 * Tests createTrackChunkInstanceFailByPassingNullString.
	 * 
	 * @throws MalformedTrackException.
	 */
	@Test(expected = MalformedTrackException.class)
	public void createTrackChunkInstanceFailByPassingNullStringTest() throws MalformedTrackException {
		TrackChunk tc = new TrackChunk(null);
		assertNull(tc);
	}

	/**
	 * Tests createTrackChunkInstanceFailByPassingInvalidString.
	 * 
	 * @throws MalformedTrackException.
	 */
	@Test(expected = MalformedTrackException.class)
	public void createTrackChunkInstanceFailByPassingInvalidStringTest() throws MalformedTrackException {
		TrackChunk trackChunk = new TrackChunk("FFFF5858");
		String chunk = trackChunk.getChunk();
		assertTrue(trackChunk.validChunk(chunk));
		new TrackChunk("FFFF585");
	}

	/**
	 * Tests setTrackChunkPositive.
	 * 
	 * @throws MalformedTrackException.
	 */
	@Test
	public void setTrackChunkPositiveTest() throws MalformedTrackException {
		TrackChunk trackChunk = new TrackChunk();
		trackChunk.setChunk("FFFF9898");
		assertTrue(trackChunk.getChunk().equals("FFFF9898"));
	}

	/**
	 * Tests setTrackChunkNegativeTest.
	 * 
	 * @throws MalformedTrackException.
	 */
	@Test(expected = MalformedTrackException.class)
	public void setTrackChunkNegativeTest() throws MalformedTrackException {
		TrackChunk trackChunk = new TrackChunk();
		trackChunk.setChunk("FFFF9898");
		assertTrue(trackChunk.getChunk().equals("FFFF9898"));
		trackChunk.setChunk("FFFF98998");
	}

	/**
	 * Tests getTrackChunkPositive.
	 * 
	 * @throws MalformedTrackException.
	 */
	@Test
	public void getTrackChunkPositiveTest() throws MalformedTrackException {
		TrackChunk trackChunk = new TrackChunk();
		trackChunk.setChunk("FFFF9898");
		assertTrue(trackChunk.getChunk().equals("FFFF9898"));
	}

}
