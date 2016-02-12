package com.kallendorf.midiloop.beat;

import com.kallendorf.midiloop.beat.BeatControler.BeatListener;

public class Beat implements BeatListener{
	int beatNumerator;
	int beatDeterminer;
	//int barHarmonic;
	
	int theBeat=0;
	int theBar=0;
	
	public static int upperCap=240;
	public static int lowerCap=40;
	
	/*
	 * @JavaDoc
	 * 4/4 for lazy
	 */
	public Beat() {
		this(4);
	}
	
	public Beat(int numerator){
		this(numerator,4);
	}
	
	public Beat(int numerator,int determiner) {
		this.beatNumerator=numerator;
		this.beatDeterminer=determiner;
	}
	
//	public Beat(int numerator,int determiner, int barHarmonics){
//		this.beatNumerator=numerator;
//		this.beatDeterminer=determiner;
//		this.barHarmonic=barHarmonics;
//	}
	
	public int getBeatInBar(int beatOfTrack){
		return beatOfTrack%beatNumerator;
	}
	
	public int getBar(int beatOfTrack){
		return beatOfTrack/beatNumerator;
	}

	@Override
	public void onBeat(int beat) {
		this.theBeat=beat%4;
		this.theBar=beat/4;
	}

	public int getTheBeat() {
		return theBeat;
	}

	public int getTheBar() {
		return theBar;
	}
	
//	public int getTrackInHarmonics(int bar){
//		return bar%barHarmonic;
//	}
	
	
}
