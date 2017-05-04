package com.kallendorf.midiloop.beat;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;

import com.kallendorf.midiloop.MidiMain;

public class BeatControler {

	Sequencer seqr;
	Timer timer;
	Beat beat;

	Vector<BeatListener> listeners;

	private final BeatListener METRONOME = new BeatListener() {

		@Override
		public void onBeat(int beat) {
			try {
				MidiSystem.getReceiver().send(new ShortMessage(ShortMessage.NOTE_ON, 9, 0x23, 0x40), 0);
			} catch (MidiUnavailableException | InvalidMidiDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	public BeatControler(Sequencer seqr, Beat beat) {
		this.seqr = seqr;
		this.beat = beat;
		listeners = new Vector<BeatListener>();
		timer = new Timer();

		addBeatListener(beat);
	}

	public BeatControler(Sequencer seq, Beat beat, float bpm) {
		this(seq, beat);
		seq.setTempoInBPM(bpm);

	}

	public void addBeatListener(BeatListener listener) {
		listeners.add(0,listener);
	}

	public void removeBeatListener(BeatListener listener) {
		listeners.remove(listener);
	}

	public void start() {
		
		try {
			MidiMain.inputDevice.open();
			MidiMain.outputDevice.open();
			seqr.open();
			seqr.setTempoInBPM(120);
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		}
		
		TimerTask tsk = new TimerTask() {

			int beat = 0;

			@Override
			public void run() {
//				System.out.println("I:"+Calendar.getInstance().getTimeInMillis());
				@SuppressWarnings("unchecked")
				Vector<BeatListener> copy = (Vector<BeatListener>) listeners.clone();
				for (BeatListener beatListener : copy) {
					beatListener.onBeat(beat);
				}
//				System.out.println("O:"+Calendar.getInstance().getTimeInMillis()+"\n");
				beat++;
			}
		};
		
		float tempoFactor = seqr.getTempoFactor();
		float tempoInMPQ = seqr.getTempoInMPQ();
		long time = (long) (tempoFactor * tempoInMPQ / 1000);
		enableMetronome();
		timer.scheduleAtFixedRate(tsk, 0, time);
		
		
		
//		seqr.setTickPosition(0);
	}

	public interface BeatListener {
		public void onBeat(int beat);
	}

	public Beat getBeat() {
		return beat;
	}

	public Sequencer getSequencer() {
		return seqr;
	}

	public void enableMetronome() {
		if (!listeners.contains(METRONOME)) {
			listeners.add(METRONOME);
		}
	}

	public void disableMetronome() {
		if (listeners.contains(METRONOME)) {
			listeners.remove(METRONOME);
		}
	}
}
