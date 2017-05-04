package com.kallendorf.midiloop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.crypto.Mac;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiDevice.Info;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Transmitter;
import javax.swing.JButton;
import javax.swing.JFrame;

import com.kallendorf.midiloop.beat.Beat;
import com.kallendorf.midiloop.beat.BeatControler;
import com.kallendorf.midiloop.beat.BeatFinder;
import com.kallendorf.midiloop.gui.MidiGuiUtil;
import com.kallendorf.midiloop.gui.MidiLooperGui;

public class MidiMain {

	public static MidiDevice inputDevice;
	public static MidiDevice outputDevice;
	public static Transmitter usbTransmitter;
	public static Receiver usbReciever;

	public static Sequencer seqr;
	public static Transmitter seqTransmitter;
	public static Receiver seqReciever;

	public static BeatControler beatControler;
	public static BeatFinder beatFinder;

	static boolean recordArmed;
	
	public static MidiLooperGui gui;

	public static void main(String[] args) {

		try {

			Info[] midiDeviceInfo = MidiSystem.getMidiDeviceInfo();

			inputDevice = MidiSystem.getMidiDevice(midiDeviceInfo[1]);
			outputDevice = MidiSystem.getMidiDevice(midiDeviceInfo[4]);

			seqr = MidiSystem.getSequencer();


			seqReciever = seqr.getReceiver();
			seqTransmitter = seqr.getTransmitters().get(0);

			usbTransmitter = inputDevice.getTransmitter();
			usbReciever = outputDevice.getReceiver();
			usbTransmitter.setReceiver(seqReciever);
			seqTransmitter.setReceiver(usbReciever);

			Sequence seq = new Sequence(Sequence.PPQ, 24);
			recordArmed = false;

			seqr.setSequence(seq);
			seqr.setTickPosition(0);

			beatControler = new BeatControler(seqr, new Beat());
			beatFinder = new BeatFinder(beatControler);

			gui=new MidiLooperGui();
			gui.setVisible(true);

		} catch (MidiUnavailableException | InvalidMidiDataException e) {
			e.printStackTrace();
		}
	}

	public static void exit() {
		inputDevice.close();
		outputDevice.close();
		usbTransmitter.close();
		seqReciever.close();
		seqr.close();
	}
}
