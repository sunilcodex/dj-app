package de.andy.spielplatz.layout.fragments.deck;

import java.util.Random;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import de.andy.spielplatz.R;
import de.andy.spielplatz.dj.Playlist;
import de.andy.spielplatz.dj.SortBy;
import de.andy.spielplatz.dj.Turntables;
import de.andy.spielplatz.dj.library.Library;

public class DeckButtonsA extends Fragment
{

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);

		ImageButton btnPrev = (ImageButton) view.findViewById(R.id.deck_buttons_prev);
		final ImageButton btnPlay = (ImageButton) view.findViewById(R.id.deck_buttons_play);
		ImageButton btnNext = (ImageButton) view.findViewById(R.id.deck_buttons_next);

		Playlist playlist = new Playlist();
		Library.instantiate(getActivity().getContentResolver());
		playlist.addSong(Library.getInstance().getSongLibrary().getAllSongs(SortBy.TITLE).get(new Random().nextInt(10)));
		Turntables.getInstance().getDeckA().setPlaylist(playlist);

		btnPrev.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Turntables.getInstance().getDeckA().previousSong();
			}
		});

		btnPlay.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (!Turntables.getInstance().getDeckA().isPlaying())
				{
					Turntables.getInstance().getDeckA().play();
					btnPlay.setImageDrawable(getResources().getDrawable(R.drawable.pause));
				}
				else
				{
					Turntables.getInstance().getDeckA().pause();
					btnPlay.setImageDrawable(getResources().getDrawable(R.drawable.play));
				}
			}
		});

		btnPlay.setOnLongClickListener(new OnLongClickListener()
		{

			@Override
			public boolean onLongClick(View v)
			{
				Turntables.getInstance().getDeckA().stop();
				btnPlay.setImageDrawable(getResources().getDrawable(R.drawable.play));
				return true;
			}
		});

		btnPlay.setOnDragListener(new OnDragListener()
		{

			@Override
			public boolean onDrag(View v, DragEvent event)
			{
				switch (event.getAction())
				{
					case DragEvent.ACTION_DRAG_ENTERED:
						Log.i("", "FADS");
						v.setBackgroundColor(Color.RED);
						break;
				}
				return false;
			}
		});

		btnNext.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Turntables.getInstance().getDeckA().nextSong();
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.deck_buttons, container, false);
	}
}
