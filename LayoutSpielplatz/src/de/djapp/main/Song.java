package de.djapp.main;

/**
 * The song
 * 
 * @author Andreas Krings
 * 
 */
public class Song
{
	private String file;

	private String title = null;
	private String artist = null;
	private String album = null;
	private String albumArtUri = null;
	private long duration;
	private String albumId;

	// private static final String TAG = "DJApp.Song";
	// private String genre = null;

	/**
	 * Create a song
	 * 
	 * @param file
	 *            The full path of the song can be found in. Neither <code>null</code> nor empty
	 */
	public Song(String file)
	{
		if (file == null || file.isEmpty())
			throw new IllegalArgumentException("The filename must neither be null nor empty");

		this.file = file;
	}

	/**
	 * Get the full path of where the file can be found
	 * 
	 * @return The full path of where the file can be found
	 */
	public String getFile()
	{
		return this.file;
	}

	/**
	 * The title of the song
	 * 
	 * @return The title of the song
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * Set the title of the song. Eg: The title retrieved from the ID3 Tags
	 * 
	 * @param title
	 *            The title
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * Get the artist of the song
	 * 
	 * @return The artist of the song
	 */
	public String getArtist()
	{
		return artist;
	}

	/**
	 * Set the artist of the song. Eg: The artist retrieved from the ID3 Tags
	 * 
	 * @param artist
	 *            The artist
	 */
	public void setArtist(String artist)
	{
		this.artist = artist;
	}

	/**
	 * Get the album name of the song
	 * 
	 * @return The album name
	 */
	public String getAlbum()
	{
		return album;
	}

	/**
	 * Set the album name of the song. Eg: The album name retrieved from the ID3 Tags
	 * 
	 * @param album
	 *            The album name
	 */
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

	/**
	 * Get the URI of where the album art can be found
	 * 
	 * @return The URI of where the album art can be found, or <code>null</code> if there is no album art associated with the file
	 */
	public String getAlbumArtUri()
	{
		return albumArtUri;
	}

	/**
	 * Set the album uri of where the album art can be found. The album art can be found in the ID3 tags. Or <code>null</code> if there is no album art
	 * 
	 * @param albumArtUri
	 *            The album art uri or <code>null</code>
	 */
	public void setAlbumArtUri(String albumArtUri)
	{
		this.albumArtUri = albumArtUri;
	}

	/**
	 * Get the duation of the song
	 * 
	 * @return The duration of the song
	 */
	public long getDuration()
	{
		return duration;
	}

	/**
	 * Set the duration of the song
	 * 
	 * @param duration
	 *            The duration of the song
	 */
	public void setDuration(long duration)
	{
		this.duration = duration;
	}

	public String getAlbumId()
	{
		return albumId;
	}

	public void setAlbumKey(String albumId)
	{
		this.albumId = albumId;
	}
}
