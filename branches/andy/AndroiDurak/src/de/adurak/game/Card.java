package de.adurak.game;

/**
 * A game card.
 * 
 * @author Andreas Krings - <a href="mailto:info@ankri.de">info@ankri.de</a> - <a href="http://www.ankri.de" target="_blank">ankri.de</a>
 * @version $Date: 2009-04-29 01:25:40 +0200 (Wed, 29 Apr 2009) $ - Revision: $Rev: 11 $
 */
public class Card implements Comparable<Card>
{
	/**
	 * The identifier for <b>clubs</b> &clubs;
	 */
	public static final int CLUBS = 0;
	/**
	 * The identifier for <b>diamonds</b> &diams;
	 */
	public static final int DIAMONDS = 1;
	/**
	 * The identifier for <b>hearts</b> &hearts;
	 */
	public static final int HEARTS = 2;
	/**
	 * The identifier for <b>spades</b> &spades;
	 */
	public static final int SPADES = 3;

	private int number;
	private int suit;

	/**
	 * Create a new card with the given parameters
	 * 
	 * @param suit
	 *            Must be one of {@link #CLUBS}, {@link #DIAMONDS}, {@link #HEARTS} or {@link #SPADES}
	 * @param number
	 *            Must be greater than 1 and smaller than 15. The valid numbers are 2-10, the jack is represented by 11, queen by 12, king by 13 and the ace is represented by 14
	 */
	public Card(int suit, int number)
	{
		assert (suit >= 0 && suit < 4);
		assert (number > 1 && number < 15);

		this.suit = suit;
		this.number = number;
	}

	/**
	 * 
	 * @return the number of the card
	 */
	public int getNumber()
	{
		return this.number;
	}

	/**
	 * 
	 * @return the suit of the card
	 */
	public int getSuit()
	{
		return this.suit;
	}

	/**
	 * 
	 * @return the suit and the number of the card: <code>suit * 100 + number</code>
	 */
	public int getSuitAndNumber()
	{
		return this.suit * 100 + this.number;
	}

	/**
	 * 
	 * @return is this card a trump card
	 */
	public boolean isTrump()
	{
		return this.suit == Deck.trumpSuit;
	}

	@Override
	public int compareTo(Card anotherCard)
	{
		int thisValue = (this.isTrump()) ? 900 + this.number : this.getSuitAndNumber();
		int anotherCardValue = (anotherCard.isTrump()) ? 900 + anotherCard.getNumber() : anotherCard.getSuitAndNumber();

		return thisValue - anotherCardValue;

	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Card)
			return this.number == ((Card) obj).getNumber() && this.suit == ((Card) obj).getSuit();
		return super.equals(obj);
	}

	/**
	 * Check if this card is greater than another card.<br/>
	 * This card is greater the other card if:
	 * <ul>
	 * <li>it is a trump card</li>
	 * <li>or if the other card is a trump, too, if this card is the higher trump card</li>
	 * <li>both cards are the same suit, but this card is higher</li>
	 * </ul>
	 * 
	 * @param anotherCard
	 *            another card
	 * @return <code>true</code> if this card is greater than the other card
	 */
	public boolean isGreaterThan(Card anotherCard)
	{
		int thisValue = (this.isTrump()) ? 900 + this.number : this.getSuitAndNumber();
		int anotherCardValue = (anotherCard.isTrump()) ? 900 + anotherCard.getNumber() : anotherCard.getSuitAndNumber();

		if (this.isTrump() || this.getSuit() == anotherCard.getSuit())
			return thisValue > anotherCardValue;
		else
			return false;

	}

	@Override
	public String toString()
	{
		String suitName = "";

		switch (this.suit)
		{
		case CLUBS:
			suitName = "Clubs";
			break;

		case HEARTS:
			suitName = "Hearts";
			break;

		case DIAMONDS:
			suitName = "Diamonds";
			break;

		case SPADES:
			suitName = "Spades";
			break;
		}
		String numberName = "" + this.number;

		switch (this.number)
		{
		case 11:
			numberName = "Jack";
			break;
		case 12:
			numberName = "Queen";
			break;
		case 13:
			numberName = "King";
			break;
		case 14:
			numberName = "Ace";
			break;
		}

		return numberName + " of " + suitName;
	}

}
