package edu.ncsu.csc216.howlondemand.model;

import edu.ncsu.csc216.audioxml.xml.MalformedTrackException;

/**
 * Abstract class that represents a Multimedia, which AudioTrack class inherits
 * from.
 * 
 * @author kavitpatel
 */
public abstract class Multimedia {

	/** id. */
	protected int id;
	/** string. */
	protected String title;

	/**
	 * Sets id and title.
	 * 
	 * @param id
	 *            of the multimedia.
	 * @param title
	 *            of the multimedia.
	 */
	public Multimedia(int id, String title) {
		setId(id);
		setTitle(title);
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * The multimedia id to set.
	 * 
	 * @param id
	 *            id.
	 */
	public void setId(int id) {
		if (id < 0) {
			throw new IllegalArgumentException("invalid value for multimedia id");
		}
		this.id = id;
	}

	/**
	 * Multimedia gets title.
	 * 
	 * @return the title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * The multimedia title to set.
	 * 
	 * @param title
	 *            multimedia title.
	 */
	public void setTitle(String title) {
		if (null == title || title.isEmpty()) {
			throw new IllegalArgumentException("invalid value for multimedia title");
		}
		this.title = title;
	}

	/**
	 * Abstract getNextChunk.
	 * 
	 * @return TrackChunk.
	 */
	public abstract TrackChunk getNextChunk();

	/**
	 * Boolean statement hasNextChunk.
	 * 
	 * @return boolean.
	 */
	public abstract boolean hasNextChunk();

	/**
	 * Abstract addChunk string chunk throws MalformedTrackException.
	 * 
	 * @param chunk
	 *            chunk.
	 * @throws MalformedTrackException
	 *             MalformedTrackException
	 */
	public abstract void addChunk(TrackChunk chunk) throws MalformedTrackException;

	/**
	 * Abstract toString to parse.
	 * 
	 * @return toString.
	 */
	public abstract String toString();

}
