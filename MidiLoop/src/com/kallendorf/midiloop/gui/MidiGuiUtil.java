package com.kallendorf.midiloop.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.UIManager;

public class MidiGuiUtil {

	public static void flashButton(JButton button, Color color, long duration) {
		Timer timer = new Timer();
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				button.setBackground(color);
			}
		});

		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				if (button.getBackground().equals(color)) {
					EventQueue.invokeLater(new Runnable() {
						
						@Override
						public void run() {
							button.setBackground(UIManager.getColor("Button.background"));
							timer.cancel();
						}
					});
				}
			}
		}, duration);
	}

	public static void flashButton(JButton button, Color color) {
		flashButton(button, color, 200);
	}
}
