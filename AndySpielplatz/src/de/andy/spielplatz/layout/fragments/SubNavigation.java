package de.andy.spielplatz.layout.fragments;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ListView;
import de.andy.spielplatz.R;
import de.andy.spielplatz.adapter.RightMenuAdapter;
import de.andy.spielplatz.layout.fragments.browse.BrowseByAlbum;
import de.andy.spielplatz.layout.fragments.browse.BrowseByArtist;
import de.andy.spielplatz.layout.fragments.browse.BrowseByTitle;

public class SubNavigation extends ListFragment
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		this.setListAdapter(new RightMenuAdapter(this.getActivity()));
		this.getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		this.getListView().setItemChecked(0, true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.sub_navigation, container, false);
		return view;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		super.onListItemClick(l, v, position, id);
		this.getListView().setItemChecked(position, true);

		Object content = this.getFragmentManager().findFragmentById(R.id.content);
		FragmentTransaction transaction = this.getFragmentManager().beginTransaction();

		switch (position)
		{
			case 1:
				if (content == null || !(content instanceof BrowseByArtist))
					transaction.replace(R.id.content, new BrowseByArtist());
				break;
			case 2:
				if (content == null || !(content instanceof BrowseByAlbum))
					transaction.replace(R.id.content, new BrowseByAlbum());
				break;
			default:
			case 0:
				if (content == null || !(content instanceof BrowseByTitle))
					transaction.replace(R.id.content, new BrowseByTitle());
				break;
		}

		transaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
		transaction.commit();
	}

	public void setVisibility(int visibility)
	{
		this.getListView().setVisibility(visibility);

		if (visibility == ListView.INVISIBLE)
			this.getListView().setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT));
		else
			this.getListView().setLayoutParams(new LinearLayout.LayoutParams(130, LayoutParams.MATCH_PARENT));
	}
}
