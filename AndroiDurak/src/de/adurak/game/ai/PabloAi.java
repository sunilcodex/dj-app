package de.adurak.game.ai;


import java.util.Random;

import de.adurak.game.Card;
import de.adurak.game.Durak;

public class PabloAi extends AbstractAi
{

	public PabloAi(Durak durak, String name)
	{
		super(durak, name);
	}

	@Override
	public Card getNextAttackCard()
	{
		// play a random card
		return this.hand.get(new Random().nextInt(this.hand.size()));
	}

	@Override
	public DefendCard getNextDefendCard()
	{
		// never defend always take
		return null;
	}

	@Override
	public boolean wantsToPlayAnotherCard()
	{
		if (this.isAttacker)
			return true;
		return false;
	}

}
