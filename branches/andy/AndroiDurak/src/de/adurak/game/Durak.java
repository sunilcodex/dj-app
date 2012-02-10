package de.adurak.game;

import java.util.List;

/**
 * create a new game of durak with a set of rules and a list of players
 * 
 * @author Andreas Krings - <a href="mailto:info@ankri.de">info@ankri.de</a> - <a href="http://www.ankri.de" target="_blank">ankri.de</a>
 * @version $Date: 2009-04-26 03:01:20 +0200 (Sun, 26 Apr 2009) $ - Revision: $Rev: 8 $
 */
public class Durak
{
	private Rules rules;
	private Table table;

	/**
	 * Start a new game with new players and new rules
	 * 
	 * @param players
	 *            The players with minimum size of 2 players.
	 * @param rules
	 *            The rules. Not <code>null</code>
	 */
	public void newGame(List<AbstractPlayer> players, Rules rules)
	{
		assert (players != null && players.size() > 1);
		assert (rules != null);

		this.rules = rules;
		this.table = new Table(players, this);
	}

	/**
	 * 
	 * @return the current rules
	 */
	public Rules getRules()
	{
		return this.rules;
	}

	/**
	 * 
	 * @return the current table
	 */
	public Table getTable()
	{
		return this.table;
	}

}
