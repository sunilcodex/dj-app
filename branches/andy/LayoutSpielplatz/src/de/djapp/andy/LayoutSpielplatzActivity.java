package de.djapp.andy;

import java.util.List;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import de.djapp.helper.FormatHelper;
import de.djapp.layout.SongListAdapter;
import de.djapp.main.Album;
import de.djapp.main.AlbumLibrary;
import de.djapp.main.Song;
import de.djapp.main.SongLibrary;
import de.djapp.main.SortBy;

public class LayoutSpielplatzActivity extends Activity
{
	private SongLibrary songLibrary;
	private AlbumLibrary albumLibrary;
	private LinearLayout parent;
	private int mGalleryItemBackground;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		this.songLibrary = new SongLibrary(getContentResolver());
		this.albumLibrary = new AlbumLibrary(getContentResolver(), this.songLibrary);

		this.parent = (LinearLayout) findViewById(R.id.centerNavigation);
		this.showTitle(null);
		// this.showAlbum(null);
		// this.showGenre(null);
	}

	public void showAlbum(View v)
	{
		this.parent.removeAllViews();
		final List<Album> allAlbums = this.albumLibrary.getAllAlbums();
		allAlbums.add(0, null);
		allAlbums.add(0, null);
		allAlbums.add(0, null);
		allAlbums.add(null);
		allAlbums.add(null);
		allAlbums.add(null);

		LinearLayout albumLayout = (LinearLayout) LayoutInflater.from(getBaseContext()).inflate(R.layout.album_browser, null);
		this.parent.addView(albumLayout, new LinearLayout.LayoutParams(-1, -1));

		final ListView stackView = (ListView) albumLayout.findViewById(R.id.album_stack_view);

		// stackView.setDivider(null);
		// stackView.setDividerHeight(0);
		TypedArray attr = getBaseContext().obtainStyledAttributes(R.styleable.GalleryBackground);
		this.mGalleryItemBackground = attr.getResourceId(R.styleable.GalleryBackground_android_galleryItemBackground, 0);
		attr.recycle();

		final AlbumAdapter albumAdapter = new AlbumAdapter(allAlbums, getBaseContext());
		stackView.setAdapter(albumAdapter);

		final TextView albumTitle = (TextView) albumLayout.findViewById(R.id.album_item_title);
		final ListView songList = (ListView) albumLayout.findViewById(R.id.album_item_list);

		stackView.setDivider(null);

		final AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener()
		{
			private View lastView = null;

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
			{
				int position = firstVisibleItem + visibleItemCount / 2;
				if (allAlbums.get(position) != null)
				{
					View currentView = stackView.getChildAt(visibleItemCount / 2);

					if (lastView != null && lastView != currentView)
					{
						lastView.setBackgroundDrawable(null);
						albumTitle.setText(allAlbums.get(position).getTitle() + " - " + allAlbums.get(position).getArtist());
						songList.setAdapter(new SongListAdapter(LayoutSpielplatzActivity.this, allAlbums.get(position).getSongs()));
					}
					currentView.setBackgroundResource(R.drawable.picture_border);
					lastView = currentView;
				}
			}
		};
		stackView.setOnScrollListener(onScrollListener);

		stackView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{

				stackView.setSelection(position - 3);
			}
		});

	}

	public void showGenre(View v)
	{
		this.parent.removeAllViews();
		final List<Album> allAlbums = this.albumLibrary.getAllAlbums();

		LinearLayout albumLayout = (LinearLayout) LayoutInflater.from(getBaseContext()).inflate(R.layout.album_browser2, null);
		this.parent.addView(albumLayout, new LinearLayout.LayoutParams(-1, -1));

		Gallery gallery = (Gallery) albumLayout.findViewById(R.id.album_gallery);
		TypedArray attr = getBaseContext().obtainStyledAttributes(R.styleable.GalleryBackground);
		this.mGalleryItemBackground = attr.getResourceId(R.styleable.GalleryBackground_android_galleryItemBackground, 0);
		attr.recycle();

		gallery.setAdapter(new AlbumAdapter2(allAlbums, getBaseContext()));

		final TextView albumTitle = (TextView) albumLayout.findViewById(R.id.album_item_title_artist);
		final ListView songList = (ListView) albumLayout.findViewById(R.id.album_songlist);

		gallery.setCallbackDuringFling(true);
		gallery.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				Album album = allAlbums.get(position);
				albumTitle.setText(album.getTitle() + " - " + album.getArtist());
				songList.setAdapter(new SongListAdapter(LayoutSpielplatzActivity.this, album.getSongs()));
			}

			@Override
			public void onNothingSelected(AdapterView<?> v)
			{

			}
		});
	}

	private class AlbumAdapter extends BaseAdapter
	{

		private List<Album> allAlbums;
		private Context context;

		public AlbumAdapter(List<Album> albums, Context c)
		{
			this.allAlbums = albums;
			this.context = c;
		}

		@Override
		public int getCount()
		{
			return this.allAlbums.size();
		}

		@Override
		public Object getItem(int i)
		{
			return this.allAlbums.get(i);
		}

		@Override
		public long getItemId(int i)
		{
			return i;
		}

		@Override
		public View getView(int i, View convertView, ViewGroup parent)
		{
			ImageView imageView = new ImageView(getBaseContext());
			imageView.setLayoutParams(new GridView.LayoutParams(80, 80));
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			// imageView.setBackgroundResource(R.drawable.picture_border);

			Album album = this.allAlbums.get(i);
			if (album != null)
			{
				if (album.getAlbumArtUri() == null)
					imageView.setImageResource(R.drawable.art_not_found);
				else
					imageView.setImageURI(Uri.parse(album.getAlbumArtUri()));
			}
			else
				imageView.setImageDrawable(null);
			return imageView;
		}
	}

	private class AlbumAdapter2 extends BaseAdapter
	{

		private List<Album> allAlbums;

		public AlbumAdapter2(List<Album> albums, Context c)
		{
			this.allAlbums = albums;
		}

		@Override
		public int getCount()
		{
			return this.allAlbums.size();
		}

		@Override
		public Object getItem(int i)
		{
			return this.allAlbums.get(i);
		}

		@Override
		public long getItemId(int i)
		{
			return i;
		}

		@Override
		public View getView(int i, View convertView, ViewGroup parent)
		{
			ImageView imageView = new ImageView(getBaseContext());
			imageView.setLayoutParams(new Gallery.LayoutParams(100, 100));
			// imageView.setScaleType(ImageView.ScaleType.);
			imageView.setBackgroundResource(LayoutSpielplatzActivity.this.mGalleryItemBackground);

			Album album = this.allAlbums.get(i);
			if (album.getAlbumArtUri() == null)
				imageView.setImageResource(R.drawable.art_not_found);
			else
				imageView.setImageURI(Uri.parse(album.getAlbumArtUri()));

			return imageView;
		}
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

	public void showTitle(View v)
	{
		this.showSongList(SortBy.TITLE);
	}

	public void showArtist(View v)
	{
		this.showSongList(SortBy.ARTIST);
	}

	private void showSongList(SortBy sortBy)
	{
		this.parent.removeAllViews();
		LinearLayout songListLayout = (LinearLayout) LayoutInflater.from(getBaseContext()).inflate(R.layout.song_list, null, false);
		this.parent.addView(songListLayout, new LinearLayout.LayoutParams(-1, -1));

		final LinearLayout songList = (LinearLayout) songListLayout.findViewById(R.id.song_list);

		char[] charArray = new char[]
		{
		'#', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
		};

		for (int i = 0; i < charArray.length; i++)
		{
			List<Song> songs = this.songLibrary.getSongs(charArray[i]);

			if (!songs.isEmpty())
			{
				LinearLayout songGroup = (LinearLayout) LayoutInflater.from(getBaseContext()).inflate(R.layout.song_group, null, false);
				songList.addView(songGroup);
				TextView groupTitle = (TextView) songGroup.findViewById(R.id.group_title);
				// groupTitle.setLineColor("#334A7A");
				groupTitle.setText("" + Character.toUpperCase(charArray[i]));

				TableLayout table = (TableLayout) songGroup.findViewById(R.id.group_table);
				table.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

				TableRow row = null;
				for (int songNumber = 0; songNumber < songs.size(); songNumber++)
				{
					Song song = songs.get(songNumber);
					LinearLayout songItem = this.getSongItem((LinearLayout) LayoutInflater.from(getBaseContext()).inflate(R.layout.song_item, null, false), song);

					if (songNumber % 2 == 0)
					{
						row = new TableRow(getBaseContext());
						row.addView(songItem, new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f));
					}
					else
					{
						row.addView(songItem, new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f));
						table.addView(row, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

						if (songNumber + 1 != songs.size())
						{
							View hr = new View(getBaseContext());
							hr.setBackgroundColor(Color.parseColor("#404040"));
							table.addView(hr, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, 1));
						}
					}
				}
				if (songs.size() % 2 == 1)
				{
					Song song = songs.get(songs.size() - 1);
					LinearLayout songItem = this.getSongItem((LinearLayout) LayoutInflater.from(getBaseContext()).inflate(R.layout.song_item, null, false), song);
					row = new TableRow(getBaseContext());
					row.addView(songItem, new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f));
					row.addView(new View(getBaseContext()), new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f));
					table.addView(row, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				}
			}
		}
	}
}
