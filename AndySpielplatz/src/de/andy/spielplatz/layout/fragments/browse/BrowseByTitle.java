package de.andy.spielplatz.layout.fragments.browse;

import android.app.ListFragment;
import android.os.Bundle;
import de.andy.spielplatz.adapter.SongListAdapter;
import de.andy.spielplatz.dj.SongLibrary;
import de.andy.spielplatz.dj.SortBy;

public class BrowseByTitle extends ListFragment
{
	private SongLibrary songLibrary;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.songLibrary = new SongLibrary(this.getActivity().getContentResolver());
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		this.setListAdapter(new SongListAdapter(this.getActivity(), this.songLibrary.getAllSongs(SortBy.TITLE)));
	}
}
