package de.andy.spielplatz.adapter;

import java.util.List;

import android.app.Activity;
import android.content.ClipData;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import de.andy.spielplatz.R;
import de.andy.spielplatz.dj.Song;
import de.andy.spielplatz.helper.FormatHelper;

public class SongListAdapter extends ArrayAdapter<Song>
{
	private List<Song> allSongs;
	private LayoutInflater layoutInflater;

	public SongListAdapter(Activity activity, List<Song> objects)
	{
		super(activity, R.layout.song_item, objects);
		this.allSongs = objects;
		this.layoutInflater = activity.getLayoutInflater();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		final ViewHolder holder;

		if (convertView == null)
		{
			convertView = this.layoutInflater.inflate(R.layout.song_item, null);

			holder = new ViewHolder();
			holder.albumArt = (ImageView) convertView.findViewById(R.id.album_art);
			holder.artist = (TextView) convertView.findViewById(R.id.song_artist);
			holder.title = (TextView) convertView.findViewById(R.id.song_title);
			holder.duration = (TextView) convertView.findViewById(R.id.song_duration);

			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}

		final Song song = this.allSongs.get(position);

		String uriString = song.getAlbumArtUri();

		if (uriString != null)
		{
			Uri albumArtUri = Uri.parse(uriString);
			holder.albumArt.setImageURI(albumArtUri);
		}
		else
		{
			holder.albumArt.setImageResource(R.drawable.art_not_found);
		}
		holder.title.setText(song.getTitle());
		holder.artist.setText(song.getArtist());
		holder.duration.setText(FormatHelper.formatDuration(song.getDuration()));

		OnDrag onDragListener = new OnDrag(position, song, holder);

		holder.title.setOnLongClickListener(onDragListener);
		holder.artist.setOnLongClickListener(onDragListener);
		holder.albumArt.setOnLongClickListener(onDragListener);

		return convertView;
	}

	static class ViewHolder
	{
		ImageView albumArt;
		TextView title;
		TextView artist;
		TextView duration;
		Button buttonDeckA;
		Button buttonDeckB;
	}

	private class OnDrag implements OnLongClickListener
	{
		private int position;
		private Song song;
		private ViewHolder holder;

		public OnDrag(int position, Song song, ViewHolder holder)
		{
			this.position = position;
			this.song = song;
			this.holder = holder;
		}

		@Override
		public boolean onLongClick(View v)
		{
			ClipData data = ClipData.newPlainText("", "" + this.position);
			v.startDrag(data, new DragShadowBuilder(holder.albumArt), this.song, 0);
			return true;
		}
	}

}
