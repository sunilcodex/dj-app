package com.example.DJApp;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
//import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

public class DJApp extends Activity implements WaveformView.WaveformListener{

	private static final String MEDIA_PATH = new String("/sdcard/Music/");
	private List<String> songs = new ArrayList<String>();

	private int currentPositionPlayer1 = 0;
	private int currentPositionPlayer2 = 0;

	private AudioPlayer audioPlayer1 = new AudioPlayer();
	private AudioPlayer audioPlayer2 = new AudioPlayer();

	private ImageButton play1Button;
	private ImageButton play2Button;
	private ImageButton next1Button;
	private ImageButton next2Button;
	private ImageButton prev1Button;
	private ImageButton prev2Button;
	private SeekBar faderSeekBar;
//	private SeekBar progressBar;
	private TextView nowPlayingText1;
	private Handler handler;
	private TimerTask testTask;
    private long mLoadingLastUpdateTime;
    private boolean mLoadingKeepGoing;
    private CheapSoundFile mSoundFile;
    private WaveformView mWaveformView;
    private WaveformView mWaveformView1;
    private int mWidth;
    private int mMaxPos;
    private int mEndPos;
    private int mOffsetGoal;
    private Handler mHandler;
    private boolean mIsPlaying;
    private boolean mTouchDragging;
    private float mDensity;
    private ProgressDialog mProgressDialog;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);


		mWaveformView = (WaveformView)findViewById(R.id.waveform);
        mWaveformView.setListener(this);
        mWaveformView.setFixedWindow(true);
        
		mWaveformView1 = (WaveformView)findViewById(R.id.waveform1);
        mWaveformView1.setListener(this);
        mWaveformView1.setFixedWindow(false);
        
        audioPlayer1.setVolume(0.5f);
		audioPlayer2.setVolume(0.5f);

		play1Button = ((ImageButton) findViewById(R.id.play1));
		play1Button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (audioPlayer1.getAudioFile() != "") {
					if (audioPlayer1.isPlaying()) {
						audioPlayer1.pause();
						play1Button.setSelected(false);
						//play1Button.setImageDrawable(getResources().getDrawable(R.drawable.playbutton));
					} else {
						mIsPlaying = true;
						audioPlayer1.start();
						play1Button.setSelected(true);
						//play1Button.setImageDrawable(getResources().getDrawable(R.drawable.pausebutton));
					}
				}
			}
		});
		
		play2Button = ((ImageButton) findViewById(R.id.play2));
		play2Button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (audioPlayer2.getAudioFile() != "") {
					if (audioPlayer2.isPlaying()) {
						audioPlayer2.pause();
						play2Button.setImageDrawable(getResources()
								.getDrawable(R.drawable.playbutton));
					} else {
						audioPlayer2.start();
						play2Button.setImageDrawable(getResources()
								.getDrawable(R.drawable.pausebutton));
					}
				}
			}
		});

		next1Button = ((ImageButton) findViewById(R.id.next1));
		next1Button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (audioPlayer1.getAudioFile() != "") {
					if(currentPositionPlayer1+1 < songs.size())
						currentPositionPlayer1++;
					else
						currentPositionPlayer1 = 0;
					setSong1();
				}
			}
		});

		prev1Button = ((ImageButton) findViewById(R.id.prev1));
		prev1Button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (audioPlayer1.getAudioFile() != "") {
					if(currentPositionPlayer1-1 >= 0)
						currentPositionPlayer1--;
					else
						currentPositionPlayer1 = songs.size()-1;
					setSong1();
				}
			}
		});

		faderSeekBar = (SeekBar) findViewById(R.id.fader);
		faderSeekBar.setMax(100);
		faderSeekBar
				.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

					public void onProgressChanged(SeekBar arg0, int arg1,
							boolean arg2) {
						float value = arg1 / 100f;
						audioPlayer1.setVolume(1 - value);
						audioPlayer2.setVolume(value);
					}

					public void onStartTrackingTouch(SeekBar arg0) {
						// TODO Auto-generated method stub

					}

					public void onStopTrackingTouch(SeekBar arg0) {
						// TODO Auto-generated method stub

					}
				});

		faderSeekBar.setProgress(50);
		updateSongList();
		
		mHandler = new Handler();
		
/*		progressBar = (SeekBar) findViewById(R.id.volume1);

		progressBar.setProgress(0);
		progressBar.setMax(100);

		progressBar
				.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub

					}

					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub

					}

					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {

						if (fromUser)
							audioPlayer1.seekTo(progress / 100f);
					}
				});*/
	}

	public void updateSongList() {
		File home = new File(MEDIA_PATH);
		if (home.listFiles(new Mp3Filter()).length > 0) {
			for (File file : home.listFiles(new Mp3Filter())) {
				songs.add(file.getName());
			}

			ArrayAdapter<String> songList = new ArrayAdapter<String>(this,
					R.layout.song_item, songs);

			final ListView pl1 = (ListView) this.findViewById(R.id.playlist1);
			final ListView pl2 = (ListView) this.findViewById(R.id.playlist2);
			pl1.setAdapter(songList);
			pl2.setAdapter(songList);

			nowPlayingText1 = (TextView) findViewById(R.id.current_position_1);

			pl1.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					currentPositionPlayer1 = arg2;
					setSong1();
				}
			});

			pl2.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					currentPositionPlayer2 = arg2;
					((TextView) findViewById(R.id.now_playing_text_2))
							.setText(songs.get(arg2));
					audioPlayer2.setAudioFile(MEDIA_PATH + songs.get(arg2));
					// playSong(MEDIA_PATH + songs.get(position));
				}
			});

		}
	}

	private void setSong1() {
		((TextView) findViewById(R.id.now_playing_text_1)).setText(songs
				.get(currentPositionPlayer1));
		audioPlayer1.setAudioFile(MEDIA_PATH
				+ songs.get(currentPositionPlayer1));
		final int durationInSeconds = audioPlayer1.getDurationInSeconds();
		final String duration = String.format("%d:%d", durationInSeconds / 60,
				durationInSeconds % 60);

        final CheapSoundFile.ProgressListener listener =
                new CheapSoundFile.ProgressListener() {
                    public boolean reportProgress(double fractionComplete) {
                        long now = System.currentTimeMillis();
                        if (now - mLoadingLastUpdateTime > 100) {
                            mLoadingLastUpdateTime = now;
                        }
                        return mLoadingKeepGoing;
                    }
                };

        new Thread() {
            public void run() {
                try {
                	mLoadingKeepGoing = true;
                    mSoundFile = CheapSoundFile.create("/sdcard/Music/" + songs
            				.get(currentPositionPlayer1), listener);

                            
                    if (mSoundFile == null) {
                        mProgressDialog.dismiss();
                        String name = "mFile.getName().toLowerCase()";
                        String[] components = name.split("\\.");
                        String err;
                        if (components.length < 2) {
                            err = getResources().getString(
                                R.string.error);
                        } else {
                            err = getResources().getString(
                                R.string.error) + " " +
                                components[components.length - 1];
                        }
                        final String finalErr = err;
                        
                        return;
                    }
                } catch (final Exception e) {
                    mProgressDialog.dismiss();
                    e.printStackTrace();
                    return;
                }
               // mProgressDialog.dismiss();
                if (mLoadingKeepGoing) {
                    Runnable runnable = new Runnable() {
                            public void run() {
                                finishOpeningSoundFile();
                            }
                        };
                    mHandler.post(runnable);
                } else {
                	int bla=1;
                }
            }
        }.start();
        
		testTask = new TimerTask() {

			@Override
			public void run() {

				int currentPositionInSeconds = audioPlayer1
						.getCurrentPositionInSeconds();
				int currentMinutes = currentPositionInSeconds / 60;
				int currentSeconds = currentPositionInSeconds % 60;

				nowPlayingText1
						.setText(""
								+ String.format("%d:%d", currentMinutes,
										currentSeconds) + "/" + duration);

//				progressBar.setProgress((int) (audioPlayer1.getCurrentPositionInPercent() * 100));
				updateDisplay();
				handler.postDelayed(testTask, 1000);
			}
		};

		handler = new Handler();
		handler.removeCallbacks(testTask);
		handler.postDelayed(testTask, 1000);

	}

    private void finishOpeningSoundFile() {
        mWaveformView.setSoundFile(mSoundFile);
        mWaveformView.recomputeHeights(mDensity);
        mWaveformView1.setSoundFile(mSoundFile);
        mWaveformView1.recomputeHeights(mDensity);

        mMaxPos = mWaveformView.maxPos();

        mTouchDragging = false;

        mOffsetGoal = 0;
        if (mEndPos > mMaxPos)
            mEndPos = mMaxPos;

        updateDisplay();
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
	


    //
    // WaveformListener
    //

    /**
     * Every time we get a message that our waveform drew, see if we need to
     * animate and trigger another redraw.
     */
    public void waveformDraw() {
//        mWidth = mWaveformView.getMeasuredWidth();
//        if (mOffsetGoal != mOffset && !mKeyDown)
//            updateDisplay();
//        else if (mIsPlaying) {
//            updateDisplay();
//        } else if (mFlingVelocity != 0) {
//            updateDisplay();
//        }
    }

    public void waveformTouchStart(float x) {
    	audioPlayer1.seekTo(x/640);
    	updateDisplay();
//        mTouchDragging = true;
//        mTouchStart = x;
//        mTouchInitialOffset = mOffset;
//        mFlingVelocity = 0;
//        mWaveformTouchStartMsec = System.currentTimeMillis();
    }

    public void waveformTouchMove(float x) {
    	audioPlayer1.seekTo(x/640);
    	updateDisplay();
//        mOffset = trap((int)(mTouchInitialOffset + (mTouchStart - x)));
//        updateDisplay();
    }
    
    public void waveformTouchEnd() {
//        mTouchDragging = false;
//        mOffsetGoal = mOffset;
//
//        long elapsedMsec = System.currentTimeMillis() -
//            mWaveformTouchStartMsec;
//        if (elapsedMsec < 300) {
//            if (mIsPlaying) {
//                int seekMsec = mWaveformView.pixelsToMillisecs(
//                    (int)(mTouchStart + mOffset));
//                if (seekMsec >= mPlayStartMsec &&
//                    seekMsec < mPlayEndMsec) {
//                    mPlayer.seekTo(seekMsec - mPlayStartOffset);
//                } else {
//                    handlePause();
//                }
//            } else {
//                onPlay((int)(mTouchStart + mOffset));
//            }
//        }
    }

  
    private void setOffsetGoalNoUpdate(int offset) {
        if (mTouchDragging) {
            return;
        }

        mOffsetGoal = offset;
        if (mOffsetGoal + mWidth / 2 > mMaxPos)
            mOffsetGoal = mMaxPos - mWidth / 2;
        if (mOffsetGoal < 0)
            mOffsetGoal = 0;
    }

    private synchronized void updateDisplay() {
        if (mIsPlaying) {
            int now = audioPlayer1.getCurrentPosition();//mPlayer.getCurrentPosition() + mPlayStartOffset;
            int frames = mWaveformView.millisecsToPixels(now);
            mWaveformView.setPlayback(frames);
            int frames1 = mWaveformView1.millisecsToPixels(now);
            mWaveformView1.setPlayback(frames1);
            setOffsetGoalNoUpdate(frames - mWidth / 2);

            mWaveformView.invalidate();

            mWaveformView1.invalidate();
       }
    }

}

class Mp3Filter implements FilenameFilter {
	public boolean accept(File dir, String name) {
		return (name.endsWith(".mp3"));
	}
}
