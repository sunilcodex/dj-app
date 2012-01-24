package de.andy.spielplatz.layout.fragments.deck;

import android.app.Fragment;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import de.andy.spielplatz.R;
import de.andy.spielplatz.dj.Deck;
import de.andy.spielplatz.dj.Playlist;
import de.andy.spielplatz.dj.Song;
import de.andy.spielplatz.dj.Turntables;
import de.andy.spielplatz.helper.FormatHelper;

public class SongDisplay extends Fragment
{

	private Song song;
	private View view;
	private ImageView albumArt;
	private TextView artist;
	private TextView title;
	private TextView duration;

	public int deck = -1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		if (this.view == null)
			this.view = this.getActivity().getLayoutInflater().inflate(R.layout.deck_song_display, container, false);
		this.albumArt = (ImageView) view.findViewById(R.id.album_art);
		this.artist = (TextView) view.findViewById(R.id.song_artist);
		this.title = (TextView) view.findViewById(R.id.song_title);
		this.duration = (TextView) view.findViewById(R.id.song_duration);

		this.updateSongDisplay();
		this.view.setOnDragListener(new DropListener());
		return view;
	}

	private void updateSongDisplay()
	{
		if (this.song == null)
		{
			this.albumArt.setVisibility(View.INVISIBLE);
			this.duration.setVisibility(View.INVISIBLE);
			// this.artist.setVisibility(View.INVISIBLE);
			this.artist.setText("no song selected. please drop a song here");
			this.artist.setGravity(Gravity.CENTER);
			this.title.setVisibility(View.INVISIBLE);
		}
		else
		{
			this.albumArt.setVisibility(View.VISIBLE);
			this.duration.setVisibility(View.VISIBLE);
			this.artist.setVisibility(View.VISIBLE);
			this.artist.setGravity(Gravity.LEFT);
			this.title.setVisibility(View.VISIBLE);
			this.view.setBackgroundResource(R.drawable.song_deck_border);

			String uriString = this.song.getAlbumArtUri();

			if (uriString != null)
			{
				Uri albumArtUri = Uri.parse(uriString);
				this.albumArt.setImageURI(albumArtUri);
			}
			else
			{
				this.albumArt.setImageResource(R.drawable.art_not_found);
			}

			this.title.setText(this.song.getTitle());
			this.artist.setText(this.song.getArtist());
			this.duration.setText(FormatHelper.formatDuration(this.song.getDuration()));
		}
	}

	private class DropListener implements OnDragListener
	{

		@Override
		public boolean onDrag(View v, DragEvent event)
		{
			Object content = event.getLocalState();

			switch (event.getAction())
			{
				case DragEvent.ACTION_DRAG_STARTED:
					if (content instanceof Song || content instanceof Playlist)
					{
						v.setBackgroundColor(Color.parseColor("#C0D8F0"));
						return true;
					}
					else
					{
						v.setBackgroundResource(R.drawable.song_deck_border);
						return false;
					}

				case DragEvent.ACTION_DRAG_ENTERED:
					if (content instanceof Song || content instanceof Playlist)
					{
						v.setBackgroundColor(Color.parseColor("#B7FFA3"));
						return true;
					}
					else
					{
						v.setBackgroundResource(R.drawable.song_deck_border);
						return false;
					}

				case DragEvent.ACTION_DROP:
					if (content instanceof Song || content instanceof Playlist)
					{
						SongDisplay.this.song = (Song) content;
						SongDisplay.this.updateSongDisplay();

						Playlist playlist = new Playlist();
						playlist.addSong(song);

						switch (SongDisplay.this.deck)
						{
							case Deck.DECK_A:
								Turntables.getInstance().getDeckA().setPlaylist(playlist);
								break;
							case Deck.DECK_B:
								Turntables.getInstance().getDeckB().setPlaylist(playlist);
								break;
						}
						return true;
					}
					else
					{
						v.setBackgroundResource(R.drawable.song_deck_border);
						return false;
					}

				case DragEvent.ACTION_DRAG_ENDED:
					v.setBackgroundResource(R.drawable.song_deck_border);
					break;
			}

			return false;
		}
	}

	public void setDeck(int deck)
	{
		this.deck = deck;
	}
}
