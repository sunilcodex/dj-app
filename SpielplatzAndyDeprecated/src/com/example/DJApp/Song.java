package com.example.DJApp;

public class Song
{
	private String file;

	private String title = null;
	private String artist = null;
	private String album = null;
	private String albumArtUri = null;
	private long duration;

	// private String genre = null;

	public Song(String file)
	{
		this.file = file;
	}

	public String getFile()
	{
		return this.file;
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

	public String getAlbum()
	{
		return album;
	}

	public void setAlbum(String album)
	{
		this.album = album;
	}

	// TODO fix the genre shit

	// public String getGenre()
	// {
	// return genre;
	// }

	// public void setGenre(String genre)
	// {
	// this.genre = genre;
	// }

	@Override
	public String toString()
	{
		return this.file + " - " + this.artist + " - " + this.title;
	}

	public String getAlbumArtUri()
	{
		return albumArtUri;
	}

	public void setAlbumArtUri(String albumArtUri)
	{
		this.albumArtUri = albumArtUri;
	}

	public long getDuration()
	{
		return duration;
	}

	public void setDuration(long duration)
	{
		this.duration = duration;
	}
}
