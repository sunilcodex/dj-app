package de.andy.spielplatz.layout.fragments.browse;

import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import de.andy.spielplatz.R;
import de.andy.spielplatz.adapter.ArtistAdapter;
import de.andy.spielplatz.adapter.SongListAdapter;
import de.andy.spielplatz.dj.Artist;
import de.andy.spielplatz.dj.library.Library;
import de.andy.spielplatz.dj.library.SongLibrary;

public class BrowseByArtist extends Fragment
{
	private List<Artist> allArtists;
	private SongLibrary songLibrary;
	private ArtistAdapter artistAdapter;
	private int lastKnownPosition = 0;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.artist_browser, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		if (this.allArtists == null)
			this.allArtists = Library.getInstance().getArtistLibrary().getAllArtists();

		if (this.songLibrary == null)
			this.songLibrary = Library.getInstance().getSongLibrary();

		if (this.artistAdapter == null)
			this.artistAdapter = new ArtistAdapter(this.getActivity(), this.allArtists);

		final ListView artistList = (ListView) this.getView().findViewById(R.id.browse_artist_artistlist);
		artistList.setAdapter(this.artistAdapter);
		artistList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		final ListView songList = (ListView) this.getView().findViewById(R.id.browse_artist_songlist);
		final TextView artistName = (TextView) this.getView().findViewById(R.id.browse_artist_name);

		Artist artist = this.allArtists.get(this.lastKnownPosition);
		artistName.setText(artist.getName());
		songList.setAdapter(new SongListAdapter(this.getActivity(), this.songLibrary.getAllSongsByArtist(artist.getKey())));
		artistList.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				artistList.setItemChecked(position, true);
				BrowseByArtist.this.lastKnownPosition = position;
				Artist artist = BrowseByArtist.this.allArtists.get(position);
				artistName.setText(artist.getName());
				songList.setAdapter(new SongListAdapter(BrowseByArtist.this.getActivity(), BrowseByArtist.this.songLibrary.getAllSongsByArtist(artist.getKey())));
			}
		});
		artistList.setItemChecked(0, true);
	}
}
