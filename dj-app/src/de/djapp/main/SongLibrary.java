package de.djapp.main;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.AudioColumns;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;

/**
 * The song library for the songs.
 * 
 * 
 * @author Andreas Krings
 * 
 */
public class SongLibrary
{
	private static final String TAG = "DJApp.SongLibrary";
	private List<Song> allSongs;

	private static final String[] COLUMNS = new String[]
	{
	MediaColumns.DATA, // 0
	MediaColumns.TITLE, // 1
	AudioColumns.ALBUM, // 2
	AudioColumns.ARTIST, // 3
	AudioColumns.ALBUM_ID, // 4
	AudioColumns.DURATION, // 5
	};

	/**
	 * Create the song library
	 * 
	 * @param contentResolver
	 *            A link to the content resolver // TODO maybe possible that the library gets its own content resolver? No idea how, though
	 */
	public SongLibrary(ContentResolver contentResolver)
	{
		this.allSongs = new ArrayList<Song>();

		Uri allSongsUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		Cursor cursor = contentResolver.query(allSongsUri, COLUMNS, "is_music=1", null, null);

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

				String filePath = cursor.getString(0);
				String title = cursor.getString(1);
				String album = cursor.getString(2);
				String artist = cursor.getString(3);
				long albumId = cursor.getLong(4);
				long duration = cursor.getLong(5);

				Cursor albumFetcher = contentResolver.query(ContentUris.withAppendedId(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, albumId), new String[]
				{
					MediaStore.Audio.AlbumColumns.ALBUM_ART
				}, null, null, null);

				String albumArtUri = null;
				if (albumFetcher.moveToFirst())
					albumArtUri = albumFetcher.getString(0);
				albumFetcher.close();

				Song song = new Song(filePath);
				song.setTitle(title);
				song.setArtist(artist);
				song.setAlbum(album);
				song.setAlbumArtUri(albumArtUri);
				song.setDuration(duration);

				this.allSongs.add(song);
			}
			while (cursor.moveToNext());
		}

	}

	/**
	 * Get a list of all songs
	 * 
	 * @return A list of all songs, or an emtpy list
	 */
	public List<Song> getAllSongs()
	{
		return this.allSongs;
	}
}