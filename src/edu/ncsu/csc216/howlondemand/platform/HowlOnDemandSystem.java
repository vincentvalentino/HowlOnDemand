/**
 *
 */
package edu.ncsu.csc216.howlondemand.platform;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.ncsu.csc216.audioxml.xml.MalformedTrackException;
import edu.ncsu.csc216.audioxml.xml.StationIOException;
import edu.ncsu.csc216.audioxml.xml.StationXML;
import edu.ncsu.csc216.audioxml.xml.StationsReader;
import edu.ncsu.csc216.howlondemand.model.AudioTrack;
import edu.ncsu.csc216.howlondemand.model.Station;
import edu.ncsu.csc216.howlondemand.model.TrackChunk;
import edu.ncsu.csc216.howlondemand.platform.enums.CommandValue;

/**
 * Concrete class that maintains the Station list and handles CommandValues from
 * the GUI and internal methods.
 * 
 * @author kavitpatel
 */
public class HowlOnDemandSystem {
	/**
	 * Buffer capacity int.
	 */
	public static final int BUFFER_CAPACITY = 100;
	/**
	 * Station capacity int.
	 */
	public static final int STATION_CAPACITY = 9;
	/**
	 * Selection name string.
	 */
	public static final String SELECTION_NAME = "Selection";
	/**
	 * Playwithbuffering name string.
	 */
	public static final String PLAYWITHBUFFERING_NAME = "Playing with Buffering";
	/**
	 * Playwithoutbuffering name string.
	 */
	public static final String PLAYWITHOUTBUFFERING_NAME = "Playing without Buffering";
	/**
	 * Stopwithoutbuffering, stopped with buffering name string.
	 */
	public static final String STOPWITHBUFFERING_NAME = "Stopped with Buffering";
	/**
	 * Stopwithoutbuffering, stopped without buffering name string.
	 */
	public static final String STOPWITHOUTBUFFERING_NAME = "Stopped without Buffering";
	/**
	 * Quit name string.
	 */
	public static final String QUIT_NAME = "Quit";
	/**
	 * Finished name String.
	 */
	public static final String FINISHED_NAME = "Finished";
	/**
	 * Private currentStation.
	 */
	private Station currentStation;
	/**
	 * Station ArrayList for the respective stations.
	 */
	private ArrayList<Station> stations;
	/**
	 * TrackChunk chunks in a ArrayList.
	 */
	private ArrayList<TrackChunk> chunks;

	/**
	 * HowlOnDemandSystemState state.
	 */
	private HowlOnDemandSystemState state;
	/**
	 * Private constant corresponding to selectionState.
	 */
	private final HowlOnDemandSystemState selectionState = new SelectionState();
	/**
	 * Private constant corresponding to playWithBufferingState.
	 */
	private final HowlOnDemandSystemState playWithBufferingState = new PlayWithBufferingState();
	/**
	 * Private constant corresponding to playWithoutBufferingState.
	 */
	private final HowlOnDemandSystemState playWithoutBufferingState = new PlayWithoutBufferingState();
	/**
	 * Private constant corresponding to stopWithBufferingState.
	 */
	private final HowlOnDemandSystemState stopWithBufferingState = new StopWithBufferingState();
	/**
	 * Private constant corresponding to stopWithoutBufferingState.
	 */
	private final HowlOnDemandSystemState stopWithoutBufferingState = new StopWithoutBufferingState();
	/**
	 * Private constant corresponding to quitState.
	 */
	private final HowlOnDemandSystemState quitState = new QuitState();
	/**
	 * Private constant corresponding to finishedState.
	 */
	private final HowlOnDemandSystemState finishedState = new FinishedState();
	/**
	 * Private static howlonDemandSystem.
	 */
	private static HowlOnDemandSystem howlOnDemandSystem;

	/**
	 * Initialize chunks and stations and set initial state to selectionState.
	 * 
	 * @singletonPattern prvents any other class from instanceating.
	 */
	private HowlOnDemandSystem() {
		state = selectionState;
		stations = new ArrayList<>(STATION_CAPACITY);
		chunks = new ArrayList<>(BUFFER_CAPACITY);
		// Initialize buffer

	}

	/**
	 * Checks to see if the singleton field has been created, if not, it should
	 * create a new instance of HowlOnDeamndSystem, then return the singleton.
	 * 
	 * @return singleton instance of HowlOnDemandSystem.
	 */
	public static HowlOnDemandSystem getInstance() {
		if (null == howlOnDemandSystem) {
			howlOnDemandSystem = new HowlOnDemandSystem();
		}
		return howlOnDemandSystem;
	}

	/**
	 * Passes the String parameter to the StationsReader class provided through the
	 * AudioXML library.
	 * 
	 * @param file
	 *            to load stations.
	 * @throws StationIOException
	 *             error loading file
	 * @throws MalformedTrackException
	 *             error loading file
	 */
	public void loadStationsFromFile(final String file) throws StationIOException, MalformedTrackException {
		final StationsReader stationReader = new StationsReader(file);
		final List<StationXML> stationXMLs = stationReader.getStations();
		for (final StationXML stationXML : stationXMLs) {
			final Station station = new Station(stationXML);
			stations.add(station);
		}
	}

	/**
	 * Provides list of stations.
	 * 
	 * @return list of the stations.
	 */
	public ArrayList<Station> getStations() {
		return this.stations;
	}

	/**
	 * Load station and if equal to null throws IAE.
	 * 
	 * @param station
	 *            to be loaded.
	 */
	public void loadStation(final Station station) {
		if (null == station) {
			throw new IllegalArgumentException("Invalid station passed");
		}
		// Changed current station to station passed.
		this.currentStation = station;
		// Now explicitly change station's index to 0.
		getCurrentStation().setIndex(0);
	}

	/**
	 * Provides information to the GUI getting current station.
	 * 
	 * @return current station.
	 */
	public Station getCurrentStation() {
		return this.currentStation;
	}

	/**
	 * Tradiontal getter method gettin currentAudioTrack.
	 * 
	 * @return current audio track of system/station.
	 */
	public AudioTrack getCurrentAudioTrack() {
		return currentStation.getCurrentAudioTrack();
	}

	/**
	 * Reset the system.
	 */
	public void reset() {
		// Return system to selection state.
		state = selectionState;
		// Empty the buffer.
		chunks.clear();
		if (null != getCurrentStation()) {
			getCurrentStation().reset();
			this.currentStation = null;
		}
		stations.clear();
	}

	/**
	 * Gets chunk size.
	 * 
	 * @return track chunk size
	 */
	public int getChunkSize() {
		// Returns current numbers of chunks loaded into the buffer.
		return this.chunks.size();
	}

	/**
	 * Remove the chunk at the 0 index only if the buffer has at least 1 ,
	 * trackChuck stored in it, otherwise it should throw an IAE.
	 * 
	 * @return track chunk to be consumed for system.
	 */
	public TrackChunk consumeTrackChunk() {
		if (getChunkSize() >= 1) {
			return chunks.remove(0);
		} else {
			throw new IllegalArgumentException("no track in buffer to be consumed");
		}
	}

	/**
	 * Should return true if the buffer is not empty, false otherwise.
	 * 
	 * @return is there next track chunk exist in buffer?
	 */
	public boolean hasNextTrackChunk() {
		// Should return true if the buffer is not empty, false otherwise.
		return chunks.isEmpty() ? false : true;
	}

	/**
	 * Add TrackChunk to the end of the buffer; if the buffer does not have room to
	 * add the additional TrackChunk, the system should IAE.
	 * 
	 * @param trackChunk
	 *            add TrackChunk if full throw IAE.
	 */
	public void addTrackChunkToBuffer(final TrackChunk trackChunk) {
		if (bufferHasRoom()) {
			chunks.add(trackChunk);
		} else {
			throw new IllegalArgumentException("buffer is full");
		}
	}

	/**
	 * Should return true if the buffer list has room to add additional TrackChucks
	 * addTrackChunkToBuffer should attempt to add the TrackChunk parameter passed
	 * to the method to the end of the buffer, if the buffer does not have room to
	 * add the additional TrackChunk, the system should throw a IAE.
	 * 
	 * @return Is buffer full ?
	 */
	public boolean bufferHasRoom() {
		// Return true if the buffer list has room to add additional TrackChuck.
		return getChunkSize() < BUFFER_CAPACITY ? true : false;
	}

	/**
	 * Gets state.
	 * 
	 * @return system state
	 */
	public HowlOnDemandSystemState getState() {
		// It will return the current state of the system.
		return state;
	}

	/**
	 * Provides the GUI with a method to call when the user presses a button. This
	 * method then passes the interaction to the internal FSM.
	 * 
	 * @param command
	 *            to be executed called from gui or internal FSM
	 */
	public void executeCommand(final Command command) {
		// Simply update the state as per command pressed.
		state.updateState(command);
	}

	/**
	 * Should be appropriately built and include the FSM's current state and the
	 * current size of the chunks collection.
	 */
	@Override
	public String toString() {
		return "HowlOnDemandSystem [state=" + getState().getStateName() + ", chunks.size()=" + getChunkSize() + "]";

	}

	/**
	 * Concrete class that represents the Selection state of the HowlOnDemand FSM.
	 * 
	 * @author kavitpatel
	 */
	private class SelectionState implements HowlOnDemandSystemState {

		@Override
		public void updateState(final Command command) {
			final CommandValue value = command.getCommand();

			switch (value) {
			case PLAY:
				// Obtain the current AudioTrack's next TrackChunk.
				final TrackChunk trackChunk = howlOnDemandSystem.getCurrentAudioTrack().getNextChunk();
				// Add it to the system's buffer.
				howlOnDemandSystem.addTrackChunkToBuffer(trackChunk);
				// State change to the playWithBufferingState.
				state = playWithBufferingState;
				break;

			default:
				throw new UnsupportedOperationException(value + " command is not supported in " + getStateName());
			}

		}

		@Override
		public String getStateName() {
			return SELECTION_NAME;
		}
	}

	/**
	 * Concrete class that represents the Play With Buffering state of the
	 * HowlOnDemand FSM.
	 * 
	 * @author kavitpatel
	 */
	private class PlayWithBufferingState implements HowlOnDemandSystemState {

		@Override
		public void updateState(final Command command) {
			final CommandValue value = command.getCommand();
			switch (value) {

			case BUFFERING:
				/*
				 * 1) If the currently loaded AudioTrack has another TrackChunk (via
				 * hasNextChunk), grab the next chunk and attempt to add it to the buffer.
				 */
				// obtain the current AudioTrack
				final AudioTrack currentAudioTrack = howlOnDemandSystem.getCurrentAudioTrack();
				// hash track chunk
				final boolean hashTrackChunk = currentAudioTrack.hasNextChunk();
				if (hashTrackChunk) {
					// add it to the system's buffer
					howlOnDemandSystem.addTrackChunkToBuffer(currentAudioTrack.getNextChunk());
				}
				/*
				 * 2) If the buffer reaches CAPACITY, or if the AudioTrack does not have any
				 * additional chunks to load, set the state to playWithoutBufferingState
				 */
				// now check buffer reaches the capacity and hash more chunks in audio track to
				// load
				if (!bufferHasRoom() || !currentAudioTrack.hasNextChunk()) {
					// state change to the playWithoutBufferingState.
					state = playWithoutBufferingState;
				}
				/* 3) If the buffer is not empty, call the consumeTrackChuck method */
				if (getChunkSize() > 0) {
					howlOnDemandSystem.consumeTrackChunk();
				}
				/*
				 * 4) if the buffer is empty and the AudioTrack does not have any more
				 * TrackChucks, change the state to finishedState
				 */
				if (getChunkSize() == 0 && !currentAudioTrack.hasNextChunk()) {
					// change state to finished state
					state = finishedState;
				}
				break;

			case NOT_BUFFERING:
				state = playWithoutBufferingState;
				/*
				 * Treat the NOT_BUFFERING Command the same as BUFFERING, except do not attempt
				 * to add new TrackChunks into the buffer. 1) If the currently loaded AudioTrack
				 * has another TrackChunk (via hasNextChunk), grab the next chunk
				 */
				// Obtain the current AudioTrack.
				final AudioTrack currentAudioTrackForNotBufferingCase = howlOnDemandSystem.getCurrentAudioTrack();
				// hash track chunk
				currentAudioTrackForNotBufferingCase.hasNextChunk();
				/*
				 * 2) If the buffer reaches CAPACITY, or if the AudioTrack does not have any
				 * additional chunks to load, set the state to playWithoutBufferingState
				 */
				// Now check buffer reaches the capacity and hash more chunks in audio track to
				// load.

				/* 3) If the buffer is not empty, call the consumeTrackChuck method */
				if (getChunkSize() > 0) {
					howlOnDemandSystem.consumeTrackChunk();
				}
				/*
				 * 4) if the buffer is empty and the AudioTrack does not have any more
				 * TrackChucks, change the state to finishedState
				 */
				if (getChunkSize() == 0 && !currentAudioTrackForNotBufferingCase.hasNextChunk()) {
					// Change state to finished state.
					state = finishedState;
				}
				break;

			case STOP:
				// Change the state to stopWithBufferingState.
				state = stopWithBufferingState;
				break;

			case RETURN:
				// change the state to quitState
				state = quitState;
				break;

			case SKIP_FORWARD:
				// will behave in 4 separate ways
				/*
				 * If the Station is set to shuffle, it will select a new randomly generated
				 * index
				 */
				if (currentStation.getShuffle()) {
					final int numbersOfAudioTracksOfCurrentStation = currentStation.getPlaylist().size();
					final int indexOfCurrentAudioTrack = currentStation.getPlaylist()
							.indexOf(currentStation.getCurrentAudioTrack());
					/*
					 * for generate random index 'new Random().nextInt(High-Low) + Low' top is
					 * exclusive;
					 */
					int randomGeneratedIndex = new Random().nextInt(numbersOfAudioTracksOfCurrentStation - 0) + 0;
					// should not same index
					while (randomGeneratedIndex == indexOfCurrentAudioTrack) {
						randomGeneratedIndex = new Random().nextInt(numbersOfAudioTracksOfCurrentStation - 0) + 0;
					}
					// set this random index to station index
					currentStation.setIndex(randomGeneratedIndex);
					// set the newly loaded AudioTrack's chunkIndex to 0
					currentStation.getCurrentAudioTrack().setChunkIndex(0);
				} else if (currentStation.hasNextTrack()) {
					/*
					 * If the Station has another track then increment the index via the setIndex
					 * method
					 */
					currentStation.setIndex(currentStation.getIndex() + 1);
					// set the newly loaded AudioTrack's chunkIndex to 0
					currentStation.getCurrentAudioTrack().setChunkIndex(0);
					// clear the chunks collection
					howlOnDemandSystem.chunks.clear();
				} else if (!currentStation.hasNextTrack() && currentStation.getRepeat()) {
					/*
					 * If the Station does not have another track and is set to repeat, set the
					 * index to 0
					 */
					currentStation.setIndex(0);
					// set the currently loaded AudioTrack's chunkIndex to 0
					currentStation.getCurrentAudioTrack().setChunkIndex(0);
					// clear the chunks collection
					howlOnDemandSystem.chunks.clear();
				} else {
					// change the state to finishedState
					state = finishedState;
				}
				break;

			case SKIP_BACKWARD:
				final Station currentStationOfSystem = howlOnDemandSystem.getCurrentStation();
				// If the currently loaded Station's index is greater than or equal to 1
				if (currentStationOfSystem.getIndex() >= 1) {
					// decrement the index
					currentStationOfSystem.setIndex(currentStationOfSystem.getIndex() - 1);
					// set the currently loaded AudioTrack's chunkIndex to 0
					currentStationOfSystem.getCurrentAudioTrack().setChunkIndex(0);
					// clear the chunks collection
					howlOnDemandSystem.chunks.clear();
				}
				break;

			default:
				throw new UnsupportedOperationException(value + " command is not supported in " + getStateName());
			}
		}

		@Override
		public String getStateName() {
			return PLAYWITHBUFFERING_NAME;
		}
	}

	/**
	 * Concrete class that represents the Play Without Buffering state of the
	 * HowlOnDemand FSM.
	 * 
	 * @author kavitpatel
	 */
	private class PlayWithoutBufferingState implements HowlOnDemandSystemState {

		@Override
		public void updateState(final Command command) {
			final CommandValue value = command.getCommand();
			switch (value) {

			case BUFFERING:
				/*
				 * do something only if the buffer is not full
				 */
				// If the buffer is not full and not empty
				if (bufferHasRoom() && getChunkSize() > 0) {
					// consumeTrackChuck;
					consumeTrackChunk();
					// obtain the current AudioTrack
					final AudioTrack currentAudioTrack = howlOnDemandSystem.getCurrentAudioTrack();
					// hash track chunk
					final boolean hashTrackChunk = currentAudioTrack.hasNextChunk();
					if (hashTrackChunk) {
						// add it to the system's buffer
						howlOnDemandSystem.addTrackChunkToBuffer(currentAudioTrack.getNextChunk());
						state = playWithoutBufferingState;
					}
				}
				break;

			case NOT_BUFFERING:
				// If the buffer is not empty
				if (getChunkSize() > 0) {
					// consumeTrackChuck
					consumeTrackChunk();
				}
				// If the buffer is empty and AudioTrack does not have another chunk
				if (getChunkSize() == 0 && !howlOnDemandSystem.getCurrentAudioTrack().hasNextChunk()) {
					// set the state to finishedState
					state = finishedState;
				}
				break;

			case STOP:
				// change the state to stopWithoutBufferingState
				state = stopWithoutBufferingState;
				break;

			case RETURN:
				// change the state to quitState
				state = quitState;
				break;

			case SKIP_FORWARD:
				// will behave in 4 separate ways
				/*
				 * If the Station is set to shuffle, it will select a new randomly generated
				 * index
				 */
				if (currentStation.getShuffle()) {
					final int numbersOfAudioTracksOfCurrentStation = currentStation.getPlaylist().size();
					final int indexOfCurrentAudioTrack = currentStation.getPlaylist()
							.indexOf(currentStation.getCurrentAudioTrack());
					/*
					 * for generate random index 'new Random().nextInt(High-Low) + Low' top is
					 * exclusive;
					 */
					int randomGeneratedIndex = new Random().nextInt(numbersOfAudioTracksOfCurrentStation - 0) + 0;
					// should not same index
					while (randomGeneratedIndex == indexOfCurrentAudioTrack) {
						randomGeneratedIndex = new Random().nextInt(numbersOfAudioTracksOfCurrentStation - 0) + 0;
					}
					// set this random index to station index
					currentStation.setIndex(randomGeneratedIndex);
					// set the newly loaded AudioTrack's chunkIndex to 0
					currentStation.getCurrentAudioTrack().setChunkIndex(0);
				} else if (currentStation.hasNextTrack()) {
					/*
					 * If the Station has another track then increment the index via the setIndex
					 * method
					 */
					currentStation.setIndex(currentStation.getIndex() + 1);
					// set the newly loaded AudioTrack's chunkIndex to 0
					currentStation.getCurrentAudioTrack().setChunkIndex(0);
					// clear the chunks collection
					howlOnDemandSystem.chunks.clear();
				} else if (!currentStation.hasNextTrack() && currentStation.getRepeat()) {
					/*
					 * If the Station does not have another track and is set to repeat, set the
					 * index to 0
					 */
					currentStation.setIndex(0);
					// set the currently loaded AudioTrack's chunkIndex to 0
					currentStation.getCurrentAudioTrack().setChunkIndex(0);
					// clear the chunks collection
					howlOnDemandSystem.chunks.clear();
				} else {
					// change the state to finishedState
					state = finishedState;
				}
				break;

			case SKIP_BACKWARD:
				final Station currentStationOfSystem = howlOnDemandSystem.getCurrentStation();
				// If the currently loaded Station's index is greater than or equal to 1
				if (currentStationOfSystem.getIndex() >= 1) {
					// decrement the index
					currentStationOfSystem.setIndex(currentStationOfSystem.getIndex() - 1);
					// set the currently loaded AudioTrack's chunkIndex to 0
					currentStationOfSystem.getCurrentAudioTrack().setChunkIndex(0);
					// clear the chunks collection
					howlOnDemandSystem.chunks.clear();
				}
				break;

			default:
				throw new UnsupportedOperationException(value + " command is not supported in " + getStateName());
			}
		}

		@Override
		public String getStateName() {
			return PLAYWITHOUTBUFFERING_NAME;
		}
	}

	/**
	 * Concrete class that represents the Stop With Buffering state of the
	 * HowlOnDemand FSM.
	 * 
	 * @author kavitpatel
	 */
	private class StopWithBufferingState implements HowlOnDemandSystemState {

		@Override
		public void updateState(final Command command) {
			final CommandValue value = command.getCommand();
			switch (value) {

			case BUFFERING:
				/*
				 * 1) If the currently loaded AudioTrack has another TrackChunk (via
				 * hasNextChunk), grab the next chunk and attempt to add it to the buffer
				 */
				// obtain the current AudioTrack
				final AudioTrack currentAudioTrack = howlOnDemandSystem.getCurrentAudioTrack();
				// hash track chunk
				final boolean hashTrackChunk = currentAudioTrack.hasNextChunk();
				if (hashTrackChunk) {
					// add it to the system's buffer
					howlOnDemandSystem.addTrackChunkToBuffer(currentAudioTrack.getNextChunk());
				}
				/*
				 * 2) If the buffer reaches CAPACITY, or if the AudioTrack does not have any
				 * additional chunks to load, set the state to playWithoutBufferingState
				 */
				// now check buffer reaches the capacity and hash more chunks in audio track to
				// load
				if (!bufferHasRoom() || !currentAudioTrack.hasNextChunk()) {
					// state change to the playWithoutBufferingState.
					state = playWithoutBufferingState;
				}
				break;

			case NOT_BUFFERING:
				// change the state to StopWithoutBufferingState
				state = stopWithoutBufferingState;
				break;

			case PLAY:
				// change the state to PlayWithBufferingState
				state = playWithBufferingState;
				break;

			case RETURN:
				// change the state to quitState
				state = quitState;
				break;

			case SKIP_FORWARD:
				/* If the Station have another track */
				if (currentStation.hasNextTrack()) {
					// increment the index
					currentStation.setIndex(currentStation.getIndex() + 1);
					// if shuffle flag set
					if (currentStation.getShuffle()) {
						final int numbersOfAudioTracksOfCurrentStation = currentStation.getPlaylist().size();
						final int indexOfCurrentAudioTrack = currentStation.getPlaylist()
								.indexOf(currentStation.getCurrentAudioTrack());
						/*
						 * for generate random index 'new Random().nextInt(High-Low) + Low' top is
						 * exclusive;
						 */
						int randomGeneratedIndex = new Random().nextInt(numbersOfAudioTracksOfCurrentStation - 0) + 0;
						// should not same index
						while (randomGeneratedIndex == indexOfCurrentAudioTrack) {
							randomGeneratedIndex = new Random().nextInt(numbersOfAudioTracksOfCurrentStation - 0) + 0;
						}
						// set this random index to station index
						currentStation.setIndex(randomGeneratedIndex);
					}
					// if repeat flag set
					// if (currentStation.getRepeat()) {
					/* set index to current index ---- modified per under standing */
					// }
					// set the currently loaded AudioTrack's chunkIndex to 0
					currentStation.getCurrentAudioTrack().setChunkIndex(0);
					// clear the chunks collection
					howlOnDemandSystem.chunks.clear();
				} else {
					/* If the Station does not have another track */
					// change the state to finishedState
					state = finishedState;
				}
				break;

			case SKIP_BACKWARD:
				final Station currentStationOfSystem = howlOnDemandSystem.getCurrentStation();
				// If the currently loaded Station's index is greater than or equal to 1
				if (currentStationOfSystem.getIndex() >= 1) {
					// decrement the index
					currentStationOfSystem.setIndex(currentStationOfSystem.getIndex() - 1);
					// set the currently loaded AudioTrack's chunkIndex to 0
					currentStationOfSystem.getCurrentAudioTrack().setChunkIndex(0);
					// clear the chunks collection
					howlOnDemandSystem.chunks.clear();
				}
				break;

			default:
				throw new UnsupportedOperationException(value + " command is not supported in " + getStateName());
			}
		}

		@Override
		public String getStateName() {
			return STOPWITHBUFFERING_NAME;
		}
	}

	/**
	 * Concrete class that represents the Stop Without Buffering state of the
	 * HowlOnDemand FSM.
	 * 
	 * @author kavitpatel
	 */
	private class StopWithoutBufferingState implements HowlOnDemandSystemState {

		@Override
		public void updateState(final Command command) {
			final CommandValue value = command.getCommand();
			switch (value) {

			case BUFFERING:
				// state change to stopWithBufferingState
				state = stopWithBufferingState;
				break;

			case NOT_BUFFERING:
				// change the state to StopWithoutBufferingState
				state = stopWithoutBufferingState;
				break;

			case PLAY:
				// If the buffer is not empty
				if (getChunkSize() > 0) {
					// consumeTrackChuck
					consumeTrackChunk();
					// change the state to playWithoutBufferingState
					state = playWithoutBufferingState;
				}
				// If the buffer is empty and AudioTrack does not have another chunk
				if (getChunkSize() == 0 && !howlOnDemandSystem.getCurrentAudioTrack().hasNextChunk()) {
					// set the state to finishedState
					state = finishedState;
				}
				break;

			case RETURN:
				// change the state to quitState
				state = quitState;
				break;

			case SKIP_FORWARD:
				/* If the Station have another track */
				if (currentStation.hasNextTrack()) {
					// increment the index
					currentStation.setIndex(currentStation.getIndex() + 1);
					// if shuffle flag set
					if (currentStation.getShuffle()) {
						final int numbersOfAudioTracksOfCurrentStation = currentStation.getPlaylist().size();
						final int indexOfCurrentAudioTrack = currentStation.getPlaylist()
								.indexOf(currentStation.getCurrentAudioTrack());
						/*
						 * for generate random index 'new Random().nextInt(High-Low) + Low' top is
						 * exclusive;
						 */
						int randomGeneratedIndex = new Random().nextInt(numbersOfAudioTracksOfCurrentStation - 0) + 0;
						// should not same index
						while (randomGeneratedIndex == indexOfCurrentAudioTrack) {
							randomGeneratedIndex = new Random().nextInt(numbersOfAudioTracksOfCurrentStation - 0) + 0;
						}
						// set this random index to station index
						currentStation.setIndex(randomGeneratedIndex);
					}
					// if repeat flag set
					// if (currentStation.getRepeat()) {
					/* set index to current index ---- modified per under standing */
					// }
					// set the currently loaded AudioTrack's chunkIndex to 0
					currentStation.getCurrentAudioTrack().setChunkIndex(0);
					// clear the chunks collection
					howlOnDemandSystem.chunks.clear();
				} else {
					/* If the Station does not have another track */
					// change the state to finishedState
					state = finishedState;
				}
				break;

			case SKIP_BACKWARD:
				final Station currentStationOfSystem = howlOnDemandSystem.getCurrentStation();
				// If the currently loaded Station's index is greater than or equal to 1
				if (currentStationOfSystem.getIndex() >= 1) {
					// decrement the index
					currentStationOfSystem.setIndex(currentStationOfSystem.getIndex() - 1);
					// set the currently loaded AudioTrack's chunkIndex to 0
					currentStationOfSystem.getCurrentAudioTrack().setChunkIndex(0);
					// clear the chunks collection
					howlOnDemandSystem.chunks.clear();
				}
				break;

			default:
				throw new UnsupportedOperationException(value + " command is not supported in " + getStateName());
			}
		}

		@Override
		public String getStateName() {
			return STOPWITHOUTBUFFERING_NAME;
		}
	}

	/**
	 * Concrete class that represents the Quit state of the HowlOnDemand FSM.
	 * 
	 * @author kavitpatel
	 */
	private class QuitState implements HowlOnDemandSystemState {

		@Override
		public void updateState(final Command command) {
			final CommandValue value = command.getCommand();
			switch (value) {

			case SELECT_STATION:
				// only action the QuitState should do is to reset
				howlOnDemandSystem.reset();
				break;

			default:
				throw new UnsupportedOperationException(value + " command is not supported in " + getStateName());
			}
		}

		@Override
		public String getStateName() {
			return QUIT_NAME;
		}
	}

	/**
	 * Concrete class that represents the Finished state of the HowlOnDemand FSM.
	 * 
	 * @author kavitpatel
	 */
	private class FinishedState implements HowlOnDemandSystemState {

		@Override
		public void updateState(final Command command) {
			final CommandValue value = command.getCommand();
			switch (value) {

			case FINISH_TRACK:
				// will behave in 3 separate ways

				/*
				 * If the Station is set to shuffle, it will select a new randomly generated
				 * index
				 */
				if (currentStation.getShuffle()) {
					final int numbersOfAudioTracksOfCurrentStation = currentStation.getPlaylist().size();
					final int indexOfCurrentAudioTrack = currentStation.getPlaylist()
							.indexOf(currentStation.getCurrentAudioTrack());
					/*
					 * for generate random index 'new Random().nextInt(High-Low) + Low' top is
					 * exclusive;
					 */
					int randomGeneratedIndex = new Random().nextInt(numbersOfAudioTracksOfCurrentStation - 0) + 0;
					// should not same index
					while (randomGeneratedIndex == indexOfCurrentAudioTrack) {
						randomGeneratedIndex = new Random().nextInt(numbersOfAudioTracksOfCurrentStation - 0) + 0;
					}
					// set this random index to station index
					currentStation.setIndex(randomGeneratedIndex);
					// set the newly loaded AudioTrack's chunkIndex to 0
					currentStation.getCurrentAudioTrack().setChunkIndex(0);
				} else if (currentStation.hasNextTrack()) {
					/*
					 * If the Station has another track then increment the index via the setIndex
					 * method
					 */
					currentStation.setIndex(currentStation.getIndex() + 1);
					// set the newly loaded AudioTrack's chunkIndex to 0
					currentStation.getCurrentAudioTrack().setChunkIndex(0);
					// clear the chunks collection
					howlOnDemandSystem.chunks.clear();
				} else if (!currentStation.hasNextTrack() && currentStation.getRepeat()) {
					/*
					 * If the Station does not have another track and is set to repeat, set the
					 * index to 0
					 */
					currentStation.setIndex(0);
					// set the currently loaded AudioTrack's chunkIndex to 0
					currentStation.getCurrentAudioTrack().setChunkIndex(0);
					// clear the chunks collection
					howlOnDemandSystem.chunks.clear();
				}
				/*
				 * Regardless of which scenario occurs, the state should change to
				 * playWithBufferingState
				 */
				state = playWithBufferingState;
				break;

			case FINISH_STATION:
				// set the state to finishedState
				state = finishedState;
				break;

			case RETURN:
				// system should reset
				howlOnDemandSystem.reset();
				// set the state to quitState
				state = quitState;
				break;

			default:
				throw new UnsupportedOperationException(value + " command is not supported in " + getStateName());
			}
		}

		@Override
		public String getStateName() {
			return FINISHED_NAME;
		}
	}
}
