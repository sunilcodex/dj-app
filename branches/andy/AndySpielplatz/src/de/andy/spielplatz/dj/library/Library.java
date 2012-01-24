package de.andy.spielplatz.dj.library;

import android.content.ContentResolver;
import android.util.Log;

public class Library
{
	private static Library library;

	private SongLibrary songLibrary;
	private AlbumLibrary albumLibrary;
	private ArtistLibrary artistLibrary;

	private Library(ContentResolver contentResolver)
	{
		this.songLibrary = new SongLibrary(contentResolver);
		this.albumLibrary = new AlbumLibrary(contentResolver, this.songLibrary);
		this.artistLibrary = new ArtistLibrary(contentResolver, this.songLibrary);
	}

	public static void instantiate(ContentResolver contentResolver)
	{
		library = new Library(contentResolver);
	}

	public synchronized static Library getInstance()
	{
		if (library == null)
			Log.e("", "library is not yet instantiated, motherfucker!");
		return library;
	}

	public SongLibrary getSongLibrary()
	{
		return this.songLibrary;
	}

	public AlbumLibrary getAlbumLibrary()
	{
		return this.albumLibrary;
	}

	public ArtistLibrary getArtistLibrary()
	{
		return this.artistLibrary;
	}
}
