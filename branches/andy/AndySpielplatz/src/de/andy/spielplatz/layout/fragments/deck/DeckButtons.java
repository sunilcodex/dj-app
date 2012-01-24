package de.andy.spielplatz.layout.fragments.deck;

import android.app.Fragment;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import de.andy.spielplatz.R;
import de.andy.spielplatz.dj.Deck;
import de.andy.spielplatz.dj.Turntables;

public class DeckButtons extends Fragment
{
	private int deck = -1;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);

		ImageButton btnPrev = (ImageButton) view.findViewById(R.id.deck_buttons_prev);
		final ImageButton btnPlay = (ImageButton) view.findViewById(R.id.deck_buttons_play);
		ImageButton btnNext = (ImageButton) view.findViewById(R.id.deck_buttons_next);

		btnPrev.setSoundEffectsEnabled(false);
		btnPrev.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
				switch (DeckButtons.this.deck)
				{
					case Deck.DECK_A:
						Turntables.getInstance().getDeckA().previousSong();
						break;
					case Deck.DECK_B:
						Turntables.getInstance().getDeckB().previousSong();
						break;
				}
			}
		});

		btnPlay.setSoundEffectsEnabled(false);
		btnPlay.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
				switch (DeckButtons.this.deck)
				{
					case Deck.DECK_A:

						if (Turntables.getInstance().getDeckA().isReady())
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
						break;

					case Deck.DECK_B:
						if (Turntables.getInstance().getDeckB().isReady())
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
						break;
				}
			}
		});

		btnPlay.setOnLongClickListener(new OnLongClickListener()
		{
			@Override
			public boolean onLongClick(View v)
			{
				switch (DeckButtons.this.deck)
				{
					case Deck.DECK_A:
						Turntables.getInstance().getDeckA().stop();
						break;
					case Deck.DECK_B:
						Turntables.getInstance().getDeckB().stop();
						break;
				}
				btnPlay.setImageDrawable(getResources().getDrawable(R.drawable.play));
				return true;
			}
		});

		btnNext.setSoundEffectsEnabled(false);
		btnNext.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
				switch (DeckButtons.this.deck)
				{
					case Deck.DECK_A:
						Turntables.getInstance().getDeckA().nextSong();
						break;
					case Deck.DECK_B:
						Turntables.getInstance().getDeckB().nextSong();
						break;
				}
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.deck_buttons, container, false);
	}

	public void setDeck(int deck)
	{
		this.deck = deck;
	}
}
