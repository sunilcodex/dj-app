package de.andy.spielplatz.dj;

import java.util.ArrayList;
import java.util.List;

/**
 * A playlist. <br>
 * 
 * <h2>General usage:</h2>
 * <ul>
 * <li>Create</li>
 * <li>add songs: {@link Playlist#addSong(Song)}</li>
 * <li>use {@link Playlist#nextSong()} and {@link Playlist#previousSong()} to navigate</li>
 * </ul>
 * 
 * @author Andreas Krings
 * 
 */
public class Playlist
{
	private List<Song> playlist;
	private String name = "here be text"; // TODO replace with sth. like unknown playlist
	private Song currentSong = null;

	// private static final String TAG = "DJApp.Playlist";

	/**
	 * Create the playlist
	 */
	public Playlist()
	{
		this.playlist = new ArrayList<Song>();
	}

	/**
	 * Load a playlist - Does not work yet!
	 * 
	 * @param name
	 *            The name of the playlist
	 */
	public Playlist(String name)
	{
		this();

		// TODO load playlist
	}

	/**
	 * Get the current song
	 * 
	 * @return The current song
	 */
	public Song getCurrentSong()
	{
		return this.currentSong;
	}

	/**
	 * Add a song to the playlist
	 * 
	 * @param song
	 *            The song. Not <code>null</code>
	 */
	public void addSong(Song song)
	{
		if (this.playlist.isEmpty())
			this.currentSong = song;

		this.playlist.add(song);
	}

	/**
	 * Removes a song from the playlist
	 * 
	 * @param song
	 *            The song to be removed. Not <code>null</code>
	 * @return <code>true</code> if the song could succesfully be removed. <code>false</code> if the song has not been in the playlist
	 */
	public boolean removeSong(Song song)
	{
		return this.playlist.remove(song);
	}

	/**
	 * Get the playlist
	 * 
	 * @return A list of songs, or an empty list.
	 */
	public List<Song> getPlaylist()
	{
		return this.playlist;
	}

	/**
	 * Clear the playlist
	 */
	public void clear()
	{
		this.playlist.clear();
	}

	/**
	 * Set the name for the playlist
	 * 
	 * @param name
	 *            The name for the playlist. Neither <code>null</code> nor empty
	 */
	public void setName(String name)
	{
		if (name == null || name.isEmpty())
			this.name = "here be text"; // TODO replace with sth. like "unknown playlist"
		else
			this.name = name;
	}

	/**
	 * Get the name for the playlist
	 * 
	 * @return The name for the playlist
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * Saves the playlist - Does not yet work
	 */
	public void save()
	{
		// TODO save playlist
	}

	/**
	 * Get the next song. <br>
	 * TODO add behavior at end of playlist-text, currently skips to first song in playlist
	 * 
	 * @return The next song
	 */
	public Song nextSong()
	{
		if (this.currentSong != null)
		{
			int index = this.playlist.indexOf(this.currentSong);
			index++;

			if (index >= this.playlist.size())
			{
				// TODO repeat first song / end when at end of playlist ?
				index = 0;
			}

			this.currentSong = this.playlist.get(index);
		}
		else
		{
			this.currentSong = this.playlist.get(0);
		}

		return this.currentSong;
	}

	/**
	 * Get the previous song. <br>
	 * TODO add behavior at beginning of playlist-text, currently skips to the end of playlist
	 * 
	 * @return The previous song
	 */
	public Song previousSong()
	{
		if (this.currentSong != null)
		{
			int index = this.playlist.indexOf(this.currentSong);
			index--;

			if (index < 0)
			{
				// TODO ...
				if (!this.playlist.isEmpty())
					index = this.playlist.size() - 1;
				else
					return null;
			}

			this.currentSong = this.playlist.get(index);
		}
		else
			this.currentSong = this.playlist.get(0);

		return this.currentSong;
	}

}
