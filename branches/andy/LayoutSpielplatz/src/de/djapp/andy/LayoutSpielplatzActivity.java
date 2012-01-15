package de.djapp.andy;

import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import de.djapp.helper.FormatHelper;
import de.djapp.main.Song;
import de.djapp.main.SongLibrary;

public class LayoutSpielplatzActivity extends Activity
{
	private SongLibrary songLibrary;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		this.songLibrary = new SongLibrary(getContentResolver());

		LinearLayout parent = (LinearLayout) findViewById(R.id.centerNavigation);
		LinearLayout view = (LinearLayout) LayoutInflater.from(getBaseContext()).inflate(R.layout.song_list, null, false);
		parent.addView(view, new LinearLayout.LayoutParams(-1, -1));

		final LinearLayout songList = (LinearLayout) view.findViewById(R.id.song_list);

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
				LinearLayout columns = null;

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

				// int col = 0;
				// for (Song song : songs)
				// {
				// if (col % 2 == 0)
				// {
				// columns = new LinearLayout(getBaseContext());
				// columns.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
				// columns.setOrientation(LinearLayout.HORIZONTAL);
				// }
				//
				// LinearLayout songItem = (LinearLayout) LayoutInflater.from(getBaseContext()).inflate(R.layout.song_item, null, false);
				// // songItem.setLineColor("#FF0000");
				// TextView songTitle = (TextView) songItem.findViewById(R.id.song_title);
				// songTitle.setText(song.getTitle());
				// TextView songArtist = (TextView) songItem.findViewById(R.id.song_artist);
				// songArtist.setText(song.getArtist());
				// TextView songDuration = (TextView) songItem.findViewById(R.id.song_duration);
				// songDuration.setText(FormatHelper.formatDuration(song.getDuration()));
				// ImageView songAlbumArt = (ImageView) songItem.findViewById(R.id.album_art);
				//
				// if (song.getAlbumArtUri() == null)
				// songAlbumArt.setImageResource(R.drawable.art_not_found);
				// else
				// songAlbumArt.setImageURI(Uri.parse(song.getAlbumArtUri()));
				//
				// songItem.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f));
				// columns.addView(songItem);
				//
				// if (col % 2 == 1)
				// songGroup.addView(columns);
				// if (songs.indexOf(song) == songs.size() - 1)
				// songItem.findViewById(R.id.song_item_border).setVisibility(View.INVISIBLE);
				// col++;
				// }
			}
		}
	}

	private LinearLayout getSongItem(LinearLayout songItem, Song song)
	{
		// songItem.setLineColor("#FF0000");
		TextView songTitle = (TextView) songItem.findViewById(R.id.song_title);
		songTitle.setText(song.getTitle());
		TextView songArtist = (TextView) songItem.findViewById(R.id.song_artist);
		songArtist.setText(song.getArtist());
		TextView songDuration = (TextView) songItem.findViewById(R.id.song_duration);
		songDuration.setText(FormatHelper.formatDuration(song.getDuration()));
		ImageView songAlbumArt = (ImageView) songItem.findViewById(R.id.album_art);

		if (song.getAlbumArtUri() == null)
			songAlbumArt.setImageResource(R.drawable.art_not_found);
		else
			songAlbumArt.setImageURI(Uri.parse(song.getAlbumArtUri()));

		return songItem;
	}
}
