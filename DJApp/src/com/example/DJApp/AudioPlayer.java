package com.example.DJApp;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Handler;
import android.util.Log;
import android.widget.MediaController;
import android.app.AlertDialog;
import android.content.res.Resources;

import android.content.Context;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class AudioPlayer implements MediaController.MediaPlayerControl{
	private static final String TAG = "AudioPlayer";

	private MediaPlayer mediaPlayer;

	private String audioFile = "";
	
	public AudioPlayer() {
		mediaPlayer = new MediaPlayer();
        }

	public String getAudioFile() {
		return audioFile;
	}

	public void setAudioFile(String audioFileP) {
		boolean playIt = false;
		if(mediaPlayer.isPlaying()) playIt = true;
		audioFile = audioFileP;
		mediaPlayer.reset();

		try {
			mediaPlayer.setDataSource(audioFile);
			mediaPlayer.prepare();
			if(playIt) mediaPlayer.start();
		} catch (IOException e) {
			Log.e(TAG, "Could not open file " + audioFile + " for playback.", e);
		}

	}

	public void setVolume(float volume)
	{
		mediaPlayer.setVolume(volume, volume);
	}
	
	// --MediaPlayerControl
	// methods----------------------------------------------------
	public void start() {
		mediaPlayer.start();
	}

	public void pause() {
		mediaPlayer.pause();
	}

	public int getDuration() {
		return mediaPlayer.getDuration();
	}
	
	public int getDurationInSeconds()
	{
		return mediaPlayer.getDuration() / 1000;
	}

	public int getCurrentPosition() {
		return mediaPlayer.getCurrentPosition();
	}
	
	public int getCurrentPositionInSeconds()
	{
		return mediaPlayer.getCurrentPosition() / 1000;
	}
	
	public float getCurrentPositionInPercent()
	{
		return (float) this.getCurrentPosition() / (float) this.getDuration() ;
	}

	public void seekTo(int i) {
		mediaPlayer.seekTo(i);
	}
	
	public void seekTo(float f)
	{
		mediaPlayer.seekTo((int) (mediaPlayer.getDuration() * f));
	}

	public boolean isPlaying() {
		return mediaPlayer.isPlaying();
	}

	public int getBufferPercentage() {
		return 0;
	}

	public boolean canPause() {
		return true;
	}

	public boolean canSeekBackward() {
		return true;
	}

	public boolean canSeekForward() {
		return true;
	}

}
