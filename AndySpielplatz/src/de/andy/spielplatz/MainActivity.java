package de.andy.spielplatz;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import de.andy.spielplatz.dj.Deck;
import de.andy.spielplatz.dj.Turntables;
import de.andy.spielplatz.dj.library.Library;
import de.andy.spielplatz.layout.fragments.deck.DeckButtons;
import de.andy.spielplatz.layout.fragments.deck.SongDisplay;

public class MainActivity extends Activity
{

	private static final int SWIPE_MIN_DISTANCE = 60;
	private static final int SWIPE_MAX_OFF_PATH = 100;
	private static final int SWIPE_THRESHOLD_VELOCITY = 100;
	private GestureDetector gestureDetector;
	View.OnTouchListener gestureListener;
	private SeekBar seekBar;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Library.instantiate(getContentResolver());

		SongDisplay displayA = (SongDisplay) getFragmentManager().findFragmentById(R.id.deck_a_song_display);
		displayA.setDeck(Deck.DECK_A);
		SongDisplay displayB = (SongDisplay) getFragmentManager().findFragmentById(R.id.deck_b_song_display);
		displayB.setDeck(Deck.DECK_B);

		DeckButtons deckButtonsA = (DeckButtons) getFragmentManager().findFragmentById(R.id.deck_a_buttons);
		deckButtonsA.setDeck(Deck.DECK_A);
		DeckButtons deckButtonsB = (DeckButtons) getFragmentManager().findFragmentById(R.id.deck_b_buttons);
		deckButtonsB.setDeck(Deck.DECK_B);

		this.seekBar = (SeekBar) findViewById(R.id.fader);
		gestureDetector = new GestureDetector(new MyGestureDetector());
		gestureListener = new View.OnTouchListener()
		{
			public boolean onTouch(View v, MotionEvent event)
			{
				return gestureDetector.onTouchEvent(event);
			}
		};

		seekBar.setOnTouchListener(gestureListener);
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
		{

			@Override
			public void onStopTrackingTouch(SeekBar seekBar)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
			{
				Turntables.getInstance().getDeckA().setVolume(1f - (progress / 100f));
				Turntables.getInstance().getDeckB().setVolume(progress / 100f);
			}
		});
	}

	class MyGestureDetector extends SimpleOnGestureListener
	{
		// @Override
		// public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
		// {
		// try
		// {
		// if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
		// return false;
		// // right to left swipe
		// if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY)
		// {
		// Toast.makeText(MainActivity.this, "Left Swipe: " + (e1.getX() - e2.getX()), Toast.LENGTH_SHORT).show();
		// int diff = (int) (e1.getX() - e2.getX());
		// ObjectAnimator anim = ObjectAnimator.ofInt(seekBar, "progress", seekBar.getProgress(), seekBar.getProgress() - diff / 4);
		// anim.setDuration(500);
		// anim.start();
		//
		// }
		// else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY)
		// {
		// Toast.makeText(MainActivity.this, "Right Swipe: " + (e2.getX() - e1.getX()), Toast.LENGTH_SHORT).show();
		// int diff = (int) (e2.getX() - e1.getX());
		// ObjectAnimator anim = ObjectAnimator.ofInt(seekBar, "progress", seekBar.getProgress(), seekBar.getProgress() + diff / 4);
		// anim.setDuration(500);
		// anim.start();
		// }
		// }
		// catch (Exception e)
		// {
		// // nothing
		// }
		// return false;
		// }

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
		{
			try
			{
				float diff = e1.getX() - e2.getY();
				Log.i("FLING", "" + diff + " " + (velocityX / 200));

				int fling = (int) (velocityX / 10);

				ObjectAnimator anim;
				if (fling < 0)
					anim = ObjectAnimator.ofInt(seekBar, "progress", seekBar.getProgress(), seekBar.getProgress() + fling);
				else
					anim = ObjectAnimator.ofInt(seekBar, "progress", seekBar.getProgress(), seekBar.getProgress() + fling);

				anim.setDuration(100);
				anim.start();
			}
			catch (Exception e)
			{
				// nothing
			}
			return false;
		}
	}
}