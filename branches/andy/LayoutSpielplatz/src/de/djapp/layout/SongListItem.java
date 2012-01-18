package de.djapp.layout;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import de.djapp.andy.R;
import de.djapp.helper.FormatHelper;
import de.djapp.main.Song;

public class SongListItem extends View
{

	public SongListItem(Context context, Song song)
	{
		super(context);

		ImageView albumArt = (ImageView) this.findViewById(R.id.album_art);
		TextView songTitle = (TextView) this.findViewById(R.id.song_title);
		TextView songArtist = (TextView) this.findViewById(R.id.song_artist);
		TextView songDuration = (TextView) this.findViewById(R.id.song_duration);

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

		// this.addView(row);
	}
}
