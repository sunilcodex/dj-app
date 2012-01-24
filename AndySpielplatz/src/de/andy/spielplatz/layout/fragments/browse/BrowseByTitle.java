package de.andy.spielplatz.layout.fragments.browse;

import android.app.ListFragment;
import android.os.Bundle;
import de.andy.spielplatz.adapter.SongListAdapter;
import de.andy.spielplatz.dj.SortBy;
import de.andy.spielplatz.dj.library.Library;
import de.andy.spielplatz.dj.library.SongLibrary;

public class BrowseByTitle extends ListFragment
{
	private SongLibrary songLibrary;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		if (this.songLibrary == null)
			this.songLibrary = Library.getInstance().getSongLibrary();

		if (this.getListAdapter() == null)
			this.setListAdapter(new SongListAdapter(this.getActivity(), this.songLibrary.getAllSongs(SortBy.TITLE)));
	}
}
