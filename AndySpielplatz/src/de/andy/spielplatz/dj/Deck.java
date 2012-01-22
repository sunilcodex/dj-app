package de.andy.spielplatz.dj;

import java.io.IOException;

import android.media.MediaPlayer;
import android.util.Log;

/**
 * The deck represents the audio player.
 * 
 * <h2>Import usage information:</h2> Set playlist - {@link Deck#setPlaylist(Playlist)} before starting to play / skip songs.
 * 
 * @author Andreas Krings, Sven Haase
 * 
 */
public class Deck
{
	private static final String TAG = "DJApp.Deck";

	private MediaPlayer mediaPlayer;
	private Playlist playlist = null;

	/**
	 * Create a new deck
	 */
	public Deck()
	{
		this.mediaPlayer = new MediaPlayer();
	}

	/**
	 * Get the current playing song
	 * 
	 * @return The current song or <code>null</code> if there is no song
	 */
	public Song getCurrentSong()
	{
		if (this.playlist == null)
		{
			Log.e(TAG, "Tried to use getCurrentSong() with uninitialized playlist.");
			return null;
		}

		return this.playlist.getCurrentSong();
	}

	/**
	 * Set the playlist for this deck
	 * 
	 * @param playlist
	 *            The playlist.
	 */
	public void setPlaylist(Playlist playlist)
	{
		this.playlist = playlist;

		if (this.playlist.getPlaylist().size() > 0)
			this.setSong(this.playlist.getCurrentSong());
	}

	/**
	 * Skip to the next song.
	 * 
	 * @see Playlist#nextSong()
	 */
	public void nextSong()
	{
		if (this.playlist == null)
			Log.e(TAG, "Tried to use nextSong() with uninitialized playlist.");
		else
			this.setSong(this.playlist.nextSong());
	}

	/**
	 * Skip to the previous song
	 * 
	 * @see Playlist#previousSong()
	 */
	public void previousSong()
	{
		if (this.playlist == null)
			Log.e(TAG, "Tried to use getCurrentSong() with uninitialized playlist.");
		else
			this.setSong(this.playlist.previousSong());
	}

	private void setSong(Song song)
	{
		if (song != null)
		{
			try
			{
				this.mediaPlayer.release();
				this.mediaPlayer.setDataSource(song.getFile());
				this.mediaPlayer.prepare();
			}
			catch (IOException e)
			{
				Log.e(TAG, "Could not open file " + song.getFile() + " for playback.", e);
			}
		}
	}

	/**
	 * Set the volume
	 * 
	 * @param volume
	 *            The volume. Has to be >= 0
	 * @see MediaPlayer#setVolume(float, float)
	 */
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
	 * Stop the song: Pause and jump to 0:00:00.
	 */
	public void stop()
	{
		this.mediaPlayer.pause();
		this.mediaPlayer.seekTo(0);
	}

	/**
	 * Returns if there is a song playing
	 * 
	 * @return <code>true</code> when there is a song playing
	 */
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

	/**
	 * Release the current file. See {@link MediaPlayer#release()} for more information
	 * 
	 * @see MediaPlayer#release()
	 */
	public void release()
	{
		this.mediaPlayer.release();
	}

	/**
	 * Get the current position of the playing song in milliseconds
	 * 
	 * @return The current position in milliseconds.
	 */
	public int getCurrentPosition()
	{
		return this.mediaPlayer.getCurrentPosition();
	}
}
