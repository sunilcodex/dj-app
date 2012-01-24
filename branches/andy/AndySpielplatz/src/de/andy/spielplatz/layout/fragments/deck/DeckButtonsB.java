package de.andy.spielplatz.layout.fragments.deck;

import java.util.Random;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import de.andy.spielplatz.R;
import de.andy.spielplatz.dj.Playlist;
import de.andy.spielplatz.dj.SortBy;
import de.andy.spielplatz.dj.Turntables;
import de.andy.spielplatz.dj.library.Library;

public class DeckButtonsB extends Fragment
{
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);

		ImageButton btnPrev = (ImageButton) view.findViewById(R.id.deck_buttons_prev);
		final ImageButton btnPlay = (ImageButton) view.findViewById(R.id.deck_buttons_play);
		ImageButton btnNext = (ImageButton) view.findViewById(R.id.deck_buttons_next);

		Playlist playlist = new Playlist();
		playlist.addSong(Library.getInstance().getSongLibrary().getAllSongs(SortBy.TITLE).get(new Random().nextInt(10)));
		Turntables.getInstance().getDeckB().setPlaylist(playlist);

		btnPrev.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Turntables.getInstance().getDeckB().previousSong();
			}
		});

		btnPlay.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (!Turntables.getInstance().getDeckB().isPlaying())
				{
					Turntables.getInstance().getDeckB().play();
					btnPlay.setImageDrawable(getResources().getDrawable(R.drawable.pause));
				}
				else
				{
					Turntables.getInstance().getDeckB().pause();
					btnPlay.setImageDrawable(getResources().getDrawable(R.drawable.play));
				}

			}
		});

		btnNext.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Turntables.getInstance().getDeckB().nextSong();
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.deck_buttons, container, false);
	}
}
