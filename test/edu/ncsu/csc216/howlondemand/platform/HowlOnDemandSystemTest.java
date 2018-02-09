package edu.ncsu.csc216.howlondemand.platform;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;

import edu.ncsu.csc216.audioxml.xml.MalformedTrackException;
import edu.ncsu.csc216.audioxml.xml.StationIOException;
import edu.ncsu.csc216.howlondemand.model.AudioTrack;
import edu.ncsu.csc216.howlondemand.model.Station;
import edu.ncsu.csc216.howlondemand.model.TrackChunk;
import edu.ncsu.csc216.howlondemand.platform.enums.CommandValue;

/**
 * Tests HowlOnDemandSystem.
 * 
 * @author kavitpatel
 *
 */
public class HowlOnDemandSystemTest {

	/**
	 * test Singleton Pattern
	 */
	@Test
	public void getSingleTonInstanceTest() {
		// will create new instance.
		HowlOnDemandSystem howlOnDemandSystem = HowlOnDemandSystem.getInstance();
		// should not null
		assertNotNull(howlOnDemandSystem);
		assertNotNull(howlOnDemandSystem.getChunkSize());
		assertNotNull(howlOnDemandSystem.getStations());
		assertNotNull(howlOnDemandSystem.getState());
		// should empty
		assertTrue(howlOnDemandSystem.getChunkSize() == 0);
		// should be a current state
		assertTrue(howlOnDemandSystem.getState().getStateName().equals("Selection"));
	}

	/**
	 * test loadStationsFromFile success scenario
	 */
	@Test
	public void loadStationsFromFileSuccessTest() throws StationIOException, MalformedTrackException {
		// will create new instance.
		HowlOnDemandSystem howlOnDemandSystem = HowlOnDemandSystem.getInstance();
		howlOnDemandSystem.loadStationsFromFile("test-files/SimpleStation.xml");
		Station station = howlOnDemandSystem.getStations().get(0);
		// station should not null
		assertNotNull(station);
		// not empty station data
		assertNotNull(station.getTitle());
	}

	/**
	 * test loadStationsFromFile StationIOException scenario
	 */
	@Test(expected = StationIOException.class)
	public void loadStationsFromFileStationIOExceptionTest() throws StationIOException, MalformedTrackException {
		// will create new instance.
		HowlOnDemandSystem howlOnDemandSystem = HowlOnDemandSystem.getInstance();
		assertNotNull(howlOnDemandSystem);
		// pass invalid file name
		howlOnDemandSystem.loadStationsFromFile("test-files/SimpleSation.xml");
		// StationIOException will thrown as expected
	}

	/**
	 * test loadStationsFromFile MalformedTrackException scenario
	 */
	@Test(expected = MalformedTrackException.class)
	public void loadStationsFromFileMalformedTrackExceptionTest() throws StationIOException, MalformedTrackException {
		// will create new instance.
		HowlOnDemandSystem howlOnDemandSystem = HowlOnDemandSystem.getInstance();
		assertNotNull(howlOnDemandSystem);
		// pass invalid file
		howlOnDemandSystem.loadStationsFromFile("test-files/InvalidSimpleStation.xml");
		// MalformedTrackException will thrown as expected
	}

	/**
	 * test getStations method by creating instance
	 */
	@Test
	public void getStationsTest() throws StationIOException, MalformedTrackException {
		// will create new instance.
		HowlOnDemandSystem howlOnDemandSystem = HowlOnDemandSystem.getInstance();
		// pass invalid file
		howlOnDemandSystem.loadStationsFromFile("test-files/SimpleStation.xml");
		// check now station exist in the list
		Station station = howlOnDemandSystem.getStations().get(0);
		// station should not null
		assertNotNull(station);
		// not empty station data
		assertNotNull(station.getTitle());
	}

	/**
	 * test loadStation method by creating instance
	 */
	@Test
	public void loadStationTest() throws StationIOException, MalformedTrackException {
		// will create new instance.
		HowlOnDemandSystem howlOnDemandSystem = HowlOnDemandSystem.getInstance();
		// pass file
		howlOnDemandSystem.loadStationsFromFile("test-files/StationList_short.xml");
		Station stationAtIndexOne = howlOnDemandSystem.getStations().get(1);
		// before do anything reset system
		howlOnDemandSystem.reset();
		// now check current station should null
		assertNull(howlOnDemandSystem.getCurrentStation());
		// now load station and check that current station is not null
		howlOnDemandSystem.loadStation(stationAtIndexOne);
		// station should not null
		assertNotNull(howlOnDemandSystem.getCurrentStation());
	}

	/**
	 * test loadStation method throwing exception scenario
	 */
	@Test(expected = IllegalArgumentException.class)
	public void loadStationExceptionTest() {
		// will create new instance.
		HowlOnDemandSystem howlOnDemandSystem = HowlOnDemandSystem.getInstance();
		assertNotNull(howlOnDemandSystem);
		howlOnDemandSystem.loadStation(null);
		// IllegalArgumentException will be thrown as expected
	}

	/**
	 * test getCurrentStation method by creating instance
	 */
	@Test
	public void getCurrentStationTest() throws StationIOException, MalformedTrackException {
		// will create new instance.
		HowlOnDemandSystem howlOnDemandSystem = HowlOnDemandSystem.getInstance();
		// load stations
		howlOnDemandSystem.loadStationsFromFile("test-files/StationList_short.xml");
		// before do anything reset system
		Station stationAtIndexOne = howlOnDemandSystem.getStations().get(0);
		howlOnDemandSystem.reset();
		// now check current station should null
		assertNull(howlOnDemandSystem.getCurrentStation());
		assertNull(howlOnDemandSystem.getCurrentStation());
		// now load station and check that current station is not null
		howlOnDemandSystem.loadStation(stationAtIndexOne);
		// station should not null
		assertNotNull(howlOnDemandSystem.getCurrentStation());
		// no null pointer exception
		assertNotNull(howlOnDemandSystem.getCurrentStation().getTitle());
	}

	/**
	 * test getCurrentAudiotrack method by creating instance
	 */
	@Test
	public void getCurrentAudiotrackTest() throws StationIOException, MalformedTrackException {
		// will create new instance.
		HowlOnDemandSystem howlOnDemandSystem = HowlOnDemandSystem.getInstance();
		// load stations
		howlOnDemandSystem.loadStationsFromFile("test-files/StationList_short.xml");
		// before do anything reset system
		Station stationAtIndexOne = howlOnDemandSystem.getStations().get(0);
		howlOnDemandSystem.reset();
		// now check current station should null
		assertNull(howlOnDemandSystem.getCurrentStation());
		howlOnDemandSystem.loadStation(stationAtIndexOne);
		// station should not null
		assertNotNull(howlOnDemandSystem.getCurrentStation());
		AudioTrack currentAudioTrack = howlOnDemandSystem.getCurrentStation().getCurrentAudioTrack();
		assertNotNull(currentAudioTrack);
	}

	/**
	 * test reset method by creating instance
	 */
	@Test
	public void resetTest() throws StationIOException, MalformedTrackException {
		// will create new instance.
		HowlOnDemandSystem howlOnDemandSystem = HowlOnDemandSystem.getInstance();
		// load stations
		howlOnDemandSystem.loadStationsFromFile("test-files/StationList_short.xml");
		// before reset tests
		// initially no chunks in buffer so size is zero
		assertTrue(howlOnDemandSystem.getChunkSize() == 0);
		// now add chunk to chunk buffer
		howlOnDemandSystem.addTrackChunkToBuffer(new TrackChunk());
		// now chunks size is not zero
		assertFalse(howlOnDemandSystem.getChunkSize() == 0);
		// now check state should be selection state
		assertTrue(howlOnDemandSystem.getState().getStateName().equals("Selection"));
		// now change state after loading station
		Station stationAtIndexOne = howlOnDemandSystem.getStations().get(1);
		howlOnDemandSystem.loadStation(stationAtIndexOne);
		howlOnDemandSystem.getState().updateState(new Command(CommandValue.PLAY));
		// now check state should be selection state
		assertTrue(howlOnDemandSystem.getState().getStateName().equals("Playing with Buffering"));
		// now call reset method and check everything will be reset accordingly
		howlOnDemandSystem.reset();
		assertNotNull(howlOnDemandSystem.getChunkSize());
		// should empty
		assertTrue(howlOnDemandSystem.getChunkSize() == 0);
		// not null current state
		assertNotNull(howlOnDemandSystem.getState());
		// now check state should be selection state
		assertTrue(howlOnDemandSystem.getState().getStateName().equals("Selection"));
		// now check current station should null
		assertNull(howlOnDemandSystem.getCurrentStation());
	}

	/**
	 * test getChunkSize method by creating instance
	 */
	@Test
	public void getChunkSizeTest() {
		// will create new instance.
		HowlOnDemandSystem howlOnDemandSystem = HowlOnDemandSystem.getInstance();
		// initially no chunks in buffer so size is zero
		assertTrue(howlOnDemandSystem.getChunkSize() == 0);
		// now add chunk to chunk buffer
		try {
			howlOnDemandSystem.addTrackChunkToBuffer(new TrackChunk());
		} catch (MalformedTrackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// now chunks size is not zero
		assertFalse(howlOnDemandSystem.getChunkSize() == 0);
		assertTrue(howlOnDemandSystem.getChunkSize() == 1);
	}

	/**
	 * test consumeTrackChunk method by creating instance
	 */
	@Test
	public void consumeTrackChunkTest() {
		// will create new instance.
		HowlOnDemandSystem howlOnDemandSystem = HowlOnDemandSystem.getInstance();
		// initially no chunks in buffer so size is zero
		assertTrue(howlOnDemandSystem.getChunkSize() == 0);
		// now add chunk to chunk buffer
		try {
			howlOnDemandSystem.addTrackChunkToBuffer(new TrackChunk());
		} catch (MalformedTrackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// now chunks size is not zero
		assertFalse(howlOnDemandSystem.getChunkSize() == 0);
		assertTrue(howlOnDemandSystem.getChunkSize() == 1);
		// now consume it
		assertNotNull(howlOnDemandSystem.consumeTrackChunk());
		// now check again size should be zero
		assertTrue(howlOnDemandSystem.getChunkSize() == 0);
	}

	/**
	 * test consumeTrackChunk method IllegalArgumentException scenario
	 */
	@Test(expected = IllegalArgumentException.class)
	public void consumeTrackChunkIllegalArgumentExceptionTest() {
		// will create new instance.
		HowlOnDemandSystem howlOnDemandSystem = HowlOnDemandSystem.getInstance();
		// initially no chunks in buffer so size is zero
		assertTrue(howlOnDemandSystem.getChunkSize() == 0);
		// now add chunk to chunk buffer
		try {
			howlOnDemandSystem.addTrackChunkToBuffer(new TrackChunk());
		} catch (MalformedTrackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// now chunks size is not zero
		assertFalse(howlOnDemandSystem.getChunkSize() == 0);
		assertTrue(howlOnDemandSystem.getChunkSize() == 1);
		// now consume it
		assertNotNull(howlOnDemandSystem.consumeTrackChunk());
		// now check again size should be zero
		assertTrue(howlOnDemandSystem.getChunkSize() == 0);
		// now if consume again exception should be thrown
		howlOnDemandSystem.consumeTrackChunk();
	}

	/**
	 * test hasNextTrackChunk method
	 */
	@Test
	public void hasNextTrackChunkTest() {
		// will create new instance.
		HowlOnDemandSystem howlOnDemandSystem = HowlOnDemandSystem.getInstance();
		// initially no chunks in buffer so size is zero
		assertTrue(howlOnDemandSystem.getChunkSize() == 0);
		// hasNextTrackChunk should return false
		assertFalse(howlOnDemandSystem.hasNextTrackChunk());
		// now add chunk to chunk buffer
		try {
			howlOnDemandSystem.addTrackChunkToBuffer(new TrackChunk());
		} catch (MalformedTrackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// now chunks size is not zero
		assertFalse(howlOnDemandSystem.getChunkSize() == 0);
		assertTrue(howlOnDemandSystem.getChunkSize() == 1);
		// hasNextTrackChunk should return true
		assertTrue(howlOnDemandSystem.hasNextTrackChunk());
	}

	/**
	 * test addTrackChunkToBuffer method
	 */
	@Test
	public void addTrackChunkToBufferTest() {
		// will create new instance.
		HowlOnDemandSystem howlOnDemandSystem = HowlOnDemandSystem.getInstance();
		// initially no chunks in buffer so size is zero
		assertTrue(howlOnDemandSystem.getChunkSize() == 0);
		// hasNextTrackChunk should return false
		assertFalse(howlOnDemandSystem.hasNextTrackChunk());
		// now add chunk to chunk buffer
		try {
			howlOnDemandSystem.addTrackChunkToBuffer(new TrackChunk());
		} catch (MalformedTrackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// now chunks size is not zero
		assertFalse(howlOnDemandSystem.getChunkSize() == 0);
		assertTrue(howlOnDemandSystem.getChunkSize() == 1);
	}

	/**
	 * test addTrackChunkToBuffer method IllegalArgumentException scenario
	 */
	@Test(expected = IllegalArgumentException.class)
	public void addTrackChunkToBufferExceptionTest() {
		// will create new instance.
		HowlOnDemandSystem howlOnDemandSystem = HowlOnDemandSystem.getInstance();
		// initially no chunks in buffer so size is zero
		assertTrue(howlOnDemandSystem.getChunkSize() == 0);
		// now add chunks up to buffer capacity
		int bufferCapacity = 100;
		for (int i = 0; i < bufferCapacity; i++) {
			try {
				howlOnDemandSystem.addTrackChunkToBuffer(new TrackChunk());
			} catch (MalformedTrackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// now chunks size is not zero
		assertFalse(howlOnDemandSystem.getChunkSize() == 0);
		assertTrue(howlOnDemandSystem.getChunkSize() == 100);
		// now, add chunk to buffer will throw error
		try {
			howlOnDemandSystem.addTrackChunkToBuffer(new TrackChunk());
		} catch (MalformedTrackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * test bufferHasRoom method
	 */
	@Test
	public void bufferHasRoomTest() {
		// will create new instance.
		HowlOnDemandSystem howlOnDemandSystem = HowlOnDemandSystem.getInstance();
		// initially no chunks in buffer so size is zero
		assertTrue(howlOnDemandSystem.getChunkSize() == 0);
		// now buffer has room
		assertTrue(howlOnDemandSystem.bufferHasRoom());
		// now add chunks up to buffer capacity
		int bufferCapacity = 100;
		for (int i = 0; i < bufferCapacity - 1; i++) {
			try {
				howlOnDemandSystem.addTrackChunkToBuffer(new TrackChunk());
			} catch (MalformedTrackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// still buffer has room
		assertTrue(howlOnDemandSystem.bufferHasRoom());
		// add chunk to buffer
		try {
			howlOnDemandSystem.addTrackChunkToBuffer(new TrackChunk());
		} catch (MalformedTrackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// now buffer should not have room
		assertFalse(howlOnDemandSystem.bufferHasRoom());
	}

	/**
	 * test getState method by creating instance
	 */
	@Test
	public void getStateTest() {
		// will create new instance.
		HowlOnDemandSystem howlOnDemandSystem = HowlOnDemandSystem.getInstance();
		// now check state should be selection state
		assertTrue(howlOnDemandSystem.getState().getStateName().equals("Selection"));
	}

	/**
	 * test executeCommand method with change state
	 */
	@Test
	public void executeCommandTest() throws StationIOException, MalformedTrackException {
		// will create new instance.
		HowlOnDemandSystem howlOnDemandSystem = HowlOnDemandSystem.getInstance();
		// load stations
		howlOnDemandSystem.loadStationsFromFile("test-files/StationList_short.xml");
		// now check state should be selection state
		assertTrue(howlOnDemandSystem.getState().getStateName().equals("Selection"));
		// now change state using loading station and execute command
		Station stationAtIndexOne = howlOnDemandSystem.getStations().get(1);
		howlOnDemandSystem.loadStation(stationAtIndexOne);
		// only play command is allowed in the selection state
		howlOnDemandSystem.executeCommand(new Command(CommandValue.PLAY));
		// now check state should be selection state after play command
		assertTrue(howlOnDemandSystem.getState().getStateName().equals("Playing with Buffering"));
	}

	/**
	 * Reset system on completion of every test.
	 */
	@After
	public void afterTestResetEverything() {
		HowlOnDemandSystem.getInstance().reset();
	}

}
