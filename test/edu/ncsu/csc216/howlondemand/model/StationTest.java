/**
 * 
 */
package edu.ncsu.csc216.howlondemand.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.ncsu.csc216.audioxml.xml.MalformedTrackException;
import edu.ncsu.csc216.audioxml.xml.StationIOException;
import edu.ncsu.csc216.audioxml.xml.StationXML;
import edu.ncsu.csc216.audioxml.xml.StationsReader;

/**
 * Tests Station class.
 * 
 * @author kavitpatel
 */
public class StationTest {

	private static List<StationXML> stationXMLs;
	private static StationXML stationXML;

	/**
	 * load required data. like load xml test-file simple station data for test only
	 */
	@BeforeClass
	public static void loadRequireddata() throws StationIOException, MalformedTrackException {
		// load file for testing using StationsReader class.
		StationsReader stationsReader = new StationsReader("test-files/SimpleStation.xml");
		stationXMLs = stationsReader.getStations();
		stationXML = stationXMLs.get(0);
	}

	/**
	 * check instance is created by passing xml file
	 */
	@Test
	public void constructorByPassingXMLTest() throws MalformedTrackException {
		Station station = new Station(stationXML);
		// check instantiated properly.
		assertTrue(station.getId() == 0);
		// check default values.
		assertTrue(station.getIndex() == 0);
		// as audio tracks loaded from xml file
		assertTrue(!station.getPlaylist().isEmpty());
		// default repeat flag
		assertTrue(!station.getRepeat());
		// default shuffle flag
		assertTrue(!station.getShuffle());
	}

	/**
	 * check instance is created
	 */
	@Test
	public void constructorTest() {
		Station station = new Station(0, "title", 0);
		// check instantiated properly.
		assertTrue(station.getId() == 0);
		// check default values.
		assertTrue(station.getIndex() == 0);
		// as audio tracks loaded from xml file
		assertTrue(station.getPlaylist().isEmpty());
		// default repeat flag
		assertTrue(!station.getRepeat());
		// default shuffle flag
		assertTrue(!station.getShuffle());
	}

	/**
	 * getId method test
	 */
	@Test
	public void getIdTest() {
		Station station = new Station(0, "title", 0);
		// check instantiated instance id.
		assertTrue(station.getId() == 0);
		// now change the id and check again
		station.setId(1);
		assertTrue(station.getId() != 0);
		assertTrue(station.getId() == 1);
	}

	/**
	 * setId method positive test
	 */
	@Test
	public void setIdPositiveTest() {
		Station station = new Station(0, "title", 0);
		// check instantiated instance id.
		assertTrue(station.getId() == 0);
		// now change the id and check again
		station.setId(1);
		assertTrue(station.getId() == 1);
	}

	/**
	 * setId method negative test
	 */
	@Test(expected = IllegalArgumentException.class)
	public void setIdNegativeTest() {
		Station station = new Station(0, "title", 0);
		// check instantiated instance id.
		assertTrue(station.getId() == 0);
		// now change the id and check again
		station.setId(-5);
	}

	/**
	 * getTitle method test
	 */
	@Test
	public void getTitleTest() {
		Station station = new Station(0, "title", 0);
		// check instantiated instance id.
		assertTrue("title".equals(station.getTitle()));
		// now change the id and check again
		station.setTitle("titleChange");
		// should not old value
		assertTrue(!"title".equals(station.getTitle()));
		// new value will be there
		assertTrue("titleChange".equals(station.getTitle()));
	}

	/**
	 * setTitle method positive test
	 */
	@Test
	public void setTitlePositiveTest() {
		Station station = new Station(0, "title", 0);
		// check instantiated instance id.
		assertTrue("title".equals(station.getTitle()));
		// now change the id and check again
		station.setTitle("titleChange");
		// should not old value
		assertTrue(!"title".equals(station.getTitle()));
		// new value will be there
		assertTrue("titleChange".equals(station.getTitle()));
	}

	/**
	 * setTitle method negative test
	 */
	@Test(expected = IllegalArgumentException.class)
	public void setTitleNegativeTest() {
		Station station = new Station(0, "title", 0);
		// check instantiated instance id.
		assertTrue("title".equals(station.getTitle()));
		// now change the id and check again
		station.setTitle(null);
	}

	/**
	 * getRepeat method and toggleRepeat test
	 */
	@Test
	public void repeatFlagTest() {
		Station station = new Station(0, "title", 0);
		// check default value of repeat flag.
		assertFalse(station.getRepeat());
		// now now toggle its value
		station.toggleRepeat();
		assertTrue(station.getRepeat());
	}

	/**
	 * getShuffle method and toggleShuffle test
	 */
	@Test
	public void shuffleFlagTest() {
		Station station = new Station(0, "title", 0);
		// check default value of shuffle flag.
		assertFalse(station.getShuffle());
		// now now toggle its value
		station.toggleShuffle();
		assertTrue(station.getShuffle());
	}

	/**
	 * getPlaylist method test using passed argument constructor and by xml passing
	 */
	@Test
	public void getPlaylistTest() throws MalformedTrackException {
		Station station = new Station(0, "title", 0);
		// play list should not null and should be empty.
		assertNotNull(station.getPlaylist());
		assertTrue(station.getPlaylist().isEmpty());

		Station stationByXML = new Station(stationXML);
		// should not null
		assertNotNull(stationByXML.getPlaylist());
		// should not empty
		assertTrue(!stationByXML.getPlaylist().isEmpty());
		// first element is AudioTrack and should not null
		assertNotNull(stationByXML.getPlaylist().get(0));
	}

	/**
	 * addAudioTrack method test
	 */
	@Test
	public void addAudioTrackTest() {
		// station instance created with empty audio track collection
		Station station = new Station(0, "title", 0);
		assertNotNull(station.getPlaylist());
		assertTrue(station.getPlaylist().isEmpty());
		// now add audio track to that empty collection
		station.getPlaylist().add(new AudioTrack(0, "audioTrackTitle", "audioTrackArtist"));
		// now check play list should not be empty
		assertTrue(!station.getPlaylist().isEmpty());
		// verify same values are there which we have added in audio track
		assertTrue(station.getPlaylist().get(0).getId() == 0);
		assertTrue("audioTrackTitle".equals(station.getPlaylist().get(0).getTitle()));
		assertTrue("audioTrackArtist".equals(station.getPlaylist().get(0).getArtist()));
	}

	/**
	 * hasNextTrack method test
	 */
	@Test
	public void hasNextTrackTest() throws MalformedTrackException {
		// station instance created with audio track collection
		Station station = new Station(stationXML);
		// should have audio track as play list is not empty
		assertFalse(station.hasNextTrack());
		// now populate one track
		station.getPlaylist().get(0);
		station.getPlaylist().remove(0);
		// now play list should be empty
		assertTrue(station.getPlaylist().isEmpty());
		// now station should not have any audio track
		assertFalse(station.hasNextTrack());
	}

	/**
	 * getColor method test
	 */
	@Test
	public void getColorTest() {
		// station instance created with empty audio track collection
		Station station = new Station(0, "title", 0);
		// color code should be 0 which we have passed
		assertTrue(station.getColor() == 0);
		// now change color code and verify
		station.setColor(4);
		assertTrue(station.getColor() != 0);
		assertTrue(station.getColor() == 4);
	}

	/**
	 * setColor method positive and negative test
	 */
	@Test(expected = IllegalArgumentException.class)
	public void setColorPositiveNegativeTest() {
		// station instance created with empty audio track collection
		Station station = new Station(0, "title", 0);
		// color code should be 0 which we have passed
		assertTrue(station.getColor() == 0);
		// now change color code and verify
		station.setColor(4);
		assertTrue(station.getColor() != 0);
		assertTrue(station.getColor() == 4);
		// now set new illegal value
		station.setColor(6);
	}

	/**
	 * getIndex method test
	 */
	@Test
	public void getIndexTest() {
		// station instance created with empty audio track collection
		Station station = new Station(0, "title", 0);
		// now index should be zero which is default primitive value
		assertTrue(station.getIndex() == 0);
		// now modify value and check again
		station.setIndex(3);
		assertTrue(station.getIndex() != 0);
		assertTrue(station.getIndex() == 3);
	}

	/**
	 * setIndex method positive and negative test
	 */
	@Test(expected = IllegalArgumentException.class)
	public void setIndexTest() {
		// station instance created with empty audio track collection
		Station station = new Station(0, "title", 0);
		// now index should be zero which is default primitive value
		assertTrue(station.getIndex() == 0);
		// now modify value and check again
		station.setIndex(3);
		assertTrue(station.getIndex() != 0);
		assertTrue(station.getIndex() == 3);
		station.setIndex(-4);
		// now provide invalid value and exception shold be thrown
	}

	/**
	 * getCurrentAudioTrack method test
	 */
	@Test
	public void getCurrentAudioTrackTest() throws MalformedTrackException {
		// create station instance by passing station xml
		Station station = new Station(stationXML);
		// check current audio track (at index value 0) contains same details
		assertTrue("title".equals(station.getCurrentAudioTrack().getTitle()));
		assertTrue(station.getCurrentAudioTrack().getId() == 0);
	}

	/**
	 * reset method test
	 */
	@Test
	public void resetTest() throws MalformedTrackException {
		// create station instance by passing station xml
		Station station = new Station(stationXML);
		// now play list has value and repeat and shuffle also set default to flase
		assertFalse(station.getRepeat());
		// now now toggle its value
		station.toggleRepeat();
		// check default value of shuffle flag.
		assertFalse(station.getShuffle());
		// now now toggle its value
		station.toggleShuffle();
		// now set index to one
		station.setIndex(1);
		assertTrue(station.getIndex() == 1);
		// now call reset method will make all values (index,repeat and shuffle) to
		// default
		station.reset();
		assertTrue(station.getIndex() == 0);
		assertTrue(!station.getRepeat());
		assertTrue(!station.getShuffle());
	}

	/**
	 * toString method test
	 */
	@Test
	public void toStringTest() {
		Station station = new Station(0, "title", 0);
		// to string should not empty
		assertTrue(!station.toString().isEmpty());
		// string contains 'title' key word
		assertTrue(station.toString().contains("title"));
	}
}
