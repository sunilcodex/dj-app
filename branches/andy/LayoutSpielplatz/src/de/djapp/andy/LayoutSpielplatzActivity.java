package de.djapp.andy;

import java.util.List;

import android.app.Activity;
import android.content.ClipData;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import de.djapp.helper.FormatHelper;
import de.djapp.layout.ArtistAdapter;
import de.djapp.layout.GalleryAdapter;
import de.djapp.layout.LeftMenuAdapter;
import de.djapp.layout.SongListAdapter;
import de.djapp.main.Album;
import de.djapp.main.AlbumLibrary;
import de.djapp.main.Artist;
import de.djapp.main.ArtistLibrary;
import de.djapp.main.Song;
import de.djapp.main.SongLibrary;
import de.djapp.main.SortBy;

public class LayoutSpielplatzActivity extends Activity
{
	private SongLibrary songLibrary;
	private AlbumLibrary albumLibrary;
	private LinearLayout parent;
	private ArtistLibrary artistLibrary;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		this.songLibrary = new SongLibrary(getContentResolver());
		this.albumLibrary = new AlbumLibrary(getContentResolver(), this.songLibrary);
		this.artistLibrary = new ArtistLibrary(getContentResolver(), this.songLibrary);

		this.parent = (LinearLayout) findViewById(R.id.centerNavigation);
		this.showTitle(null);

		ListView leftNavigation = (ListView) findViewById(R.id.leftNavigation);
		leftNavigation.setAdapter(new LeftMenuAdapter(this));
		// InputStream in = getResources().openRawResource(R.drawable.left_navigation_selected);
		// leftNavigation.setSelector(NinePatchDrawable.createFromStream(in, null));
		// this.showAlbum(null);
		// this.showGenre(null);
	}

	public void showAlbum(View v)
	{
		this.parent.removeAllViews();
		final List<Album> allAlbums = this.albumLibrary.getAllAlbums();

		LinearLayout albumLayout = (LinearLayout) LayoutInflater.from(getBaseContext()).inflate(R.layout.album_browser, null);
		this.parent.addView(albumLayout, new LinearLayout.LayoutParams(-1, -1));

		Gallery gallery = (Gallery) albumLayout.findViewById(R.id.album_gallery);

		gallery.setAdapter(new GalleryAdapter(allAlbums, this.getBaseContext()));

		final TextView albumTitle = (TextView) albumLayout.findViewById(R.id.album_item_title_artist);
		final ListView songList = (ListView) albumLayout.findViewById(R.id.album_songlist);

		gallery.setCallbackDuringFling(true);
		gallery.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				Album album = allAlbums.get(position);
				if (album != null)
				{
					albumTitle.setText(album.getTitle() + " - " + album.getArtist());
					songList.setAdapter(new SongListAdapter(LayoutSpielplatzActivity.this, album.getSongs()));
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> v)
			{

			}
		});
	}

	private LinearLayout getSongItem(final LinearLayout songItem, Song song)
	{
		// songItem.setLineColor("#FF0000");

		TextView songTitle = (TextView) songItem.findViewById(R.id.song_title);
		songTitle.setText(song.getTitle());
		TextView songArtist = (TextView) songItem.findViewById(R.id.song_artist);
		songArtist.setText(song.getArtist());
		final TextView songDuration = (TextView) songItem.findViewById(R.id.song_duration);
		songDuration.setText(FormatHelper.formatDuration(song.getDuration()));
		ImageView songAlbumArt = (ImageView) songItem.findViewById(R.id.album_art);

		if (song.getAlbumArtUri() == null)
			songAlbumArt.setImageResource(R.drawable.art_not_found);
		else
			songAlbumArt.setImageURI(Uri.parse(song.getAlbumArtUri()));

		final OnLongClickListener dragListener = new OnLongClickListener()
		{

			@Override
			public boolean onLongClick(View v)
			{
				songDuration.setVisibility(View.INVISIBLE);
				ClipData clipData = ClipData.newPlainText("", "" + v.getContentDescription());
				View.DragShadowBuilder dsb = new View.DragShadowBuilder(songItem);
				v.startDrag(clipData, dsb, v, 0);
				songDuration.setVisibility(View.VISIBLE);

				return true;
			}
		};

		songItem.setOnLongClickListener(dragListener);

		OnLongClickListener passThroughListener = new OnLongClickListener()
		{

			@Override
			public boolean onLongClick(View v)
			{
				return dragListener.onLongClick(v);
			}
		};

		songDuration.setOnLongClickListener(passThroughListener);
		songArtist.setOnLongClickListener(passThroughListener);
		songTitle.setOnLongClickListener(passThroughListener);
		songAlbumArt.setOnLongClickListener(passThroughListener);

		return songItem;
	}

	public void showArtist(View v)
	{
		this.parent.removeAllViews();
		final List<Artist> allArtists = this.artistLibrary.getAllArtists();

		LinearLayout artistsListLayout = (LinearLayout) LayoutInflater.from(getBaseContext()).inflate(R.layout.artist_browser, null);
		this.parent.addView(artistsListLayout, new LinearLayout.LayoutParams(-1, -1));

		ListView artistList = (ListView) artistsListLayout.findViewById(R.id.artist_artistlist);
		artistList.setAdapter(new ArtistAdapter(this, allArtists));

		final ListView songList = (ListView) artistsListLayout.findViewById(R.id.artist_songlist);
		final TextView artistName = (TextView) artistsListLayout.findViewById(R.id.artist_name);

		Artist artist = allArtists.get(0);
		artistName.setText(artist.getName());
		songList.setAdapter(new SongListAdapter(LayoutSpielplatzActivity.this, LayoutSpielplatzActivity.this.songLibrary.getAllSongsByArtist(artist.getKey())));

		artistList.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Artist artist = allArtists.get(position);
				artistName.setText(artist.getName());
				songList.setAdapter(new SongListAdapter(LayoutSpielplatzActivity.this, LayoutSpielplatzActivity.this.songLibrary.getAllSongsByArtist(artist.getKey())));

			}
		});
	}

	public void showTitle(View v)
	{
		// this.showSongList(SortBy.TITLE);
		this.parent.removeAllViews();
		final List<Song> allSongs = this.songLibrary.getAllSongs(SortBy.TITLE);

		LinearLayout songListLayout = (LinearLayout) LayoutInflater.from(getBaseContext()).inflate(R.layout.song_list, null);
		this.parent.addView(songListLayout, new LinearLayout.LayoutParams(-1, -1));

		ListView songList = (ListView) songListLayout.findViewById(R.id.song_list);
		songList.setAdapter(new SongListAdapter(this, allSongs));
	}

	private void showSongList(SortBy sortBy)
	{
		this.parent.removeAllViews();
		// LinearLayout songListLayout = (LinearLayout) LayoutInflater.from(getBaseContext()).inflate(R.layout.song_list, null, false);
		// this.parent.addView(songListLayout, new LinearLayout.LayoutParams(-1, -1));
		//
		// final LinearLayout songList = (LinearLayout) songListLayout.findViewById(R.id.song_list);
		//
		// char[] charArray = new char[]
		// {
		// '#', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
		// };
		//
		// for (int i = 0; i < charArray.length; i++)
		// {
		// List<Song> songs = this.songLibrary.getSongs(charArray[i]);
		//
		// if (!songs.isEmpty())
		// {
		// LinearLayout songGroup = (LinearLayout) LayoutInflater.from(getBaseContext()).inflate(R.layout.song_group, null, false);
		// songList.addView(songGroup);
		// TextView groupTitle = (TextView) songGroup.findViewById(R.id.group_title);
		// // groupTitle.setLineColor("#334A7A");
		// groupTitle.setText("" + Character.toUpperCase(charArray[i]));
		//
		// TableLayout table = (TableLayout) songGroup.findViewById(R.id.group_table);
		// table.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
		//
		// TableRow row = null;
		// for (int songNumber = 0; songNumber < songs.size(); songNumber++)
		// {
		// Song song = songs.get(songNumber);
		// LinearLayout songItem = this.getSongItem((LinearLayout) LayoutInflater.from(getBaseContext()).inflate(R.layout.song_item, null, false), song);
		//
		// if (songNumber % 2 == 0)
		// {
		// row = new TableRow(getBaseContext());
		// row.addView(songItem, new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f));
		// }
		// else
		// {
		// row.addView(songItem, new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f));
		// table.addView(row, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		//
		// if (songNumber + 1 != songs.size())
		// {
		// View hr = new View(getBaseContext());
		// hr.setBackgroundColor(Color.parseColor("#404040"));
		// table.addView(hr, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, 1));
		// }
		// }
		// }
		// if (songs.size() % 2 == 1)
		// {
		// Song song = songs.get(songs.size() - 1);
		// LinearLayout songItem = this.getSongItem((LinearLayout) LayoutInflater.from(getBaseContext()).inflate(R.layout.song_item, null, false), song);
		// row = new TableRow(getBaseContext());
		// row.addView(songItem, new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f));
		// row.addView(new View(getBaseContext()), new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f));
		// table.addView(row, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		// }
		// }
		// }
	}
}
