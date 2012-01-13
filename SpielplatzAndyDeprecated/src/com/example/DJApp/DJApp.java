package com.example.DJApp;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

public class DJApp extends Activity
{

	private static final String MEDIA_PATH = new String("/sdcard/Music/");
	private List<String> songs = new ArrayList<String>();

	private int currentPositionPlayer1 = 0;
	private int currentPositionPlayer2 = 0;

	private AudioPlayer audioPlayer1 = new AudioPlayer();
	private AudioPlayer audioPlayer2 = new AudioPlayer();
	private Deck deck = new Deck();

	private ImageButton play1Button;
	private ImageButton play2Button;
	private ImageButton next1Button;
	private ImageButton next2Button;
	private ImageButton prev1Button;
	private ImageButton prev2Button;
	private SeekBar faderSeekBar;
	private SeekBar progressBar;
	private TextView nowPlayingText1;

	private Handler handler;
	private TimerTask testTask;
	private SongLibrary songLibrary;

	@Override
	public void onCreate(Bundle icicle)
	{
		super.onCreate(icicle);
		setContentView(R.layout.main);

		audioPlayer1.setVolume(0.5f);
		audioPlayer2.setVolume(0.5f);

		play1Button = ((ImageButton) findViewById(R.id.play1));
		play1Button.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				if (audioPlayer1.getAudioFile() != "")
				{
					if (audioPlayer1.isPlaying())
					{
						audioPlayer1.pause();
						play1Button.setImageDrawable(getResources().getDrawable(R.drawable.playbutton));
					}
					else
					{
						audioPlayer1.start();
						play1Button.setImageDrawable(getResources().getDrawable(R.drawable.pausebutton));
					}
				}
			}
		});

		play2Button = ((ImageButton) findViewById(R.id.play2));
		play2Button.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				if (!deck.isPlaying())
					deck.play();
				else
					deck.pause();
			}
		});

		play2Button.setOnLongClickListener(new OnLongClickListener()
		{

			public boolean onLongClick(View v)
			{
				deck.stop();
				return true;
			}
		});

		next1Button = ((ImageButton) findViewById(R.id.next1));
		next1Button.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				if (audioPlayer1.getAudioFile() != "")
				{
					if (currentPositionPlayer1 + 1 < songs.size())
						currentPositionPlayer1++;
					else
						currentPositionPlayer1 = 0;
					setSong1();
				}
			}
		});

		prev1Button = ((ImageButton) findViewById(R.id.prev1));
		prev1Button.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				if (audioPlayer1.getAudioFile() != "")
				{
					if (currentPositionPlayer1 - 1 >= 0)
						currentPositionPlayer1--;
					else
						currentPositionPlayer1 = songs.size() - 1;
					setSong1();
				}
			}
		});

		faderSeekBar = (SeekBar) findViewById(R.id.fader);
		faderSeekBar.setMax(100);
		faderSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
		{

			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2)
			{
				float value = arg1 / 100f;
				audioPlayer1.setVolume(1 - value);
				audioPlayer2.setVolume(value);
			}

			public void onStartTrackingTouch(SeekBar arg0)
			{
				// TODO Auto-generated method stub

			}

			public void onStopTrackingTouch(SeekBar arg0)
			{
				// TODO Auto-generated method stub

			}
		});

		faderSeekBar.setProgress(50);
		updateSongList();

		progressBar = (SeekBar) findViewById(R.id.volume1);

		progressBar.setProgress(0);
		progressBar.setMax(100);

		progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
		{

			public void onStopTrackingTouch(SeekBar seekBar)
			{
				// TODO Auto-generated method stub

			}

			public void onStartTrackingTouch(SeekBar seekBar)
			{
				// TODO Auto-generated method stub

			}

			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
			{

				if (fromUser)
					audioPlayer1.seekTo(progress / 100f);
			}
		});
	}

	public void updateSongList()
	{
		// Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		// Uri uri2 = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
		//
		// Log.i("TEST", uri2.toString());
		// Cursor cursor = getContentResolver().query(uri, null, null, null, null);
		//
		// if (cursor == null)
		// {
		// Log.i("FUCK YOU", "HANDLER FAILED");
		// }
		// else if (!cursor.moveToFirst())
		// {
		// Log.i("FUCK YOU", "NO MEDIA");
		// }
		// else
		// {
		// int artistColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE);
		// int artist = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media._ID);
		//
		// do
		// {
		// String album = cursor.getString(cursor.getColumnIndex(AudioColumns.ALBUM));
		// long albumId = cursor.getLong(cursor.getColumnIndex(AudioColumns.ALBUM_ID));
		//
		// String title = cursor.getString(cursor.getColumnIndex(AudioColumns.TITLE));
		// int isAlarm = cursor.getInt(cursor.getColumnIndex(AudioColumns.IS_ALARM));
		// int isMusic = cursor.getInt(cursor.getColumnIndex(AudioColumns.IS_MUSIC));
		// String data = cursor.getString(cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.DATA));
		//
		// Cursor cursor2 = getContentResolver().query(ContentUris.withAppendedId(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, albumId), new String[] { MediaStore.Audio.AlbumColumns.ALBUM_ART }, null, null, null);
		//
		// String albumArtUri = null;
		// Log.i("TEST", "" + cursor2.getCount());
		// if (cursor2.moveToFirst())
		// {
		// albumArtUri = cursor.getString(0);
		// }
		//
		// cursor2.close();
		//
		// Log.i("FUCK YOU ", album + " " + albumArtUri + " " + albumId + " " + title + " " + isAlarm + " " + isMusic + " " + data);
		// }
		// while (cursor.moveToNext());
		// }

		Playlist playlist = new Playlist();

		File home = new File(MEDIA_PATH);
		if (home.listFiles(new Mp3Filter()).length > 0)
		{
			this.songLibrary = new SongLibrary(getContentResolver());
			List<Song> allSongs = songLibrary.getAllSongs();

			for (Song song : allSongs)
			{
				songs.add(song.getFile());
				playlist.addSong(song);
			}

			deck.setPlaylist(playlist);

			final ListView pl1 = (ListView) this.findViewById(R.id.playlist1);
			final ListView pl2 = (ListView) this.findViewById(R.id.playlist2);
			pl1.setAdapter(new TestAdapter(this, R.layout.song_item, allSongs));
			pl2.setAdapter(new TestAdapter(this, R.layout.song_item, allSongs));

			nowPlayingText1 = (TextView) findViewById(R.id.current_position_1);

			pl1.setOnItemClickListener(new OnItemClickListener()
			{

				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
				{
					currentPositionPlayer1 = arg2;
					setSong1();
				}
			});

			pl2.setOnItemClickListener(new OnItemClickListener()
			{
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
				{
					currentPositionPlayer2 = arg2;
					((TextView) findViewById(R.id.now_playing_text_2)).setText(songs.get(arg2));
					audioPlayer2.setAudioFile(MEDIA_PATH + songs.get(arg2));
					// playSong(MEDIA_PATH + songs.get(position));
				}
			});

		}
	}

	private class TestAdapter extends ArrayAdapter<Song>
	{
		private Context context;
		private int textViewResourceId;

		public TestAdapter(Context context, int textViewResourceId, List<Song> objects)
		{
			super(context, textViewResourceId, objects);
			this.context = context;
			this.textViewResourceId = textViewResourceId;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View row = convertView;

			if (row == null)
			{
				LayoutInflater inflater = ((Activity) context).getLayoutInflater();
				row = inflater.inflate(textViewResourceId, parent, false);
			}

			ImageView albumArt = (ImageView) row.findViewById(R.id.album_art);
			TextView songTitle = (TextView) row.findViewById(R.id.song_title);
			TextView songArtist = (TextView) row.findViewById(R.id.song_artist);
			TextView songDuration = (TextView) row.findViewById(R.id.song_duration);

			Song song = songLibrary.getAllSongs().get(position);

			String uriString = song.getAlbumArtUri();

			if (uriString != null)
			{
				Uri albumArtUri = Uri.parse(uriString);
				albumArt.setImageURI(albumArtUri);
			}
			songTitle.setText(song.getTitle());
			songArtist.setText(song.getArtist());
			songDuration.setText(Helper.formatDuration(song.getDuration()));

			return row;
		}
	}

	private void setSong1()
	{
		((TextView) findViewById(R.id.now_playing_text_1)).setText(songs.get(currentPositionPlayer1));
		audioPlayer1.setAudioFile(MEDIA_PATH + songs.get(currentPositionPlayer1));
		final int durationInSeconds = audioPlayer1.getDurationInSeconds();
		final String duration = String.format("%d:%d", durationInSeconds / 60, durationInSeconds % 60);

		testTask = new TimerTask()
		{

			@Override
			public void run()
			{

				int currentPositionInSeconds = audioPlayer1.getCurrentPositionInSeconds();
				int currentMinutes = currentPositionInSeconds / 60;
				int currentSeconds = currentPositionInSeconds % 60;

				nowPlayingText1.setText("" + String.format("%d:%d", currentMinutes, currentSeconds) + "/" + duration);

				progressBar.setProgress((int) (audioPlayer1.getCurrentPositionInPercent() * 100));

				handler.postDelayed(testTask, 1000);
			}
		};

		handler = new Handler();
		handler.removeCallbacks(testTask);
		handler.postDelayed(testTask, 1000);

	}

	// private void playSong(String songPath) {
	// try {
	//
	// MediaController mediaController = (MediaController)
	// findViewById(R.id.mediaController1);
	//
	// mp.reset();
	// mp.setDataSource(songPath);
	// mp.prepare();
	// mp.start();
	//
	// // Setup listener so next song starts automatically
	// mp.setOnCompletionListener(new OnCompletionListener() {
	//
	// public void onCompletion(MediaPlayer arg0) {
	// nextSong();
	// }
	//
	// });
	//
	// } catch (IOException e) {
	// Log.v(getString(R.string.app_name), e.getMessage());
	// }
	// }
	//
	// private void nextSong() {
	// if (++currentPosition >= songs.size()) {
	// // Last song, just reset currentPosition
	// currentPosition = 0;
	// } else {
	// // Play next song
	// playSong(MEDIA_PATH + songs.get(currentPosition));
	// }
	// }
	//
}

class Mp3Filter implements FilenameFilter
{
	public boolean accept(File dir, String name)
	{
		return name.toLowerCase().endsWith(".mp3");
	}
}
