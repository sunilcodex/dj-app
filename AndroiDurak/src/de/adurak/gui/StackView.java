package de.adurak.gui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.AttributeSet;
import android.view.View;
import de.adurak.R;
import de.adurak.game.Card;
import de.adurak.gui.controller.Controller;
import de.adurak.gui.helper.CardManager;

public class StackView extends View
{

	private Controller controller;

	public StackView(Context context)
	{
		super(context);
	}

	public StackView(Context context, AttributeSet attributeSet)
	{
		super(context, attributeSet);
	}

	public void setController(Controller controller)
	{
		this.controller = controller;
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		if (this.controller != null)
		{
			int paddingLeft = 40;
			int paddingTop = 10;
			int visibleRect = 30;
			int remainingCards = this.controller.getGame().getTable().getDeck().getRemainingCards();

			Card trumpCard = null;
			Drawable trump = null;

			if (remainingCards > 0)
			{
				trumpCard = this.controller.getGame().getTable().getDeck().getLastCard();
				trump = getContext().getResources().getDrawable(CardManager.getInstance().getCard(trumpCard));
			}

			Drawable backOfCard = getContext().getResources().getDrawable(R.drawable.back);
			Drawable emptyStack = getContext().getResources().getDrawable(R.drawable.slot);

			if (remainingCards > 1)
			{
				trump.setBounds(paddingLeft + visibleRect, paddingTop, paddingLeft + visibleRect + CardManager.CARD_WIDTH, paddingTop + CardManager.CARD_HEIGHT);
				trump.draw(canvas);

				backOfCard.setBounds(paddingLeft, paddingTop, paddingLeft + CardManager.CARD_WIDTH, paddingTop + CardManager.CARD_HEIGHT);
				backOfCard.draw(canvas);
			}
			else if (remainingCards == 1)
			{
				trump.setBounds(paddingLeft, paddingTop, paddingLeft + CardManager.CARD_WIDTH, paddingTop + CardManager.CARD_HEIGHT);
				trump.draw(canvas);
			}
			else if (remainingCards == 0)
			{
				emptyStack.setBounds(paddingLeft + visibleRect / 2, paddingTop, paddingLeft + visibleRect / 2 + CardManager.CARD_WIDTH, paddingTop + CardManager.CARD_HEIGHT);
				emptyStack.draw(canvas);

				int trumpSuit = this.controller.getGame().getTable().getTrumpSuit();
				String trumpSuitText = Html.fromHtml(CardManager.getSuitNameAsUnicode(trumpSuit)).toString();
				Paint paint = new Paint();

				if (trumpSuit == 1 || trumpSuit == 2)
					paint.setColor(Color.RED);
				else
					paint.setColor(Color.BLACK);

				paint.setTextSize(75f);
				Rect bounds = new Rect();
				paint.getTextBounds(trumpSuitText, 0, 1, bounds);
				canvas.drawText(trumpSuitText, paddingLeft - bounds.width() / 2 + CardManager.CARD_WIDTH / 2, paddingTop + CardManager.CARD_HEIGHT / 2 + bounds.height() / 2, paint);
			}

			if (remainingCards > 0)
			{
				String remainingCardsText = "" + remainingCards;
				Paint paint = new Paint();
				paint.setColor(Color.WHITE);
				paint.setTextSize(75f);
				paint.setShadowLayer(8f, 0, 0, Color.BLACK);

				Rect bounds = new Rect();
				paint.getTextBounds(remainingCardsText, 0, remainingCardsText.length(), bounds);
				canvas.drawText(remainingCardsText, paddingLeft + CardManager.CARD_WIDTH / 2 - bounds.width() / 2, paddingTop + CardManager.CARD_HEIGHT / 2 + bounds.height() / 2, paint);
			}
		}

		super.onDraw(canvas);
	}
}
