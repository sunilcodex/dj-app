package de.adurak.game;

/**
 * A human player
 * 
 * @author Andreas Krings - <a href="mailto:info@ankri.de">info@ankri.de</a> - <a href="http://www.ankri.de" target="_blank">ankri.de</a>
 * @version $Date: 2009-04-26 00:25:05 +0200 (Sun, 26 Apr 2009) $ - Revision: $Rev: 7 $
 */
public class Player extends AbstractPlayer
{
	/**
	 * Create a new player with the given parameters
	 * 
	 * @param durak
	 *            The game logic
	 * @param name
	 *            The name of the player
	 */
	public Player(Durak durak, String name)
	{
		super(durak, name);
	}

}
