package de.andy.spielplatz;

import android.app.Activity;
import android.os.Bundle;
import de.andy.spielplatz.dj.Deck;
import de.andy.spielplatz.layout.fragments.deck.SongDisplay;

public class MainActivity extends Activity
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		SongDisplay displayA = (SongDisplay) getFragmentManager().findFragmentById(R.id.deck_a_song_display);
		displayA.setDeck(Deck.DECK_A);
		SongDisplay displayB = (SongDisplay) getFragmentManager().findFragmentById(R.id.deck_b_song_display);
		displayB.setDeck(Deck.DECK_B);
	}
}