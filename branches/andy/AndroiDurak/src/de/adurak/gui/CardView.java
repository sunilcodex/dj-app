package de.adurak.gui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.widget.ImageView;
import de.adurak.game.Card;
import de.adurak.gui.helper.CardManager;

public class CardView extends ImageView
{

	private Card card;

	private int status = -1;

	public CardView(Context context, Card card)
	{
		super(context);

		this.card = card;
		this.setImageResource(CardManager.getInstance().getCard(card));
	}

	public Card getCard()
	{
		return this.card;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);

		if (status == 1)
		{
			canvas.drawColor(Color.GREEN, Mode.MULTIPLY);
		}
		else if (status == 2)
		{
			canvas.drawColor(Color.RED, Mode.MULTIPLY);
		}
	}
}
