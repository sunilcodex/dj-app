package de.andy.spielplatz.adapter;

import java.util.List;

import de.andy.spielplatz.R;
import de.andy.spielplatz.dj.Artist;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ArtistAdapter extends ArrayAdapter<Artist>
{
	public ArtistAdapter(Activity activity, List<Artist> objects)
	{
		super(activity, R.layout.song_item, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		if (convertView == null)
		{
			convertView = new TextView(getContext());
			convertView.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, 30));
			((TextView) convertView).setTextSize(18);
			((TextView) convertView).setGravity(Gravity.CENTER_VERTICAL);
		}
		((TextView) convertView).setText(this.getItem(position).getName());
		((TextView) convertView).setPadding(4, 2, 4, 2);

		return convertView;
	}
}
