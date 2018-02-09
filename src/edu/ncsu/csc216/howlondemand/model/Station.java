/**
 * 
 */
package edu.ncsu.csc216.howlondemand.model;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc216.audioxml.xml.AudioTrackXML;
import edu.ncsu.csc216.audioxml.xml.MalformedTrackException;
import edu.ncsu.csc216.audioxml.xml.StationXML;

/**
 * Concrete class that represents a Station loaded into the HowlOnDemand system.
 * 
 * @author kavitpatel
 */
public class Station {

	private int id;
	private String title;
	private boolean repeat;
	private boolean shuffle;
	private int color;
	private int index;
	private ArrayList<AudioTrack> playlist;

	/**
	 * Station constrctor.
	 * 
	 * @param id
	 *            id of the station.
	 * @param title
	 *            title of the station.
	 * @param color
	 *            color of the station.
	 */
	public Station(int id, String title, int color) {

		this.setId(id);
		this.setTitle(title);
		this.setColor(color);
		playlist = new ArrayList<>();
	}

	/**
	 * Station thorws MalformedTrackException.
	 * 
	 * @param stationXML
	 *            stationXML.
	 * @throws MalformedTrackException
	 *             MalformedTrackException
	 */
	public Station(StationXML stationXML) throws MalformedTrackException {
		this(stationXML.getId(), stationXML.getTitle(), stationXML.getColor());
		List<AudioTrackXML> audioTrackXMLs = stationXML.getAudioTracks().getAudioTrackXML();
		for (AudioTrackXML audioTrackXML : audioTrackXMLs) {
			playlist.add(new AudioTrack(audioTrackXML));
		}
	}

	/**
	 * Getting the id of the station.
	 * 
	 * @return the id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Setting the id of the station.
	 * 
	 * @param id
	 *            the id to set.
	 */
	public void setId(int id) {
		if (id < 0) {
			throw new IllegalArgumentException("Invalid station id passed");
		} else {
			this.id = id;
		}
	}

	/**
	 * Getting title of the station.
	 * 
	 * @return the title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setting title of the station, if tile is equal to null, then IAE is thrown
	 * with a message.
	 * 
	 * @param title
	 *            the title to set.
	 */
	public void setTitle(String title) {
		if (null == title) {
			throw new IllegalArgumentException("Invalid station title passed");
		} else {
			this.title = title;
		}
	}

	/**
	 * Getting repeat.
	 * 
	 * @return the repeat.
	 */
	public boolean getRepeat() {
		return repeat;
	}

	/**
	 * Alternate between true or false, depending on their previous value.
	 */
	public void toggleRepeat() {
		this.repeat = !repeat;
	}

	/**
	 * Alternate between true or false, depending on their previous value.
	 * 
	 * @return the shuffle getshuffle.
	 */
	public boolean getShuffle() {
		return shuffle;
	}

	/**
	 * Alternate between true or false, depending on their previous value.
	 */
	public void toggleShuffle() {
		this.shuffle = !shuffle;
	}

	/**
	 * Getting the playlist from ArrayList.
	 * 
	 * @return the playlist
	 */
	public ArrayList<AudioTrack> getPlaylist() {
		return playlist;
	}

	/**
	 * Adding new audioTrack
	 * 
	 * @param audioTrack
	 *            adding track.
	 */
	public void addAudioTrack(AudioTrack audioTrack) {
		playlist.add(audioTrack);
	}

	/**
	 * hasNextTrack should return a boolean about whether the index is less than the
	 * size of the playlist.
	 * 
	 * @return next track, checking if next track exists.
	 */
	public boolean hasNextTrack() {
		// whether the index is less than the size of the playlist
		return (index + 1) < getPlaylist().size();
	}

	/**
	 * Getting color of the station.
	 * 
	 * @return color, color of the station.
	 */
	public int getColor() {
		return color;
	}

	/**
	 * setColor should only accept integers between 0 and 5, inclusive, other
	 * integers should throw an IllegalArgumentException.
	 * 
	 * @param color
	 *            the color to set.
	 */
	public void setColor(int color) {
		// only accepts o to 5
		if (color >= 0 && color <= 5) {
			this.color = color;
		} else {
			throw new IllegalArgumentException("Invalid color code passed");
		}
	}

	/**
	 * Getting station index.
	 * 
	 * @return index, the station index.
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * The station index to set.
	 * 
	 * @param index
	 *            setting index.
	 */
	public void setIndex(int index) {
		if (index < 0) {
			throw new IllegalArgumentException("Invalid index value for station");
		} else {
			this.index = index;
		}
	}

	/**
	 * Getting currect audio track playing.
	 * 
	 * @return current audio track.
	 */
	public AudioTrack getCurrentAudioTrack() {
		// get current audio track
		return playlist.get(index);
	}

	/**
	 * Reset should make the index return to 0 and repeat and shuffle to false.
	 * resets audio track.
	 */
	public void reset() {
		// reset the values of station
		index = 0;
		repeat = false;
		shuffle = false;
	}

	/**
	 * toString that return id,title,repeat,shuffle,color,index and playlist.
	 */
	@Override
	public String toString() {
		return "Station \"" + this.getTitle() + "\"";
	}

}
