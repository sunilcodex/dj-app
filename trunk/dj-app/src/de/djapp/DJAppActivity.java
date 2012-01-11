package de.djapp;

import android.app.Activity;
import android.os.Bundle;

public class DJAppActivity extends Activity
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main);
	}
}