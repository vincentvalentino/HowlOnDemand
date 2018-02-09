package edu.ncsu.csc216.howlondemand.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import edu.ncsu.csc216.howlondemand.model.AudioTrack;
import edu.ncsu.csc216.howlondemand.model.Station;
import edu.ncsu.csc216.howlondemand.platform.Command;
import edu.ncsu.csc216.howlondemand.platform.HowlOnDemandSystem;
import edu.ncsu.csc216.howlondemand.platform.enums.CommandValue;
import edu.ncsu.csc216.howlondemand.ui.util.Properties;

/**
 * Panel for playing stations.
 * 
 * @author Adam Gaweda
 */
public class StationPlayPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private String trackArtist;
	private String trackTitle;
	private HowlOnDemandGUI gui;
	private JTextPane stationTitle;
	private JPanel visualizationPane;
	private int[] visualBarNumbers;
	private Color panelColor;
	private Timer timer;
	private JLabel playButton;
	private JLabel stopButton;
	private JLabel repeatButton;
	private JLabel shuffleButton;
	private JTextPane trackText;

	/**
	 * Constructs the panel.
	 * 
	 * @param howlOnDemandGUI
	 *            GUI reference
	 */
	public StationPlayPanel(HowlOnDemandGUI howlOnDemandGUI) {
		this.gui = howlOnDemandGUI;
		this.visualBarNumbers = new int[10];
		this.timer = new Timer(100, this);
		this.timer.setInitialDelay(190);
		this.timer.start();

		setBackground(Properties.BLACK);
		setLayout(new BorderLayout());

		// Top - Station Title & Return to Station Selection
		JPanel stationPanel = makeStationPanel();

		// Audio Visualization
		JPanel visualizationPanel = makeVisualizationPanel();

		// Audio Track Information & Controls
		JPanel trackPanel = makeTrackPanel();

		add(stationPanel, BorderLayout.NORTH);
		add(visualizationPanel, BorderLayout.CENTER);
		add(trackPanel, BorderLayout.SOUTH);
	}

	private JPanel makeStationPanel() {
		JPanel jp = new JPanel(new BorderLayout());

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		ImageIcon menuImage = new ImageIcon("assets/menu_button.png");
		JLabel menuButton = new ControlButton("", menuImage, JLabel.CENTER);
		menuButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Command c = new Command(CommandValue.RETURN);
				HowlOnDemandSystem.getInstance().executeCommand(c);
				c = new Command(CommandValue.SELECT_STATION);
				HowlOnDemandSystem.getInstance().executeCommand(c);
				gui.setTitle("HowlOnDemand");
				gui.startSelectTimer();
				gui.stopPlayTimer();
				gui.showScreen(Properties.STATION_SELECTION);
			}
		});
		stationTitle = new JTextPane();
		stationTitle.setFont(new Font("Century Gothic", Font.PLAIN, 30));
		stationTitle.setOpaque(false);
		stationTitle.setEditable(false);
		topPanel.add(menuButton);
		topPanel.add(stationTitle);
		jp.add(topPanel, BorderLayout.WEST);

		return jp;
	}

	@SuppressWarnings("serial")
	private JPanel makeVisualizationPanel() {
		visualizationPane = new JPanel() {
			private int w, h;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;

				Dimension d = this.getSize();

				w = (int) d.getWidth();
				h = (int) d.getHeight();

				// Only show bars if in the play state
				String state = HowlOnDemandSystem.getInstance().getState().getStateName();
				String playBuff = HowlOnDemandSystem.PLAYWITHBUFFERING_NAME;
				String playNoBuff = HowlOnDemandSystem.PLAYWITHOUTBUFFERING_NAME;
				if (!state.equals(playBuff) && !state.equals(playNoBuff))
					h = 0;

				Polygon poly = new Polygon();
				poly.addPoint(0, h);
				double spacing = w / visualBarNumbers.length;
				for (int i = 0; i < visualBarNumbers.length; i++) {
					visualBarNumbers[i] = (int) (Math.random() * h);
					int offset = (int) (i * spacing);
					poly.addPoint(offset, visualBarNumbers[i]);
					poly.addPoint(offset + (int) spacing, visualBarNumbers[i]);
				}
				poly.addPoint(w, h);

				g2.setColor(panelColor);
				g2.fill(poly);
			}
		};
		visualizationPane.setBackground(Properties.SILVER);

		return visualizationPane;
	}

	private JPanel makeTrackPanel() {
		ImageIcon stopImage = new ImageIcon("assets/stop_button.png");
		ImageIcon stopImageDisabled = new ImageIcon("assets/stop_button_disabled.png");
		ImageIcon playImage = new ImageIcon("assets/play_button.png");
		ImageIcon playImageDisabled = new ImageIcon("assets/play_button_disabled.png");
		ImageIcon repeatImage = new ImageIcon("assets/repeat_button.png");
		ImageIcon repeatImageDisabled = new ImageIcon("assets/repeat_button_disabled.png");
		ImageIcon shuffleImage = new ImageIcon("assets/shuffle_button.png");
		ImageIcon shuffleImageDisabled = new ImageIcon("assets/shuffle_button_disabled.png");

		JPanel jp = new JPanel(new BorderLayout());

		jp.setForeground(Properties.BLACK);
		jp.setBackground(Properties.WHITE);

		// Play Controls
		JPanel playControls = new JPanel();
		playControls.setLayout(new FlowLayout());
		// Stop Button
		stopButton = new ControlButton("", stopImageDisabled, JLabel.CENTER);
		stopButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Command c = new Command(CommandValue.STOP);
				HowlOnDemandSystem.getInstance().executeCommand(c);
				stopButton.setIcon(stopImage);
				playButton.setIcon(playImageDisabled);
			}
		});

		// Play Button
		playButton = new ControlButton("", playImage, JLabel.CENTER);
		playButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Command c = new Command(CommandValue.PLAY);
				HowlOnDemandSystem.getInstance().executeCommand(c);
				playButton.setIcon(playImage);
				stopButton.setIcon(stopImageDisabled);
			}
		});

		// Backward Button
		ImageIcon backwardImage = new ImageIcon("assets/backward_button.png");
		JLabel backwardButton = new ControlButton("", backwardImage, JLabel.CENTER);
		backwardButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Command c = new Command(CommandValue.SKIP_BACKWARD);
				HowlOnDemandSystem.getInstance().executeCommand(c);
			}
		});

		// Forward Button
		ImageIcon forwardImage = new ImageIcon("assets/forward_button.png");
		JLabel forwardButton = new ControlButton("", forwardImage, JLabel.CENTER);
		forwardButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Command c = new Command(CommandValue.SKIP_FORWARD);
				HowlOnDemandSystem.getInstance().executeCommand(c);
			}
		});

		// Add to JPanel
		playControls.add(stopButton);
		playControls.add(playButton);
		playControls.add(backwardButton);
		playControls.add(forwardButton);
		jp.add(playControls, BorderLayout.WEST);

		// Display Track Info
		JPanel trackInfo = new JPanel();
		trackInfo.setLayout(new GridBagLayout());
		trackText = new JTextPane();
		trackText.setText(trackArtist + " - " + trackTitle);
		trackText.setFont(new Font("Century Gothic", Font.PLAIN, 30));
		trackText.setOpaque(false);
		trackText.setEditable(false);
		trackInfo.add(trackText);
		jp.add(trackInfo, BorderLayout.CENTER);

		// Loop Controls
		JPanel repeatControls = new JPanel();
		repeatControls.setLayout(new FlowLayout(FlowLayout.RIGHT));
		repeatButton = new ControlButton("", repeatImageDisabled, JLabel.CENTER);
		repeatButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				HowlOnDemandSystem.getInstance().getCurrentStation().toggleRepeat();
				boolean repeat = HowlOnDemandSystem.getInstance().getCurrentStation().getRepeat();
				if (repeat)
					repeatButton.setIcon(repeatImage);
				else
					repeatButton.setIcon(repeatImageDisabled);
			}
		});
		shuffleButton = new ControlButton("", shuffleImageDisabled, JLabel.CENTER);
		shuffleButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				HowlOnDemandSystem.getInstance().getCurrentStation().toggleShuffle();
				boolean shuffle = HowlOnDemandSystem.getInstance().getCurrentStation().getShuffle();
				if (shuffle)
					shuffleButton.setIcon(shuffleImage);
				else
					shuffleButton.setIcon(shuffleImageDisabled);
			}
		});
		repeatControls.add(repeatButton);
		repeatControls.add(shuffleButton);
		jp.add(repeatControls, BorderLayout.EAST);

		return jp;
	}

	/**
	 * Changes the station title to the given title.
	 * 
	 * @param title
	 *            title to change to
	 */
	public void changeStationTitle(String title) {
		stationTitle.setText(title);
	}

	/**
	 * Changes the track information.
	 * 
	 * @param name
	 *            name of track
	 * @param title
	 *            title of track
	 */
	public void changeTrackInformation(String name, String title) {
		trackText.setText(name + " - " + title);
	}

	/**
	 * Updates the panel color
	 * 
	 * @param stationColor
	 *            color to update
	 */
	public void updatePanelColor(Color stationColor) {
		this.panelColor = stationColor;
		changeVisualizationPanelBG();
	}

	private void changeVisualizationPanelBG() {
		if (panelColor.equals(Properties.RED))
			visualizationPane.setBackground(Properties.LIGHTRED);
		else if (panelColor.equals(Properties.ORANGE))
			visualizationPane.setBackground(Properties.LIGHTORANGE);
		else if (panelColor.equals(Properties.YELLOW))
			visualizationPane.setBackground(Properties.LIGHTYELLOW);
		else if (panelColor.equals(Properties.GREEN))
			visualizationPane.setBackground(Properties.LIGHTGREEN);
		else if (panelColor.equals(Properties.BLUE))
			visualizationPane.setBackground(Properties.LIGHTBLUE);
		else if (panelColor.equals(Properties.INDIGO))
			visualizationPane.setBackground(Properties.LIGHTINDIGO);
		else
			visualizationPane.setBackground(Properties.SILVER);
	}

	/**
	 * Handles actions.
	 * 
	 * @param e
	 *            action event to handle
	 */
	public void actionPerformed(ActionEvent e) {
		visualizationPane.repaint();
		updateTextSizes();
		updateCurrentPlaying();
		ImageIcon stopImage = new ImageIcon("assets/stop_button_disabled.png");
		ImageIcon playImage = new ImageIcon("assets/play_button_disabled.png");
		ImageIcon repeatImage = new ImageIcon("assets/repeat_button_disabled.png");
		ImageIcon shuffleImage = new ImageIcon("assets/shuffle_button_disabled.png");
		String stateName = HowlOnDemandSystem.getInstance().getState().getStateName();
		Station s = HowlOnDemandSystem.getInstance().getCurrentStation();
		if (stateName.equals(HowlOnDemandSystem.PLAYWITHBUFFERING_NAME)
				|| stateName.equals(HowlOnDemandSystem.STOPWITHBUFFERING_NAME)) {
			Command c = new Command(CommandValue.BUFFERING);
			HowlOnDemandSystem.getInstance().executeCommand(c);
		} else if (stateName.equals(HowlOnDemandSystem.PLAYWITHOUTBUFFERING_NAME)
				|| stateName.equals(HowlOnDemandSystem.STOPWITHOUTBUFFERING_NAME)) {
			Command c = new Command(CommandValue.NOT_BUFFERING);
			HowlOnDemandSystem.getInstance().executeCommand(c);
		} else if (stateName.equals(HowlOnDemandSystem.FINISHED_NAME)) {
			Command c = new Command(CommandValue.FINISH_TRACK);
			if (!s.hasNextTrack()) {
				c = new Command(CommandValue.FINISH_STATION);
				stopImage = new ImageIcon("assets/stop_button_disabled.png");
				playImage = new ImageIcon("assets/play_button_disabled.png");
				stopButton.setIcon(stopImage);
				playButton.setIcon(playImage);
				trackText.setText("End of Playlist");
			}
			HowlOnDemandSystem.getInstance().executeCommand(c);
		}

		if (stateName.equals(HowlOnDemandSystem.PLAYWITHBUFFERING_NAME)
				|| stateName.equals(HowlOnDemandSystem.PLAYWITHOUTBUFFERING_NAME)) {
			stopImage = new ImageIcon("assets/stop_button.png");
			playImage = new ImageIcon("assets/play_button_disabled.png");
			stopButton.setIcon(stopImage);
			playButton.setIcon(playImage);
		} else if (stateName.equals(HowlOnDemandSystem.STOPWITHBUFFERING_NAME)
				|| stateName.equals(HowlOnDemandSystem.STOPWITHOUTBUFFERING_NAME)) {
			stopImage = new ImageIcon("assets/stop_button_disabled.png");
			playImage = new ImageIcon("assets/play_button.png");
			stopButton.setIcon(stopImage);
			playButton.setIcon(playImage);
		}

		if (s != null) {
			if (s.getRepeat())
				repeatImage = new ImageIcon("assets/repeat_button.png");
			if (s.getShuffle())
				shuffleImage = new ImageIcon("assets/shuffle_button.png");
			repeatButton.setIcon(repeatImage);
			shuffleButton.setIcon(shuffleImage);
		}

	}

	private void updateCurrentPlaying() {
		Station s = HowlOnDemandSystem.getInstance().getCurrentStation();
		String centerText;
		try {
			AudioTrack at = s.getCurrentAudioTrack();
			trackArtist = at.getArtist();
			trackTitle = at.getTitle();
			centerText = trackArtist + " - " + trackTitle + " (" + at.getChunkIndex() + ":" + at.getTrackChunkSize()
					+ ")";
			int percent = HowlOnDemandSystem.getInstance().getChunkSize();
			if (percent == 0)
				percent = 1;
			centerText = trackArtist + " - " + trackTitle + "\n(Buffer: " + percent + "%)";
		} catch (NullPointerException npe) {
			trackArtist = "Jonathan Coulton";
			trackTitle = "Code Monkey";
			centerText = trackArtist + " - " + trackTitle;
		}
		trackText.setText(centerText);
	}

	/**
	 * Updates the text size.
	 */
	public void updateTextSizes() {
		Dimension d = this.getSize();
		MutableAttributeSet attrs = trackText.getInputAttributes();
		if (d.getWidth() < 1200)
			StyleConstants.setFontSize(attrs, 25);
		else
			StyleConstants.setFontSize(attrs, 35);
		StyledDocument doc = trackText.getStyledDocument();
		doc.setCharacterAttributes(0, doc.getLength() + 1, attrs, false);

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
