package com.kallendorf.midiloop.beat;

import java.time.Duration;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;

public class BeatFinder {
	Instant c0;
	Instant c1;
	Instant c2;
	Instant c3;

	Timer timeoutTimer;
	BeatControler controler;
	static int timeout = 90000 / (Beat.lowerCap);

	public BeatFinder(BeatControler controler) {
		this.controler = controler;
		timeoutTimer = new Timer();
	}

	/*
	 * @Return: true if set.
	 */
	public synchronized boolean click() {
		timeoutTimer.cancel();
		timeoutTimer = new Timer();

		if (c0 == null) {
			c0 = Instant.now();

		} else if (c1 == null) {
			c1 = Instant.now();

		} else if (c2 == null) {
			c2 = Instant.now();

		} else {
			c3 = Instant.now();
			float bpm = Math.max(Beat.lowerCap, Math.min(Beat.upperCap, bpm(c0, c1, c2, c3)));
			controler.getSequencer().setTempoInBPM(bpm);
			reset();
			return true;
		}

		startTimeoutTimer();
		return false;
	}

	private void startTimeoutTimer() {
		timeoutTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				reset();
			}
		}, timeout);
	}

	public synchronized void reset() {
		c0 = null;
		c1 = null;
		c2 = null;
		c3 = null;
	}

	public int getTimeout() {
		return timeout;
	}

	private float bpm(Instant... c) {
		Duration between = Duration.between(c[2], c[3]);
		float nanos = Duration.ofMinutes(1).toMillis();
		float nanos2 = between.abs().toMillis();
		float bpm = nanos / nanos2;
		return bpm;
	}
}
