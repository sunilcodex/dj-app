package de.andy.spielplatz.adapter;

import java.util.Arrays;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import de.andy.spielplatz.R;

public class BrowseMenuAdapter extends ArrayAdapter<String>
{
	public static final int TITLE = 0;
	public static final int ARTIST = 1;
	public static final int ALBUM = 2;
	public static final int GENRE = 3;
	public static final int FAVORITES = 4;
	public static final int TAGS = 5;

	public BrowseMenuAdapter(Activity activity)
	{
		super(activity, R.layout.menu_item, Arrays.asList(new String[]
		{
		"Title", "Artist", "Album", "Genre", "Favorites", "Tags"
		}));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		if (convertView == null)
			convertView = new TextView(this.getContext());
		TextView textView = (TextView) convertView;

		textView.setText(getItem(position));
		textView.setHeight(40);
		textView.setGravity(Gravity.CENTER);
		textView.setTextSize(18);

		ListView listView = (ListView) parent;
		if (position == listView.getCheckedItemPosition())
		{
			textView.setTextColor(Color.parseColor("#0099CC"));
			textView.setBackgroundResource(R.drawable.left_navigation_selected);
		}
		else
		{
			textView.setTextColor(Color.parseColor("#686868"));
			textView.setBackgroundDrawable(null);
		}

		return convertView;
	}

	static class ViewHolder
	{
		ImageView albumArt;
		TextView title;
		TextView artist;
		TextView duration;
		Button buttonDeckA;
		Button buttonDeckB;
	}

}
