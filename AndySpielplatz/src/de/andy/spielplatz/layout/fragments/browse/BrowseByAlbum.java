package de.andy.spielplatz.layout.fragments.browse;

import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.ListView;
import android.widget.TextView;
import de.andy.spielplatz.R;
import de.andy.spielplatz.adapter.GalleryAdapter;
import de.andy.spielplatz.adapter.SongListAdapter;
import de.andy.spielplatz.dj.Album;
import de.andy.spielplatz.dj.AlbumLibrary;
import de.andy.spielplatz.dj.SongLibrary;

public class BrowseByAlbum extends Fragment
{
	private SongLibrary songLibrary;
	private List<Album> allAlbums;
	private AlbumLibrary albumLibrary;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.songLibrary = new SongLibrary(this.getActivity().getContentResolver());
		this.albumLibrary = new AlbumLibrary(this.getActivity().getContentResolver(), this.songLibrary);
		this.allAlbums = this.albumLibrary.getAllAlbums();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.album_browser, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		Gallery gallery = (Gallery) this.getView().findViewById(R.id.album_gallery);

		gallery.setAdapter(new GalleryAdapter(allAlbums, this.getActivity()));

		final TextView albumTitle = (TextView) this.getView().findViewById(R.id.album_item_title_artist);
		final ListView songList = (ListView) this.getView().findViewById(R.id.album_songlist);

		gallery.setCallbackDuringFling(true);
		gallery.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				Album album = allAlbums.get(position);
				if (album != null)
				{
					albumTitle.setText(album.getTitle() + " - " + album.getArtist());
					songList.setAdapter(new SongListAdapter(BrowseByAlbum.this.getActivity(), album.getSongs()));
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> v)
			{

			}
		});
	}
}
