package de.andy.spielplatz.layout.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import de.andy.spielplatz.R;
import de.andy.spielplatz.adapter.MainMenuAdapter;

public class MainNavigation extends ListFragment
{
	private SubNavigation subNavigation;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		this.subNavigation = (SubNavigation) getFragmentManager().findFragmentById(R.id.sub_navigation_fragment);
		this.setListAdapter(new MainMenuAdapter(this.getActivity()));
		this.getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		this.getListView().setItemChecked(0, true);
		this.subNavigation.setVisibility(View.VISIBLE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.main_navigation, container, false);
		return view;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		super.onListItemClick(l, v, position, id);
		getListView().setItemChecked(position, true);

		switch (position)
		{
			case 0:
				this.subNavigation.setVisibility(ListView.VISIBLE);
				break;
			default:
				this.subNavigation.setVisibility(ListView.INVISIBLE);
		}

	}
}
