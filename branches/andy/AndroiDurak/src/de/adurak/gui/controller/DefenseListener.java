package de.adurak.gui.controller;

import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;
import de.adurak.game.Card;
import de.adurak.game.Durak;
import de.adurak.gui.CardView;

public class DefenseListener implements OnDragListener
{

	private Controller controller;
	private Durak game;

	public DefenseListener(Controller controller)
	{
		this.controller = controller;
		this.game = controller.getGame();
	}

	@Override
	public boolean onDrag(View v, DragEvent event)
	{
		Card cardDefendingWith = (Card) event.getLocalState();
		Card cardToBeDefeated = ((CardView) v).getCard();

		if (this.controller.playerIsAttacker() != null && !this.controller.playerIsAttacker())
		{
			if (event.getAction() == DragEvent.ACTION_DRAG_STARTED)
				return true;
			else if (event.getAction() == DragEvent.ACTION_DRAG_ENTERED && this.game.getTable().canDefendWithCard(cardDefendingWith, cardToBeDefeated))
			{
				((CardView) v).setStatus(1);
				v.invalidate();
				return true;
			}
			else if (event.getAction() == DragEvent.ACTION_DRAG_ENTERED && !this.game.getTable().canDefendWithCard(cardDefendingWith, cardToBeDefeated))
			{
				((CardView) v).setStatus(2);
				v.invalidate();
				return true;
			}
			else if (event.getAction() == DragEvent.ACTION_DRAG_EXITED)
			{
				((CardView) v).setStatus(-1);
				v.invalidate();
				return true;
			}
			else if (event.getAction() == DragEvent.ACTION_DROP && this.game.getTable().canDefendWithCard(cardDefendingWith, cardToBeDefeated))
			{
				((CardView) v).setStatus(-1);
				v.invalidate();

				this.controller.defend(cardToBeDefeated, cardDefendingWith);

				return true;
			}
			else if (event.getAction() == DragEvent.ACTION_DROP && !this.game.getTable().canDefendWithCard(cardDefendingWith, cardToBeDefeated))
			{
				((CardView) v).setStatus(-1);
				v.invalidate();

				return true;
			}

		}
		return false;
	}
}
