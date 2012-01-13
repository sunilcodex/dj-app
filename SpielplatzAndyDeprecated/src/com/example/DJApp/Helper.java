package com.example.DJApp;

public class Helper
{
	public static String formatDuration(long durationInMs)
	{
		int minute = (int) (durationInMs / 1000 / 60);
		int seconds = (int) (durationInMs / 1000 % 60);

		String minuteString = "" + minute;
		if (minute < 10)
			minuteString = "0" + minuteString;

		String secondsString = "" + seconds;
		if (seconds < 10)
			secondsString = "0" + secondsString;

		return minuteString + ":" + secondsString;
	}

}
