package edu.ncsu.csc216.howlondemand.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

import edu.ncsu.csc216.howlondemand.model.AudioTrack;
import edu.ncsu.csc216.howlondemand.model.Station;
import edu.ncsu.csc216.howlondemand.platform.Command;
import edu.ncsu.csc216.howlondemand.platform.HowlOnDemandSystem;
import edu.ncsu.csc216.howlondemand.platform.enums.CommandValue;
import edu.ncsu.csc216.howlondemand.ui.util.Properties;

/**
 * Panel for Station selection.
 * 
 * @author Adam Gaweda
 */
public class StationSelectionPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private HowlOnDemandGUI gui;
	private ArrayList<Station> stations;
	private ArrayList<JButton> stationBtns;
	private Timer timer;

	/**
	 * Constructs the station selection panel.
	 * 
	 * @param howlOnDemandGUI
	 *            GUI that is connected to.
	 */
	public StationSelectionPanel(HowlOnDemandGUI howlOnDemandGUI) {
		this.gui = howlOnDemandGUI;
		this.timer = new Timer(100, this);
		this.timer.setInitialDelay(190);
		this.timer.start();
		this.stations = HowlOnDemandSystem.getInstance().getStations();
		this.stationBtns = new ArrayList<JButton>();

		setOpaque(false);
		setVisible(true);
		setLayout(new GridLayout(3, 3, 30, 30));
		setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

		for (Station station : stations) {
			JButton newStationButton = createStationButton(station);
			newStationButton.addActionListener(ae -> {
				try {
					HowlOnDemandSystem.getInstance().loadStation(station);
					station.setIndex(0);
					AudioTrack at = station.getCurrentAudioTrack();
					at.setChunkIndex(0);

					Command c = new Command(CommandValue.PLAY);
					HowlOnDemandSystem.getInstance().executeCommand(c);

					if (HowlOnDemandSystem.getInstance().getCurrentStation() == null) {
						throw new Exception();
					} else {
						this.gui.startPlayTimer();
						this.gui.stopSelectTimer();
						this.gui.setTitle("Now Playing - " + station.getTitle());
						this.gui.changeStationTitle(station.getTitle());
						this.gui.updatePanelColor(Properties.LIST[station.getColor()]);
						this.gui.showScreen(Properties.STATION_PLAY);
					}
				} catch (Exception ex) {
					// Do nothing
				}
			});

			add(newStationButton);
			stationBtns.add(newStationButton);
		}
	}

	private JButton createStationButton(Station station) {
		JButton btn = new JButton(station.getTitle());
		btn.setBackground(Properties.LIST[station.getColor()]);
		btn.setForeground(Properties.WHITE);
		btn.setPreferredSize(new Dimension(WIDTH / 4, HEIGHT / 4));
		btn.setFont(new Font("Century Gothic", Font.PLAIN, 30));
		btn.setFocusPainted(false);
		return btn;
	}

	/**
	 * Handles action.
	 * 
	 * @param e
	 *            action event
	 */
	public void actionPerformed(ActionEvent e) {
		updateTextSizes();
	}

	/**
	 * Updates the text sizes.
	 */
	public void updateTextSizes() {
		Dimension d = this.getSize();
		int fontSize = 35;
		if (d.getWidth() < 1200)
			fontSize = 25;
		else
			fontSize = 35;

		for (JButton station : stationBtns) {
			Font font = station.getFont();
			float size = fontSize;
			station.setFont(font.deriveFont(size));
		}
	}

	/**
	 * Starts the timer.
	 */
	public void startTimer() {
		this.timer.start();
	}

	/**
	 * Stops the timer.
	 */
	public void stopTimer() {
		this.timer.stop();
	}

}
