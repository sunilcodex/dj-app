package de.adurak.game.ai;

import de.adurak.game.Card;
import de.adurak.game.Durak;

public class NormalAi extends AbstractAi
{

	public NormalAi(Durak durak, String name)
	{
		super(durak, name);
	}

	@Override
	public Card getNextAttackCard()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DefendCard getNextDefendCard()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean wantsToPlayAnotherCard()
	{
		// TODO Auto-generated method stub
		return false;
	}

}
