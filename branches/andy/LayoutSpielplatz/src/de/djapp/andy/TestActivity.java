package de.djapp.andy;

import android.app.Activity;
import android.os.Bundle;
import de.djapp.main.SongLibrary;

public class TestActivity extends Activity
{
	private SongLibrary songLibrary;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		this.songLibrary = new SongLibrary(getContentResolver());

	}
}
