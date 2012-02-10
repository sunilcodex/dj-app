package de.adurak.gui.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import de.adurak.game.Card;
import de.adurak.game.Durak;
import de.adurak.gui.CardView;
import de.adurak.gui.controller.Controller;

public class PlayerCardAdapter extends BaseAdapter
{
	private List<Card> playerCards;
	private Context context;
	private Durak game;
	private Controller controller;

	public PlayerCardAdapter(Context context, Controller controller)
	{
		this.playerCards = new ArrayList<Card>();
		this.context = context;
		this.controller = controller;
		this.game = controller.getGame();
	}

	public void setCards(List<Card> cards)
	{
		this.playerCards = cards;

		Collections.sort(this.playerCards);
	}

	@Override
	public int getCount()
	{
		return playerCards.size();
	}

	@Override
	public Object getItem(int pos)
	{
		return this.playerCards.get(pos);
	}

	@Override
	public long getItemId(int pos)
	{
		return this.playerCards.get(pos).getSuitAndNumber();
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent)
	{
		Card card = this.playerCards.get(pos);
		CardView cardImage = new CardView(this.context, card);
		cardImage.setLayoutParams(new Gallery.LayoutParams(CardManager.CARD_WIDTH, CardManager.CARD_HEIGHT));
		cardImage.setScaleType(ImageView.ScaleType.FIT_XY);
		cardImage.setAlpha(1f);
		// cardImage.setPadding(10, 0, 10, 0);

		if ((this.controller.playerIsAttacker() != null && this.controller.playerIsAttacker() && !this.game.getTable().canAttackWithThisCard(card)) || this.controller.playerIsAttacker() == null)
		{
			cardImage.setColorFilter(Color.GRAY, Mode.OVERLAY);
			cardImage.setAlpha(0.1f);
		}

		cardImage.setOnTouchListener(new OnTouchListener()
		{

			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				PlayerCardAdapter.this.controller.setSelectedCard((CardView) v);
				return false;
			}
		});

		return cardImage;
	}

}
