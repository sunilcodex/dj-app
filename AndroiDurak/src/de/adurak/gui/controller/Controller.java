package de.adurak.gui.controller;

import java.util.ArrayList;
import java.util.List;

import de.adurak.AndroiDurakActivity;
import de.adurak.game.AbstractPlayer;
import de.adurak.game.Card;
import de.adurak.game.Durak;
import de.adurak.game.Player;
import de.adurak.game.Rules;
import de.adurak.game.ai.SimpleAi;
import de.adurak.gui.CardView;

public class Controller
{
	private Durak game;
	private AndroiDurakActivity activity;
	private CardView selectedCard = null;
	private Player player = null;

	public Controller(Durak game, AndroiDurakActivity activity)
	{
		this.game = game;
		this.activity = activity;
	}

	public void newGame()
	{
		List<AbstractPlayer> listOfPlayers = new ArrayList<AbstractPlayer>();
		this.player = new Player(this.game, "Testdurak");
		listOfPlayers.add(this.player);
		listOfPlayers.add(new SimpleAi(this.game, "Computer 1"));
		listOfPlayers.add(new SimpleAi(this.game, "Computer 2"));
		listOfPlayers.add(new SimpleAi(this.game, "Computer 3"));
		listOfPlayers.add(new SimpleAi(this.game, "Computer 4"));

		Rules rules = new Rules(Rules.MIN_AMOUNT_OF_CARDS);
		this.game.newGame(listOfPlayers, rules);

		this.activity.newGame(listOfPlayers);
	}

	public Durak getGame()
	{
		return this.game;
	}

	public void setSelectedCard(CardView card)
	{
		this.selectedCard = card;
	}

	public CardView getSelectedCard()
	{
		return this.selectedCard;
	}

	public Boolean playerIsAttacker()
	{
		if (this.game.getTable().getAttackers().contains(this.player))
			return true;
		else if (this.game.getTable().getDefender().equals(this.player))
			return false;
		return null;
	}

	public void attackWith(Card card)
	{
		if (this.player != null)
		{
			this.player.attackWith(card);
			this.activity.updateView();
		}
	}

	public Player getPlayer()
	{
		if (this.player == null)
			for (AbstractPlayer absPlayer : this.game.getTable().getPlayers())
				if (absPlayer instanceof Player)
					this.player = (Player) absPlayer;

		return this.player;
	}

	public void defend(Card cardToBeDefeated, Card cardDefendingWith)
	{
		if (this.game.getTable().canDefendWithCard(cardDefendingWith, cardToBeDefeated))
		{
			this.game.getTable().defend(cardToBeDefeated, cardDefendingWith);
			this.activity.updateView();
		}
	}
}
