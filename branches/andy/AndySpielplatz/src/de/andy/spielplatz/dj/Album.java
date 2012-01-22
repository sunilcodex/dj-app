package de.andy.spielplatz.dj;

import java.util.ArrayList;
import java.util.List;

public class Album
{
	private List<Song> allSongs;
	private String title;
	private String artist;
	private String albumArtUri;

	public Album()
	{
		this.allSongs = new ArrayList<Song>();
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getArtist()
	{
		return artist;
	}

	public void setArtist(String artist)
	{
		this.artist = artist;
	}

	public String getAlbumArtUri()
	{
		return albumArtUri;
	}

	public void setAlbumArtUri(String albumArtUri)
	{
		this.albumArtUri = albumArtUri;
	}

	public List<Song> getSongs()
	{
		return allSongs;
	}

	public void setSongs(List<Song> allSongs)
	{
		this.allSongs = allSongs;
	}
}
