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

public class MainMenuAdapter extends ArrayAdapter<String>
{
	public MainMenuAdapter(Activity activity)
	{
		super(activity, R.layout.menu_item, Arrays.asList(new String[]
		{
		"Browse", "Playlist", "Turntables", "Misc"
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
			textView.setBackgroundResource(R.drawable.left_navigation_selected_01);
		}
		else
		{
			textView.setTextColor(Color.parseColor("#686868"));
			textView.setBackgroundDrawable(null);
		}

		// textView.setOnClickListener(new View.OnClickListener()
		// {
		//
		// @Override
		// public void onClick(View v)
		// {
		// v.setBackgroundResource(R.drawable.left_navigation_selected);
		// }
		// });

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
