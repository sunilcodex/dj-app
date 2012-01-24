package de.andy.spielplatz.adapter;

import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import de.andy.spielplatz.R;
import de.andy.spielplatz.dj.Artist;

public class ArtistAdapter extends ArrayAdapter<Artist>
{
	public ArtistAdapter(Activity activity, List<Artist> objects)
	{
		super(activity, android.R.layout.simple_list_item_activated_1, objects);
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

		if (((ListView) parent).getCheckedItemPosition() == position)
			// convertView.setBackgroundColor(Color.parseColor("#C0D8F0"));
			convertView.setBackgroundResource(R.drawable.group_header_border);
		else
			convertView.setBackgroundColor(Color.WHITE);

		((TextView) convertView).setText(this.getItem(position).getName());
		convertView.setPadding(4, 2, 4, 2);

		return convertView;
	}
}
