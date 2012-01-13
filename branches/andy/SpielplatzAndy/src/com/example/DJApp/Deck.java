package com.example.DJApp;

import java.io.IOException;

import android.media.MediaPlayer;
import android.util.Log;

/**
 * The deck represents the audio player.
 * 
 * @author Andreas Krings, Sven Haase
 * 
 */
public class Deck
{
	private static final String TAG = "Deck";

	private MediaPlayer mediaPlayer;

	private Playlist playlist = null;

	public Deck()
	{
		this.mediaPlayer = new MediaPlayer();
	}

	public Song getCurrentSong()
	{
		if (this.playlist == null)
			return null;

		return this.playlist.getCurrentSong();
	}

	public void setPlaylist(Playlist playlist)
	{
		this.playlist = playlist;
		String audioFile = null;

		if (this.playlist.getPlaylist().size() > 0)
		{
			try
			{
				this.mediaPlayer.setDataSource(this.playlist.getCurrentSong().getFile());
				this.mediaPlayer.prepare();
			}
			catch (IOException e)
			{
				Log.e(TAG, "Could not open file " + audioFile + " for playback.", e);
			}
		}
	}

	public void setVolume(float volume)
	{
		if (volume <= 0)
			this.mediaPlayer.setVolume(0, 0);
		else
			this.mediaPlayer.setVolume(volume, volume);
	}

	/**
	 * Start the song. If the song is currently running. Start over and play from the beginning.
	 */
	public void play()
	{
		this.mediaPlayer.start();
	}

	/**
	 * Pause the song
	 */
	public void pause()
	{
		this.mediaPlayer.pause();
	}

	/**
	 * Stop the song. Jump to beginning.
	 */
	public void stop()
	{
		this.mediaPlayer.pause();
		this.mediaPlayer.seekTo(0);
	}

	public boolean isPlaying()
	{
		return this.mediaPlayer.isPlaying();
	}

	/**
	 * Seek to a certain point in the song
	 * 
	 * @param ms
	 *            Jump to the given ms. If ms is < 0 jump to the beginning. If ms is > than the duration of the song, jump to the end.
	 */
	public void seekTo(int ms)
	{
		if (ms <= 0)
			this.mediaPlayer.seekTo(0);
		else if (ms >= this.mediaPlayer.getDuration())
			this.mediaPlayer.seekTo(this.mediaPlayer.getDuration());
		else
			this.mediaPlayer.seekTo(ms);
	}

	/**
	 * Jump to a certain point in the song.
	 * 
	 * @param f
	 *            0 <= f <= 1. Eg: 0.5 jumps to 50% of the song. 0.75 to 75%
	 */
	public void seekTo(float f)
	{
		if (f <= 0)
			this.seekTo(0);
		else if (f >= 1)
			this.mediaPlayer.seekTo(this.mediaPlayer.getDuration());
		else
			this.mediaPlayer.seekTo((int) (this.mediaPlayer.getDuration() * f));
	}
}
