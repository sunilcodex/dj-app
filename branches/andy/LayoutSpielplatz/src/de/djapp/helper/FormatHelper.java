package de.djapp.helper;

/**
 * A format helper
 * 
 * @author Andreas Krings
 * 
 */
public class FormatHelper
{
	/**
	 * Format the duration of the song in ##:## - Eg 04:05 for 4 minutes, 5 seconds
	 * 
	 * @param durationInMs
	 *            The duration in milliseconds
	 * @return The formatted String in ##:##
	 */
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
