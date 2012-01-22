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
import de.andy.spielplatz.dj.ArtistLibrary;
import de.andy.spielplatz.dj.SongLibrary;

public class BrowseByArtist extends Fragment
{
	private SongLibrary songLibrary;
	private List<Artist> allArtists;
	private ArtistLibrary artistLibrary;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.songLibrary = new SongLibrary(this.getActivity().getContentResolver());
		this.artistLibrary = new ArtistLibrary(this.getActivity().getContentResolver(), this.songLibrary);
		this.allArtists = this.artistLibrary.getAllArtists();
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

		ListView artistList = (ListView) this.getView().findViewById(R.id.browse_artist_artistlist);
		artistList.setAdapter(new ArtistAdapter(this.getActivity(), this.allArtists));

		final ListView songList = (ListView) this.getView().findViewById(R.id.browse_artist_songlist);
		final TextView artistName = (TextView) this.getView().findViewById(R.id.browse_artist_name);

		Artist artist = this.allArtists.get(0);
		artistName.setText(artist.getName());
		songList.setAdapter(new SongListAdapter(this.getActivity(), this.songLibrary.getAllSongsByArtist(artist.getKey())));

		artistList.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Artist artist = allArtists.get(position);
				artistName.setText(artist.getName());
				songList.setAdapter(new SongListAdapter(BrowseByArtist.this.getActivity(), BrowseByArtist.this.songLibrary.getAllSongsByArtist(artist.getKey())));
			}
		});

	}
}
