package de.djapp.main;

import java.util.ArrayList;
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
public class ArtistLibrary
{
	private static final String TAG = "DJApp.SongLibrary";
	private List<Artist> allArtists;

	private static final String[] COLUMNS = new String[]
	{
	AudioColumns.ARTIST, // 1
	AudioColumns.ARTIST_KEY, // 2
	};

	/**
	 * Create the song library
	 * 
	 * @param contentResolver
	 *            A link to the content resolver // TODO maybe possible that the library gets its own content resolver? No idea how, though
	 */
	public ArtistLibrary(ContentResolver contentResolver, SongLibrary songLibrary)
	{
		this.allArtists = new ArrayList<Artist>();

		Uri allArtistsUri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
		Cursor cursor = contentResolver.query(allArtistsUri, COLUMNS, null, null, AudioColumns.ARTIST + " ASC");

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
				String artistName = cursor.getString(0);
				String artistId = cursor.getString(1);

				Artist artist = new Artist(artistName, artistId);

				this.allArtists.add(artist);
			}
			while (cursor.moveToNext());
		}
	}

	public List<Artist> getAllArtists()
	{
		return this.allArtists;
	}
}