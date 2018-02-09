package edu.ncsu.csc216.howlondemand.model;

import java.util.regex.Pattern;

import edu.ncsu.csc216.audioxml.xml.MalformedTrackException;

/**
 * Concrete class that represents a TrackChunk loaded into the AudioTrack class.
 * 
 * @author kavitpatel
 */
public class TrackChunk {

	private String chunk;

	/**
	 * Default constructor.
	 * 
	 * @throws MalformedTrackException
	 *             MalformedTrackException
	 */
	public TrackChunk() throws MalformedTrackException {
		this("00000000");
	}

	/**
	 * TrackChunkString
	 * 
	 * @param chunk
	 *            if chunk is equal to null.
	 * @throws MalformedTrackException
	 *             MalformedTrackException
	 */
	public TrackChunk(String chunk) throws MalformedTrackException {
		if (null == chunk || !validChunk(chunk)) {
			throw new MalformedTrackException();
		}
		this.chunk = chunk;
	}

	/**
	 * String to getChunk.
	 * 
	 * @return the chunk
	 */
	public String getChunk() {
		return chunk;
	}

	/**
	 * Audio track chunk to set.
	 * 
	 * @param chunk
	 *            if chunk is equal to null.
	 * @throws MalformedTrackException
	 *             MalformedTrackException
	 */
	public void setChunk(String chunk) throws MalformedTrackException {
		if (null == chunk || !validChunk(chunk)) {
			throw new MalformedTrackException();
		}
		this.chunk = chunk;
	}

	/**
	 * checks if validChunk matches and depending return true of false.
	 * 
	 * @param chunk
	 *            chunk compiles or not.
	 * @return Is valid track chunk?
	 */
	public boolean validChunk(String chunk) {
		Pattern regexPattern = Pattern.compile("^[0-9A-F]{8}$");
		return regexPattern.matcher(chunk).matches();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TrackChunk [chunk=" + chunk + "]";
	}
}
