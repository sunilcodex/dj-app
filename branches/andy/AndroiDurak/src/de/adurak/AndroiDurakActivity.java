package de.adurak;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnTouchListener;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.Gallery;
import de.adurak.game.AbstractPlayer;
import de.adurak.game.Card;
import de.adurak.game.Durak;
import de.adurak.game.ai.AbstractAi;
import de.adurak.game.ai.DefendCard;
import de.adurak.gui.CardView;
import de.adurak.gui.DurakTableLayout;
import de.adurak.gui.ListOfPlayers;
import de.adurak.gui.StackView;
import de.adurak.gui.controller.Controller;
import de.adurak.gui.helper.PlayerCardAdapter;

public class AndroiDurakActivity extends Activity
{

	private Gallery playerCards;
	private DurakTableLayout table;
	private Durak game;
	private PlayerCardAdapter playerCardAdapter;
	private Controller controller;
	private StackView stack;
	private ListOfPlayers listOfPlayers;
	private Button btnNextTurn;

	public void newGame(List<AbstractPlayer> players)
	{
		this.listOfPlayers.updateView();
		this.updateView();
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		this.game = new Durak();
		this.controller = new Controller(this.game, this);

		this.table = (DurakTableLayout) this.findViewById(R.id.table);
		this.playerCards = (Gallery) this.findViewById(R.id.player_cards);
		this.stack = (StackView) this.findViewById(R.id.staple);
		this.listOfPlayers = (ListOfPlayers) this.findViewById(R.id.player_list);
		this.btnNextTurn = (Button) this.findViewById(R.id.btn_next_turn);

		this.playerCardAdapter = new PlayerCardAdapter(this, this.controller);

		final GestureListener gestureListener = new GestureListener();
		final GestureDetector gestureDetector = new GestureDetector(gestureListener);
		this.playerCards.setOnTouchListener(new OnTouchListener()
		{

			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				gestureListener.setSelectedCard(AndroiDurakActivity.this.controller.getSelectedCard());
				return gestureDetector.onTouchEvent(event);
			}
		});

		this.playerCards.setClipToPadding(false);

		this.listOfPlayers.setController(this.controller);
		this.controller.newGame();
		this.table.setController(this.controller);
		this.stack.setController(this.controller);
	}

	private void updateTable()
	{
		this.table.addView(new ViewStub(this));
	}

	private void updateHand()
	{
		if (this.controller.getPlayer() != null)
		{
			this.playerCardAdapter.setCards(this.controller.getPlayer().getHand());
			this.playerCards.setAdapter(this.playerCardAdapter);
		}
	}

	public void updateView()
	{
		this.updateHand();
		this.updateTable();
	}

	private Boolean playerIsAttacker()
	{
		if (this.game.getTable().getAttackers().contains(this.controller.getPlayer()))
			return true;
		else if (this.game.getTable().getDefender().equals(this.controller.getPlayer()))
			return false;
		else
			return null;
	}

	private ArrayList<AbstractAi> getComputers()
	{
		ArrayList<AbstractAi> computers = new ArrayList<AbstractAi>();

		for (AbstractPlayer player : this.game.getTable().getAttackers())
			if (player instanceof AbstractAi)
				computers.add((AbstractAi) player);

		if (this.game.getTable().getDefender() instanceof AbstractAi)
			computers.add((AbstractAi) this.game.getTable().getDefender());

		return computers;
	}

	private boolean oneOfTheComputersWantsToMove()
	{
		boolean oneOfComputersWantsToMove = false;

		for (AbstractAi ai : getComputers())
			oneOfComputersWantsToMove |= ai.wantsToPlayAnotherCard();

		return oneOfComputersWantsToMove;
	}

	protected void attackWithCard(Card card)
	{
		if (this.game.getTable().canAttackWithThisCard(card))
		{
			// this.table.addView(new CardView(AndroiDurakActivity.this, card));
			this.playerCardAdapter.setCards(this.controller.getPlayer().getHand());
			this.playerCards.setAdapter(this.playerCardAdapter);
			this.table.addView(new CardView(this, card));
		}
	}

	protected void defendCard(Card cardToBeDefeated, Card cardDefendingWith)
	{
		if (this.game.getTable().canDefendWithCard(cardDefendingWith, cardToBeDefeated))
		{
			this.playerCardAdapter.setCards(this.controller.getPlayer().getHand());
			this.playerCards.setAdapter(this.playerCardAdapter);
			this.table.addView(new CardView(this, cardDefendingWith));
		}

	}

	public void nextTurn(View v)
	{
		for (AbstractPlayer attacker : this.game.getTable().getAttackers())
		{
			if (attacker instanceof AbstractAi)
			{
				AbstractAi computer = (AbstractAi) attacker;
				while (computer.wantsToPlayAnotherCard())
				{
					Card attackCard = computer.getNextAttackCard();
					if (attackCard != null)
					{
						computer.attackWith(attackCard);
						this.attackWithCard(attackCard);
					}
					else
						System.err.println("problem with attack method in ai"); // TODO proper error message
				}

			}
		}

		if (this.game.getTable().getDefender() instanceof AbstractAi)
		{
			AbstractAi computer = (AbstractAi) this.game.getTable().getDefender();

			while (computer.wantsToPlayAnotherCard())
			{
				DefendCard defense = computer.getNextDefendCard();
				if (defense != null)
				{
					this.game.getTable().defend(defense.getCardToBeDefeated(), defense.getCardDefendingWith());
					this.defendCard(defense.getCardToBeDefeated(), defense.getCardDefendingWith());

				}
				else
					System.err.println("problem with defense method in ai"); // TODO proper error
			}
		}
	}

	public void endTurn(View v)
	{
		if (this.oneOfTheComputersWantsToMove())
		{
			this.nextTurn(v);
		}
		else
		{
			this.game.getTable().endTurn();
			this.listOfPlayers.updateView();
			this.stack.invalidate();
			this.updateView();
		}
	}

	private class GestureListener extends SimpleOnGestureListener
	{
		private View selectedCard;

		public void setSelectedCard(View selectedCard)
		{
			this.selectedCard = selectedCard;
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
		{
			if (AndroiDurakActivity.this.playerIsAttacker() != null && Math.abs(distanceX) < 3 && this.selectedCard != null && Math.abs(this.selectedCard.getX() + 130 - e1.getRawX()) < 130)
			{

				DragShadowBuilder cardShadow = new DragShadowBuilder(this.selectedCard);
				this.selectedCard.startDrag(null, cardShadow, ((CardView) this.selectedCard).getCard(), 0);

				return true;
			}

			return super.onScroll(e1, e2, distanceX, distanceY);
		}
	}
}