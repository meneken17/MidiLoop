package com.kallendorf.midiloop.gui;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class KeyControledButton extends JButton {

	private KeyStroke keyStroke;
	private Action a;

	public KeyControledButton(String text, KeyStroke keyStroke) {
		super(text);
		this.keyStroke = keyStroke;
	}

	public void setAction(Action a) {
		removeActionListener(this.a);
		addActionListener(a);

		if (getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).get(keyStroke) == null) {
			getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, this);
		}else{
			getActionMap().remove(this.a);
		}

		getActionMap().put(this, a);
		this.a=a;
	}
}
