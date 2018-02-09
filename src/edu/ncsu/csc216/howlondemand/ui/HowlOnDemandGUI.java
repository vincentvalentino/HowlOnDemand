package edu.ncsu.csc216.howlondemand.ui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import edu.ncsu.csc216.audioxml.xml.MalformedTrackException;
import edu.ncsu.csc216.audioxml.xml.StationIOException;
import edu.ncsu.csc216.howlondemand.platform.HowlOnDemandSystem;
import edu.ncsu.csc216.howlondemand.ui.util.Properties;

/**
 * Howl on Demand GUI.
 * 
 * @author Adam Gaweda
 */
public class HowlOnDemandGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	// Backend model
	private StationSelectionPanel stationSelectionPanel;
	private StationPlayPanel stationPlayPanel;
	private JPanel mainPanel;
	private final int width = 1200;
	private final int height = 800;

	/**
	 * Constructs the GUI.
	 */
	public HowlOnDemandGUI() {

		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			HowlOnDemandSystem.getInstance().loadStationsFromFile("test-files/StationList.xml");

			stationSelectionPanel = new StationSelectionPanel(this);
			stationPlayPanel = new StationPlayPanel(this);

			setTitle("HowlOnDemand");
			setIconImage(new ImageIcon("assets/icon.png").getImage());
			setMinimumSize(new Dimension(width, height));
			setExtendedState(JFrame.MAXIMIZED_BOTH);
			setBackground(Properties.BLACK);
			setDefaultCloseOperation(EXIT_ON_CLOSE);

			mainPanel = new JPanel(new CardLayout());
			mainPanel.setBackground(Properties.BLACK);

			mainPanel.add(stationSelectionPanel, "" + Properties.STATION_SELECTION);
			mainPanel.add(stationPlayPanel, "" + Properties.STATION_PLAY);

			add(mainPanel);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (StationIOException e) {
			e.printStackTrace();
		} catch (MalformedTrackException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Shows the screen.
	 * 
	 * @param screen
	 *            screen to show
	 */
	protected void showScreen(int screen) {
		CardLayout cl = (CardLayout) (mainPanel.getLayout());
		if (screen == Properties.STATION_SELECTION) {
			cl.show(mainPanel, "" + Properties.STATION_SELECTION);
		} else if (screen == Properties.STATION_PLAY) {
			cl.show(mainPanel, "" + Properties.STATION_PLAY);
		}
		repaint();
	}

	/**
	 * Starts the program
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			HowlOnDemandGUI hod = new HowlOnDemandGUI();
			hod.setVisible(true);
		});
	}

	/**
	 * Changes the station title to the given title.
	 * 
	 * @param title
	 *            title to change to
	 */
	public void changeStationTitle(String title) {
		stationPlayPanel.changeStationTitle(title);
	}

	/**
	 * Updates the color to the given color
	 * 
	 * @param color
	 *            color to change to
	 */
	public void updatePanelColor(Color color) {
		stationPlayPanel.updatePanelColor(color);
	}

	/**
	 * Starts the play timer.
	 */
	public void startPlayTimer() {
		stationPlayPanel.startTimer();
	}

	/**
	 * Starts the timer for the selection panel.
	 */
	public void startSelectTimer() {
		stationSelectionPanel.startTimer();
	}

	/**
	 * Stops the play timer.
	 */
	public void stopPlayTimer() {
		stationPlayPanel.stopTimer();
	}

	/**
	 * Stops the timer for the selection panel.
	 */
	public void stopSelectTimer() {
		stationSelectionPanel.stopTimer();
	}

}