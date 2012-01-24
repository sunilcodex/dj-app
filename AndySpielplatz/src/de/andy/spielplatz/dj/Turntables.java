package de.andy.spielplatz.dj;

public class Turntables
{
	private Deck deckA;
	private Deck deckB;
	private static Turntables instance;

	private Turntables()
	{
		this.deckA = new Deck();
		this.deckB = new Deck();
	}

	public synchronized static Turntables getInstance()
	{
		if (instance == null)
			instance = new Turntables();

		return instance;
	}

	public Deck getDeckA()
	{
		return this.deckA;
	}

	public Deck getDeckB()
	{
		return this.deckB;
	}
}
