package de.djapp.layout;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import de.djapp.R;
import de.djapp.helper.FormatHelper;
import de.djapp.main.Song;

public class SongListAdapter extends ArrayAdapter<Song>
{
	private Context context;
	private List<Song> allSongs;

	public SongListAdapter(Context context, List<Song> objects)
	{
		super(context, R.layout.song_item, objects);
		this.context = context;
		this.allSongs = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View row = convertView;

		if (row == null)
		{
			LayoutInflater inflater = ((Activity) this.context).getLayoutInflater();
			row = inflater.inflate(R.layout.song_item, parent, false);
		}

		ImageView albumArt = (ImageView) row.findViewById(R.id.album_art);
		TextView songTitle = (TextView) row.findViewById(R.id.song_title);
		TextView songArtist = (TextView) row.findViewById(R.id.song_artist);
		TextView songDuration = (TextView) row.findViewById(R.id.song_duration);

		Song song = this.allSongs.get(position);

		String uriString = song.getAlbumArtUri();

		if (uriString != null)
		{
			Uri albumArtUri = Uri.parse(uriString);
			albumArt.setImageURI(albumArtUri);
		}
		else
		{
			albumArt.setImageResource(R.drawable.art_not_found);
		}

		songTitle.setText(song.getTitle());
		songArtist.setText(song.getArtist());
		songDuration.setText(FormatHelper.formatDuration(song.getDuration()));

		return row;
	}

}
