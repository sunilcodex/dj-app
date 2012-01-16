package de.djapp.main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.AudioColumns;
import android.util.Log;

/**
 * The song library for the songs.
 * 
 * 
 * @author Andreas Krings
 * 
 */
public class AlbumLibrary
{
	private static final String TAG = "DJApp.SongLibrary";
	private List<Album> allAlbums;

	private static final String[] COLUMNS = new String[]
	{
	AudioColumns.ALBUM, // 0
	AudioColumns.ARTIST, // 1
	AudioColumns.ALBUM_ART, // 2
	AudioColumns.ALBUM_KEY, // 3

	};

	/**
	 * Create the song library
	 * 
	 * @param contentResolver
	 *            A link to the content resolver // TODO maybe possible that the library gets its own content resolver? No idea how, though
	 */
	public AlbumLibrary(ContentResolver contentResolver, SongLibrary songLibrary)
	{
		this.allAlbums = new ArrayList<Album>();

		Uri allAlbumsUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
		Cursor cursor = contentResolver.query(allAlbumsUri, COLUMNS, null, null, AudioColumns.ALBUM + " ASC");

		if (cursor == null)
		{
			Log.e(TAG, "Handler Failed");
		}
		else if (!cursor.moveToFirst())
		{
			Log.e(TAG, "No media found");
		}
		else
		{
			do
			{
				String albumTitle = cursor.getString(0);
				String artist = cursor.getString(1);
				String artUri = cursor.getString(2);
				String albumId = cursor.getString(3);

				Album album = new Album();
				album.setTitle(albumTitle);
				album.setArtist(artist);
				album.setAlbumArtUri(artUri);
				album.setSongs(songLibrary.getAllSongs(albumId));

				this.allAlbums.add(album);
			}
			while (cursor.moveToNext());
		}
	}

	public List<Album> getAllAlbums()
	{
		return this.allAlbums;
	}

	private class SortByTitle implements Comparator<Album>
	{
		@Override
		public int compare(Album lhs, Album rhs)
		{
			if (lhs == null || rhs == null || lhs.getTitle() == null || rhs.getTitle() == null)
				return -1;

			return lhs.getTitle().compareTo(rhs.getTitle());
		}
	}

}