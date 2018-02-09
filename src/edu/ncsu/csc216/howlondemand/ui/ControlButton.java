package edu.ncsu.csc216.howlondemand.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.JLabel;

import edu.ncsu.csc216.howlondemand.ui.util.Properties;

/**
 * Control button for Howl on Demand.
 * 
 * @author Adam Gaweda
 */
@SuppressWarnings("serial")
public class ControlButton extends JLabel {
	/**
	 * Creates a control button.
	 * 
	 * @param text
	 *            button text
	 * @param icon
	 *            button icon
	 * @param horizontalAlignment
	 *            button alighnment
	 */
	public ControlButton(String text, Icon icon, int horizontalAlignment) {
		super(text, icon, horizontalAlignment);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setOpaque(true);
				setBackground(Properties.SILVER);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setOpaque(false);
				setBackground(null);
			}
		});
	}

}
