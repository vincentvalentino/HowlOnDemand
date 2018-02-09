package edu.ncsu.csc216.howlondemand.model;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.ncsu.csc216.audioxml.xml.AudioTrackXML;
import edu.ncsu.csc216.audioxml.xml.MalformedTrackException;
import edu.ncsu.csc216.audioxml.xml.StationIOException;
import edu.ncsu.csc216.audioxml.xml.StationXML;
import edu.ncsu.csc216.audioxml.xml.StationsReader;

/**
 * Testing AudioTrack.
 * 
 * @author kavitpatel
 */
public class AudioTrackTest {

	private static List<AudioTrackXML> audioTrackXMLs;

	/**
	 * Load required data. like load xml test-file simple station data for test
	 * only.
	 */
	@BeforeClass
	public static void loadRequireddata() throws StationIOException, MalformedTrackException {
		// Load file for testing using StationsReader class.
		StationsReader stationsReader = new StationsReader("test-files/SimpleStation.xml");
		prepareAudioTrackXMLs(stationsReader.getStations());
	}

	private static void prepareAudioTrackXMLs(List<StationXML> stationXMLs) throws MalformedTrackException {
		// Last station tracks will be loaded
		for (StationXML stationXML : stationXMLs) {
			audioTrackXMLs = stationXML.getAudioTracks().getAudioTrackXML();
		}
	}

	/**
	 * Check instance is created and it is also instance of Multimedia
	 */
	@Test
	public void constructorTest() {
		AudioTrack audioTrack = new AudioTrack(1, "titleOne", "artistOne");
		// Check instantiated properly.
		assertTrue(audioTrack.getId() == 1);
		// Check is instance of Multimedia.
		assertTrue(audioTrack instanceof Multimedia);
	}

	/**
	 * Check instance is created and default values are initialized properly
	 */
	@Test
	public void constructorInitializationDefaultValuesTest() {
		AudioTrack audioTrack = new AudioTrack(1, "titleOne", "artistOne");
		// Check instantiated properly.
		assertTrue(audioTrack.getId() == 1);
		// Check default values
		assertTrue(audioTrack.getChunkIndex() == 0);
		// Empty track chunks collection
		assertTrue(audioTrack.getTrackChunkSize() == 0);
	}

	/**
	 * Create instance using audio track xml
	 */
	@Test
	public void constructorByPassingAudioTrackXMLTest() throws MalformedTrackException {
		AudioTrack audioTrack = new AudioTrack(audioTrackXMLs.get(0));
		// Check instantiated properly.
		assertTrue(audioTrack.getId() == 0);
		// Check title value.
		assertTrue("title".equals(audioTrack.getTitle()));
		// Check artist value.
		assertTrue("artist".equals(audioTrack.getArtist()));
		// Check default values.
		assertTrue(audioTrack.getChunkIndex() == 0);
		// Empty track chunks collection.
		assertTrue(audioTrack.getTrackChunkSize() == 1);
	}

	/**
	 * getArtist method test.
	 */
	@Test
	public void getArtistTest() {
		AudioTrack audioTrack = new AudioTrack(1, "titleOne", "artistOne");
		// Check artist name is same as passed in constructor argument.
		assertTrue("artistOne".equals(audioTrack.getArtist()));
	}

	/**
	 * setArtist method positive test.
	 */
	@Test
	public void setArtistPositiveTest() {
		AudioTrack audioTrack = new AudioTrack(1, "titleOne", "artistOne");
		audioTrack.setArtist("artistSet");
		// Set and check artist name.
		assertTrue("artistSet".equals(audioTrack.getArtist()));
	}

	/**
	 * setArtist method negative test.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void setArtistNegativeTest() {
		AudioTrack audioTrack = new AudioTrack(1, "titleOne", "artistOne");
		audioTrack.setArtist("artistSet");
		assertTrue("artistSet".equals(audioTrack.getArtist()));
		// Null not allowed and exception will be thrown.
		audioTrack.setArtist(null);
	}

	/**
	 * getChunkIndex method test.
	 */
	@Test
	public void getChunkIndexTest() throws MalformedTrackException {
		AudioTrack audioTrack = new AudioTrack(1, "titleOne", "artistOne");
		// Check chunk index value.
		assertTrue(audioTrack.getChunkIndex() == 0);
		// Now set and check test.
		AudioTrack audioTrackByXML = new AudioTrack(audioTrackXMLs.get(0));
		assertTrue(audioTrackByXML.getTrackChunkSize() == 1);
		audioTrackByXML.getNextChunk();
		assertTrue(audioTrackByXML.getChunkIndex() == 1);
		audioTrackByXML.setChunkIndex(0);
		assertTrue(audioTrackByXML.getChunkIndex() == 0);
	}

	/**
	 * setChunkIndex method positive test.
	 */
	@Test
	public void setChunkIndexPositiveTest() throws MalformedTrackException {
		// Now set and check chunk index test.
		AudioTrack audioTrackByXML = new AudioTrack(audioTrackXMLs.get(0));
		assertTrue(audioTrackByXML.getTrackChunkSize() == 1);
		audioTrackByXML.getNextChunk();
		assertTrue(audioTrackByXML.getChunkIndex() == 1);
		audioTrackByXML.setChunkIndex(0);
		assertTrue(audioTrackByXML.getChunkIndex() == 0);
	}

	/**
	 * setChunkIndex method negative test.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void setChunkIndexNegativeTest() throws MalformedTrackException {
		AudioTrack audioTrack = new AudioTrack(1, "titleOne", "artistOne");
		// Check chunk index value.
		assertTrue(audioTrack.getChunkIndex() == 0);
		// Now set index test.
		audioTrack.setChunkIndex(1);
	}

	/**
	 * getTrackChunkSize method test.
	 */
	@Test
	public void getTrackChunkSizeTest() throws MalformedTrackException {
		AudioTrack audioTrack = new AudioTrack(1, "titleOne", "artistOne");
		// Initial chunk size should be zero.
		assertTrue(audioTrack.getTrackChunkSize() == 0);
		audioTrack.addChunk(new TrackChunk("FFFF8585"));
		// Now check track chunk size.
		assertTrue(audioTrack.getTrackChunkSize() == 1);

	}

	/**
	 * getNextChunk method positive test.
	 */
	@Test
	public void getNextChunkPositiveTest() throws MalformedTrackException {
		AudioTrack audioTrack = new AudioTrack(audioTrackXMLs.get(0));
		// Should not empty chunk or null.
		assertTrue(audioTrack.getChunkIndex() == 0);
		assertTrue(!audioTrack.getNextChunk().getChunk().isEmpty());
		assertTrue(audioTrack.getChunkIndex() == 1);

	}

	/**
	 * getNextChunk method negative test.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void getNextChunkNegativeTest() throws MalformedTrackException {
		AudioTrack audioTrack = new AudioTrack(audioTrackXMLs.get(0));
		// Should not empty chunk or null.
		assertTrue(audioTrack.getChunkIndex() == 0);
		assertTrue(!audioTrack.getNextChunk().getChunk().isEmpty());
		assertTrue(audioTrack.getChunkIndex() == 1);
		audioTrack.getNextChunk();

	}

	/**
	 * addChunk method test.
	 */
	@Test
	public void addChunkTest() throws MalformedTrackException {
		AudioTrack audioTrack = new AudioTrack(1, "titleOne", "artistOne");
		// Initial chunk size should be zero.
		assertTrue(audioTrack.getTrackChunkSize() == 0);
		audioTrack.addChunk(new TrackChunk("FFFF8585"));
		// Now check track chunk size.
		assertTrue(audioTrack.getTrackChunkSize() == 1);

	}

	/**
	 * toString method test.
	 */
	@Test
	public void toStringTest() {
		AudioTrack audioTrack = new AudioTrack(1, "titleOne", "artistOne");
		// Should not empty after instance creation.
		assertTrue(!audioTrack.toString().isEmpty());

	}
}
