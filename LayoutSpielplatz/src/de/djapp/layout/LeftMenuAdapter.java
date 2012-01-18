package de.djapp.layout;

import java.util.Arrays;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import de.djapp.andy.R;

public class LeftMenuAdapter extends ArrayAdapter<String>
{
	public LeftMenuAdapter(Activity activity)
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
