/**
 * 
 */
package edu.ncsu.csc216.howlondemand.model;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc216.audioxml.xml.AudioTrackXML;
import edu.ncsu.csc216.audioxml.xml.MalformedTrackException;

/**
 * The AudioTrack class represents the audio track artist, current chuckIndex,
 * and chunks collection of TrackChunks which is required every time
 * HowlOnDemandSystem requests the next track chunk to load into the buffer.
 * 
 * @author kavitpatel
 */
public class AudioTrack extends Multimedia {

	/** Initializes int chunkIndex. */
	private int chunkIndex;
	/** Initializes artist String. */
	private String artist;
	/** Initliaze ArrayList chunk. */
	private ArrayList<TrackChunk> chunks;

	/**
	 * AudioTrack should pull values from the AudioTrackXML parameter.
	 * 
	 * @param id
	 *            id of the track.
	 * @param title
	 *            title of the track.
	 * @param artist
	 *            artist of the track.
	 */
	public AudioTrack(int id, String title, String artist) {
		super(id, title);
		this.artist = artist;
		chunks = new ArrayList<>();
		this.chunkIndex = 0;
	}

	/**
	 * AudioTrackXML as list.
	 * 
	 * @param audioTrackXML
	 *            audioTrackXML
	 * @throws MalformedTrackException
	 *             MalformedTrackException
	 */
	public AudioTrack(AudioTrackXML audioTrackXML) throws MalformedTrackException {
		this(audioTrackXML.getId(), audioTrackXML.getTitle(), audioTrackXML.getArtist());
		List<String> trackChunks = audioTrackXML.getTrackChunks().getChunk();
		for (String trackChunk : trackChunks) {
			chunks.add(new TrackChunk(trackChunk));
		}
		this.chunkIndex = 0;
	}

	/**
	 * Sets the AudioTrack artist.
	 * 
	 * @return the artist
	 */
	public String getArtist() {
		return artist;
	}

	/**
	 * The audio track artist to set.
	 * 
	 * @param artist
	 *            artist of the song.
	 */
	public void setArtist(String artist) {
		if (null == artist) {
			throw new IllegalArgumentException();
		}
		this.artist = artist;
	}

	/**
	 * Gets ChunkIndex upon calling.
	 * 
	 * @return the chunkIndex
	 */
	public int getChunkIndex() {
		return chunkIndex;
	}

	/**
	 * Sets ChunkIndex.
	 * 
	 * @param chunkIndex
	 *            the chunkIndex to set
	 */
	public void setChunkIndex(int chunkIndex) {
		if (chunkIndex < 0 || chunkIndex > getTrackChunkSize()) {
			throw new IllegalArgumentException();
		}
		this.chunkIndex = chunkIndex;
	}

	/**
	 * Gets TrackChunkSize, pieces of chunk that Track gets broken down into.
	 * 
	 * @return trackChunkSize
	 */
	public int getTrackChunkSize() {
		return chunks.size();
	}

	/**
	 * Checks getChunkIndex, throws IllegalArgumentException else it increments the
	 * index size and returns it.
	 */
	@Override
	public TrackChunk getNextChunk() {
		if (!(getChunkIndex() < getTrackChunkSize())) {
			throw new IllegalArgumentException();
		} else {
			TrackChunk trackChunk = chunks.get(getChunkIndex());
			chunkIndex++;
			return trackChunk;
		}
	}

	/**
	 * Checks if the chunkIndex's size is less that TrackChunkSize as then returns
	 * it as true / false boolean statement.
	 */
	@Override
	public boolean hasNextChunk() {
		if (chunkIndex < getTrackChunkSize()) {
			return true;
		}
		return false;
	}

	/**
	 * If TrackChunk chunk is equal to null then it throws MalformedTrackException
	 * exception, if not then it gets added.
	 */
	@Override
	public void addChunk(TrackChunk chunk) throws MalformedTrackException {
		if (chunk == null)
			throw new MalformedTrackException();
		chunks.add(chunk);

	}

	/**
	 * AudioTrack string returns the id + title + arrtist.
	 */
	@Override
	public String toString() {
		return "AudioTrack [id=" + super.getId() + ", title=" + super.getTitle() + ", artist=" + artist + "]";
	}

}
