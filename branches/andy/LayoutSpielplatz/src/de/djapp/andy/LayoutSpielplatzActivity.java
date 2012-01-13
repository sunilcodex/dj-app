package de.djapp.andy;

import android.app.Activity;
import android.content.ClipData;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnDragListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import de.djapp.layout.SongListAdapter;
import de.djapp.main.SongLibrary;
import de.djapp.main.SortBy;

public class LayoutSpielplatzActivity extends Activity
{
	private SongLibrary songLibrary;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		this.songLibrary = new SongLibrary(getContentResolver());

		LinearLayout parent = (LinearLayout) findViewById(R.id.centerNavigation);
		LinearLayout view = (LinearLayout) LayoutInflater.from(getBaseContext()).inflate(R.layout.songlist, null, false);
		parent.addView(view, new LinearLayout.LayoutParams(-1, -1));

		final GridView songList = (GridView) view.findViewById(R.id.song_list);
		songList.setAdapter(new SongListAdapter(this, this.songLibrary.getAllSongs(SortBy.TITLE)));

		Spinner sortBySpinner = (Spinner) view.findViewById(R.id.spinner_sort_by);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sort_by_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sortBySpinner.setAdapter(adapter);

		sortBySpinner.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
			{
				if (parent.getItemAtPosition(pos).toString().equals("Title"))
					songList.setAdapter(new SongListAdapter(LayoutSpielplatzActivity.this, songLibrary.getAllSongs(SortBy.TITLE)));
				else if (parent.getItemAtPosition(pos).toString().equals("Album"))
					songList.setAdapter(new SongListAdapter(LayoutSpielplatzActivity.this, songLibrary.getAllSongs(SortBy.ALBUM)));
				else if (parent.getItemAtPosition(pos).toString().equals("Artist"))
					songList.setAdapter(new SongListAdapter(LayoutSpielplatzActivity.this, songLibrary.getAllSongs(SortBy.ARTIST)));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
			}
		});

		songList.setOnItemLongClickListener(new OnItemLongClickListener()
		{

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id)
			{

				ClipData clipData = ClipData.newPlainText("", "" + pos);
				View.DragShadowBuilder dsb = new View.DragShadowBuilder(view);
				view.startDrag(clipData, dsb, view, 0);

				return true;
			}
		});

		TextView deckA = (TextView) findViewById(R.id.deckA);
		deckA.setOnDragListener(new OnDragListener()
		{

			@Override
			public boolean onDrag(View view, DragEvent event)
			{
				Log.i("LOG", "" + event.getAction());

				switch (event.getAction())
				{
					case DragEvent.ACTION_DRAG_ENDED:
						return true;
				}

				return false;
			}
		});
	}
}
