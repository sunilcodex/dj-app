package com.example.DJApp;

import java.util.ArrayList;
import java.util.List;

public class Playlist
{
	private List<Song> playlist;
	private String name;
	private Song currentSong = null;

	public Playlist()
	{
		this.playlist = new ArrayList<Song>();
	}

	public Playlist(String name)
	{
		this();

		// TODO load playlist
	}

	public Song getCurrentSong()
	{
		return this.currentSong;
	}

	public void addSong(Song song)
	{
		if (this.playlist.isEmpty())
			this.currentSong = song;

		this.playlist.add(song);
	}

	public boolean removeSong(Song song)
	{
		return this.playlist.remove(song);
	}

	public List<Song> getPlaylist()
	{
		return this.playlist;
	}

	public void clear()
	{
		this.playlist.clear();
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return this.name;
	}

	public void save()
	{
		// TODO save playlist
	}

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
