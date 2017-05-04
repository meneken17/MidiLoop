package com.kallendorf.midiloop.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import com.kallendorf.midiloop.MidiMain;
import com.kallendorf.midiloop.TrackControler;

public class MidiLooperGui extends JFrame {

	private JPanel contentPane;
	private TrackControler[] trackCtrl;

	/**
	 * Create the frame.
	 */
	public MidiLooperGui() {
		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				MidiMain.exit();
				System.exit(0);
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}
		});
		setBounds(100, 100, 853, 461);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JButton btnCaps = new JButton("Caps");
		GridBagConstraints gbc_btnCaps = new GridBagConstraints();
		gbc_btnCaps.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCaps.gridwidth = 3;
		gbc_btnCaps.insets = new Insets(0, 0, 5, 5);
		gbc_btnCaps.gridx = 0;
		gbc_btnCaps.gridy = 2;
		contentPane.add(btnCaps, gbc_btnCaps);

		JButton btnTab = new JButton("Tab");
		GridBagConstraints gbc_btnTab = new GridBagConstraints();
		gbc_btnTab.gridwidth = 2;
		gbc_btnTab.insets = new Insets(0, 0, 5, 5);
		gbc_btnTab.gridx = 0;
		gbc_btnTab.gridy = 1;
		contentPane.add(btnTab, gbc_btnTab);

		JButton btnShift = new JButton("Shift");
		GridBagConstraints gbc_btnShift = new GridBagConstraints();
		gbc_btnShift.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnShift.gridwidth = 2;
		gbc_btnShift.insets = new Insets(0, 0, 5, 5);
		gbc_btnShift.gridx = 0;
		gbc_btnShift.gridy = 3;
		contentPane.add(btnShift, gbc_btnShift);

		KeyControledButton btnY = new KeyControledButton("Y",KeyStroke.getKeyStroke(KeyEvent.VK_Y, 0));
		GridBagConstraints gbc_btnY = new GridBagConstraints();
		gbc_btnY.gridwidth = 2;
		gbc_btnY.insets = new Insets(0, 0, 5, 5);
		gbc_btnY.gridx = 4;
		gbc_btnY.gridy = 3;
		contentPane.add(btnY, gbc_btnY);

		KeyControledButton btnX = new KeyControledButton("X",KeyStroke.getKeyStroke(KeyEvent.VK_X, 0));
		GridBagConstraints gbc_btnX = new GridBagConstraints();
		gbc_btnX.gridwidth = 2;
		gbc_btnX.insets = new Insets(0, 0, 5, 5);
		gbc_btnX.gridx = 6;
		gbc_btnX.gridy = 3;
		contentPane.add(btnX, gbc_btnX);

		KeyControledButton btnC = new KeyControledButton("C",KeyStroke.getKeyStroke(KeyEvent.VK_C, 0));
		GridBagConstraints gbc_btnC = new GridBagConstraints();
		gbc_btnC.gridwidth = 2;
		gbc_btnC.insets = new Insets(0, 0, 5, 5);
		gbc_btnC.gridx = 8;
		gbc_btnC.gridy = 3;
		contentPane.add(btnC, gbc_btnC);

		KeyControledButton btnV = new KeyControledButton("V",KeyStroke.getKeyStroke(KeyEvent.VK_V, 0));
		GridBagConstraints gbc_btnV = new GridBagConstraints();
		gbc_btnV.gridwidth = 2;
		gbc_btnV.insets = new Insets(0, 0, 5, 5);
		gbc_btnV.gridx = 10;
		gbc_btnV.gridy = 3;
		contentPane.add(btnV, gbc_btnV);

		KeyControledButton btnB = new KeyControledButton("B",KeyStroke.getKeyStroke(KeyEvent.VK_B, 0));
		GridBagConstraints gbc_btnB = new GridBagConstraints();
		gbc_btnB.gridwidth = 2;
		gbc_btnB.insets = new Insets(0, 0, 5, 5);
		gbc_btnB.gridx = 12;
		gbc_btnB.gridy = 3;
		contentPane.add(btnB, gbc_btnB);

		KeyControledButton btnN = new KeyControledButton("N",KeyStroke.getKeyStroke(KeyEvent.VK_N, 0));
		GridBagConstraints gbc_btnN = new GridBagConstraints();
		gbc_btnN.gridwidth = 2;
		gbc_btnN.insets = new Insets(0, 0, 5, 5);
		gbc_btnN.gridx = 14;
		gbc_btnN.gridy = 3;
		contentPane.add(btnN, gbc_btnN);

		KeyControledButton btnM = new KeyControledButton("M",KeyStroke.getKeyStroke(KeyEvent.VK_M, 0));
		GridBagConstraints gbc_btnM = new GridBagConstraints();
		gbc_btnM.gridwidth = 2;
		gbc_btnM.insets = new Insets(0, 0, 5, 5);
		gbc_btnM.gridx = 16;
		gbc_btnM.gridy = 3;
		contentPane.add(btnM, gbc_btnM);

		KeyControledButton btnPer = new KeyControledButton(",",KeyStroke.getKeyStroke(KeyEvent.VK_PERIOD, 0));
		GridBagConstraints gbc_btnPer = new GridBagConstraints();
		gbc_btnPer.insets = new Insets(0, 0, 5, 5);
		gbc_btnPer.gridx = 18;
		gbc_btnPer.gridy = 3;
		contentPane.add(btnPer, gbc_btnPer);

		JButton btnLCtrl = new JButton("LCtrl");
		btnLCtrl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		GridBagConstraints gbc_btnLCtrl = new GridBagConstraints();
		gbc_btnLCtrl.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnLCtrl.gridwidth = 3;
		gbc_btnLCtrl.insets = new Insets(0, 0, 0, 5);
		gbc_btnLCtrl.gridx = 0;
		gbc_btnLCtrl.gridy = 4;
		contentPane.add(btnLCtrl, gbc_btnLCtrl);

		JButton btnSpace = new JButton("Space");
		GridBagConstraints gbc_btnSpace = new GridBagConstraints();
		gbc_btnSpace.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSpace.gridwidth = 12;
		gbc_btnSpace.insets = new Insets(0, 0, 0, 5);
		gbc_btnSpace.gridx = 7;
		gbc_btnSpace.gridy = 4;
		contentPane.add(btnSpace, gbc_btnSpace);

		JButton btnRCtrl = new JButton("RCtrl");
		GridBagConstraints gbc_btnRCtrl = new GridBagConstraints();
		gbc_btnRCtrl.gridwidth = 3;
		gbc_btnRCtrl.gridx = 26;
		gbc_btnRCtrl.gridy = 4;
		contentPane.add(btnRCtrl, gbc_btnRCtrl);
		
		KeyControledButton[] btns=new KeyControledButton[]{btnY,btnX,btnC,btnV,btnB,btnN,btnM,btnPer};
		trackCtrl=new TrackControler[8];
		for (int i = 0; i < trackCtrl.length; i++) {
			trackCtrl[i]=new TrackControler(btns[i], MidiMain.seqr);
		}
		
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {

				return false;
			}
		});

	}
}
