package de.adurak.gui.controller;

import android.graphics.Color;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;
import de.adurak.R;
import de.adurak.game.Card;
import de.adurak.game.Durak;

public class AttackListener implements OnDragListener
{
	private Controller controller;
	private Durak game;

	public AttackListener(Controller controller)
	{
		this.controller = controller;
		this.game = controller.getGame();
	}

	@Override
	public boolean onDrag(View v, DragEvent event)
	{
		Card card = (Card) event.getLocalState();

		if (this.controller.playerIsAttacker() != null && this.controller.playerIsAttacker())
		{
			if (event.getAction() == DragEvent.ACTION_DRAG_STARTED)
				return true;
			else if (event.getAction() == DragEvent.ACTION_DRAG_ENTERED && this.game.getTable().canAttackWithThisCard(card))
			{
				v.setBackgroundColor(Color.GREEN);
				return true;
			}
			else if (event.getAction() == DragEvent.ACTION_DRAG_ENTERED && !this.game.getTable().canAttackWithThisCard(card))
			{
				v.setBackgroundColor(Color.RED);
				return true;
			}
			else if (event.getAction() == DragEvent.ACTION_DRAG_EXITED)
			{
				v.setBackgroundResource(R.drawable.baize_background);
				return true;
			}
			else if (event.getAction() == DragEvent.ACTION_DROP)
			{
				v.setBackgroundResource(R.drawable.baize_background);
				if (this.game.getTable().canAttackWithThisCard(card))
				{
					this.controller.attackWith(card);
				}

				return true;
			}
		}

		return false;
	}

}
