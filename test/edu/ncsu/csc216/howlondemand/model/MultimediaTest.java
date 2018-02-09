package edu.ncsu.csc216.howlondemand.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import edu.ncsu.csc216.audioxml.xml.MalformedTrackException;

/**
 * Concrete implementation of abstract class 'Multimedia'. Abstract class that
 * represents a Multimedia, which AudioTrack class inherits from. Overridden
 * dummy methods.
 * 
 * @author kavitpatel
 */
public class MultimediaTest extends Multimedia {

	/**
	 * MultimediaTest.
	 */
	public MultimediaTest() {
		super(1, "Title");
	}

	@Override
	public TrackChunk getNextChunk() {
		return null;
	}

	@Override
	public boolean hasNextChunk() {
		return false;
	}

	@Override
	public void addChunk(TrackChunk chunk) throws MalformedTrackException {
		// this smethod is created to just for concreate behavior

	}

	@Override
	public String toString() {
		return null;
	}

	/**
	 * constructor of concrete implementation class test
	 */
	@Test
	public void multimediaConstructorTest() {
		MultimediaTest multimedia = new MultimediaTest();
		assertTrue(multimedia.getId() == 1);
	}

	/**
	 * setId method of concrete implementation class test
	 */
	@Test
	public void multimediaSetIdTest() {
		MultimediaTest multimedia = new MultimediaTest();
		multimedia.setId(5);
		assertTrue(multimedia.getId() == 5);
	}

	/**
	 * getId method of concrete implementation class test
	 */
	@Test
	public void multimediaGetIdTest() {
		MultimediaTest multimedia = new MultimediaTest();
		assertTrue(multimedia.getId() == 1);
	}

	/**
	 * getTitle method of concrete implementation class test
	 */
	@Test
	public void multimediaGetTitleTest() {
		MultimediaTest multimedia = new MultimediaTest();
		assertTrue("Title".equals(multimedia.getTitle()));
	}

	/**
	 * setTitle method of concrete implementation class test
	 */
	@Test
	public void multimediaSetTitleTest() {
		MultimediaTest multimedia = new MultimediaTest();
		multimedia.setTitle("multimediaTitle");
		assertTrue("multimediaTitle".equals(multimedia.getTitle()));
	}

	/**
	 * setTitle method of concrete implementation class test throwing exception
	 * test.
	 */

	@Test
	public void multimediaSetTitleExceptionTest() {
		AudioTrack multimedia1 = new AudioTrack(5, "Anything else", "X");
		try {
			multimedia1.setTitle(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Anything else", multimedia1.getTitle());
		}
	}

	/**
	 * setId method of concrete implementation class test throwing exception test.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void multimediaSetIdExceptionTest() {
		MultimediaTest multimedia = new MultimediaTest();
		multimedia.setId(2);
		assertTrue(multimedia.getId() == 2);
		multimedia.setId(-5);
	}

}
