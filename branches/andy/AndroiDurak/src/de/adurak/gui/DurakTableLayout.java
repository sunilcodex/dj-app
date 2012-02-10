package de.adurak.gui;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.adurak.game.Card;
import de.adurak.game.Durak;
import de.adurak.game.Player;
import de.adurak.gui.controller.AttackListener;
import de.adurak.gui.controller.Controller;
import de.adurak.gui.controller.DefenseListener;
import de.adurak.gui.helper.CardManager;

public class DurakTableLayout extends LinearLayout
{

	private Durak game;
	private Player player;
	private DefenseListener defenseListener;

	public DurakTableLayout(Context context)
	{
		super(context);
	}

	public DurakTableLayout(Context context, AttributeSet attributes)
	{
		super(context, attributes);
	}

	public void setController(Controller controller)
	{
		this.game = controller.getGame();
		this.player = controller.getPlayer();

		this.setOnDragListener(new AttackListener(controller));
		this.defenseListener = new DefenseListener(controller);

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		this.removeAllViews();
		if (this.game != null)
		{
			int paddingTop = l + 15;
			int paddingLeft = t + 15;

			TextView attackerOneLabel = new TextView(getContext());
			attackerOneLabel.setText("Cards from: " + this.game.getTable().getAttackers().get(0).getName());
			List<Card> cardsOfAttackerOne = this.game.getTable().getCardsOfAttackerOneOnTable();
			List<Card> cardsOfAttackerTwo = this.game.getTable().getCardsOfAttackerTwoOnTable();
			Map<Card, Card> defeatedCards = this.game.getTable().getDefeatedCards();

			this.addView(attackerOneLabel);
			attackerOneLabel.layout(paddingLeft, paddingTop, 650, 35);
			attackerOneLabel.invalidate();
			int spacing = 25;
			int cardWidth = CardManager.CARD_WIDTH;
			int cardHeight = CardManager.CARD_HEIGHT;
			int overlaySpacing = 20;

			paddingTop += 30;

			for (int i = 0; i < cardsOfAttackerOne.size(); i++)
			{
				Card attackingCard = cardsOfAttackerOne.get(i);
				View child = new CardView(getContext(), attackingCard);
				this.addView(child);

				child.layout((i == 0 ? paddingLeft : spacing * (i + 1)) + (i * cardWidth), paddingTop, (i == 0 ? paddingLeft : spacing * (i + 1)) + (i * cardWidth) + cardWidth, paddingTop + cardHeight);
				child.invalidate();

				if (defeatedCards.containsKey(attackingCard))
				{
					View defeatingCard = new CardView(getContext(), defeatedCards.get(attackingCard));
					this.addView(defeatingCard);
					defeatingCard.layout((i == 0 ? paddingLeft : spacing * (i + 1)) + (i * cardWidth) + overlaySpacing, paddingTop + overlaySpacing, (i == 0 ? paddingLeft : spacing * (i + 1)) + (i * cardWidth) + cardWidth + overlaySpacing, paddingTop + cardHeight + overlaySpacing);
					defeatingCard.invalidate();
				}
				else if (this.game.getTable().getDefender().equals(this.player))
					child.setOnDragListener(this.defenseListener);
			}

			paddingTop += 220;

			TextView attackerTwoLabel = new TextView(getContext());
			attackerTwoLabel.setText("Cards from: " + this.game.getTable().getAttackers().get(1).getName());
			this.addView(attackerTwoLabel);
			attackerTwoLabel.layout(paddingLeft, paddingTop, 650, paddingTop + 35);
			attackerTwoLabel.invalidate();

			paddingTop += 30;

			for (int i = 0; i < cardsOfAttackerTwo.size(); i++)
			{
				Card attackingCard = cardsOfAttackerTwo.get(i);

				View child = new CardView(getContext(), attackingCard);
				this.addView(child);
				child.layout((i == 0 ? paddingLeft : spacing * (i + 1)) + (i * cardWidth), paddingTop, (i == 0 ? paddingLeft : spacing * (i + 1)) + (i * cardWidth) + cardWidth, paddingTop + cardHeight);
				child.invalidate();

				if (defeatedCards.containsKey(attackingCard))
				{
					View defeatingCard = new CardView(getContext(), defeatedCards.get(attackingCard));
					this.addView(defeatingCard);
					defeatingCard.layout((i == 0 ? paddingLeft : spacing * (i + 1)) + (i * cardWidth) + overlaySpacing, paddingTop + overlaySpacing, (i == 0 ? paddingLeft : spacing * (i + 1)) + (i * cardWidth) + cardWidth + overlaySpacing, paddingTop + cardHeight + overlaySpacing);
					defeatingCard.invalidate();
				}
				else if (this.game.getTable().getDefender().equals(this.player))
					child.setOnDragListener(this.defenseListener);

			}

		}
	}
}
