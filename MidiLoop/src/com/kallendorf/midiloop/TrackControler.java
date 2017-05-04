package com.kallendorf.midiloop;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Track;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import com.kallendorf.midiloop.beat.BeatControler.BeatListener;
import com.kallendorf.midiloop.gui.KeyControledButton;
import com.kallendorf.midiloop.gui.MidiGuiUtil;

public class TrackControler {
	Track track;
	KeyControledButton btn;
	Sequencer seqr;
	Sequence seq;
	int trackIndex;
	boolean isrecording;

	public TrackControler(KeyControledButton button, Sequencer seqr) {
		this.btn = button;
		this.seqr = seqr;
		this.seq = seqr.getSequence();
		this.track = seq.createTrack();
		this.trackIndex = Arrays.asList(seq.getTracks()).indexOf(track);
		isrecording = false;
		
		MidiMain.beatControler.addBeatListener(new BeatListener() {

			@Override
			public void onBeat(int beat) {
				if (isrecording) {
					if (MidiMain.beatControler.getBeat().getBeatInBar(beat) == 0) {
						MidiGuiUtil.flashButton(button, Color.red);
					} else {
						MidiGuiUtil.flashButton(button, Color.green);
					}
				}
			}
		});
		
		@SuppressWarnings("serial")
		AbstractAction a=new AbstractAction("CallTr"+trackIndex){

			@Override
			public void actionPerformed(ActionEvent e) {
				onCall();
			}
			
		};
		btn.setAction(a);

		
	}

	public void onCall() {
		if (isEmpty() && !MidiMain.recordArmed) {
			onRecordStarted();
		} else if (isrecording) {
			onRecordStopped();
		} else if (isMute()) {
			System.out.println("unmute");
			unmute();
		} else {
			System.out.println("mute");
			mute();
		}
	}

	public Track getTrack() {
		return track;
	}

	private void setTrack(Track track) {
		this.track = track;
	}

	public boolean isEmpty() {
		return track.size() <= 1;
	}

	public void reset() {
		this.track = null;
	}

	public void mute() {
		setMute(true);
	}

	public void unmute() {
		setMute(false);
	}

	public boolean isMute() {
		return seqr.getTrackMute(trackIndex);
	}

	public void setMute(boolean mute) {
		seqr.setTrackMute(trackIndex, mute);
	}

	public void solo() {
		setSolo(true);
	}

	public void unsolo() {
		setSolo(false);
	}

	public boolean isSolo() {
		return seqr.getTrackSolo(trackIndex);
	}

	public void setSolo(boolean solo) {
		seqr.setTrackSolo(trackIndex, solo);
	}

	/*
	 * Pre synchronization
	 */
	public void onRecordStarted() {
		System.out.println("Record on");
		isrecording = true;
		MidiMain.recordArmed = true;

		if (!seqr.isRunning()) {
			MidiMain.beatControler.start();
		}
		seqr.startRecording();

		MidiMain.beatControler.addBeatListener(new BeatListener() {

			@Override
			public void onBeat(int beat) {
				if (MidiMain.beatControler.getBeat().getBeatInBar(beat) == 0) {
					startRecordNow();
					MidiMain.beatControler.removeBeatListener(this);
				}
			}
		});
	}

	/*
	 * Pre synchronization
	 */
	public void onRecordStopped() {
		System.out.println("Record off");
		isrecording = false;
		MidiMain.recordArmed = false;

		MidiMain.beatControler.addBeatListener(new BeatListener() {

			@Override
			public void onBeat(int beat) {
				if (MidiMain.beatControler.getBeat().getBeatInBar(beat) == 0) {
					endRecordNow();
					MidiMain.beatControler.removeBeatListener(this);
				}
			}
		});
	}

	private void startRecordNow() {
		System.out.println("Start Record Now");
		seqr.recordEnable(track, -1);
	}

	private void endRecordNow() {
		System.out.println("End Record Now");

		seqr.recordDisable(track);

		if (!seqr.isRunning()) {
			seqr.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
			seqr.start();
		}

		System.out.println("Seqr pos at " + seqr.getMicrosecondPosition());
		System.out.println("in   pos at " + MidiMain.inputDevice.getMicrosecondPosition());
		System.out.println("out  pos at " + MidiMain.outputDevice.getMicrosecondPosition());

		seqr.stopRecording();

		System.out.println(track.size() - 1 + " events");

		try {
			seqr.setSequence(seqr.getSequence());
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}

		long barLengthInTicks = 4 * 24;

		long offset = track.get(0).getTick() - track.get(0).getTick() % barLengthInTicks;

		MidiEvent eot = track.get(track.size() - 1);

		long eotTick = eot.getTick() - offset;
		eot.setTick(eotTick - (eotTick % barLengthInTicks) + barLengthInTicks);

		for (int i = 0; i < track.size() - 1; i++) {
			MidiEvent e = track.get(i);

			long tick = e.getTick();
			e.setTick(tick - offset);
		}

		System.out.println("Offset of " + offset);
		System.out.println("First Event at " + track.get(0).getTick());

		try {
			System.out.println("Last Event at " + track.get(track.size() - 2).getTick());
		} catch (ArrayIndexOutOfBoundsException idntC) {
		}
		System.out.println("EOT  Event at " + track.ticks());

	}

}
