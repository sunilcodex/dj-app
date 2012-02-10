package de.adurak.gui.helper;

import de.adurak.R;
import de.adurak.game.Card;

public class CardManager
{
	public static final int CARD_WIDTH = 130;
	public static final int CARD_HEIGHT = 180;

	private static CardManager cardManager = null;

	private CardManager()
	{
	}

	public static CardManager getInstance()
	{
		if (cardManager == null)
			cardManager = new CardManager();

		return cardManager;
	}

	public int getCard(Card card)
	{
		if (card != null)
			switch (card.getSuitAndNumber())
			{
			// Clubs
				case 2:
					return R.drawable.c002;
				case 3:
					return R.drawable.c003;
				case 4:
					return R.drawable.c004;
				case 5:
					return R.drawable.c005;
				case 6:
					return R.drawable.c006;
				case 7:
					return R.drawable.c007;
				case 8:
					return R.drawable.c008;
				case 9:
					return R.drawable.c009;
				case 10:
					return R.drawable.c010;
				case 11:
					return R.drawable.c011;
				case 12:
					return R.drawable.c012;
				case 13:
					return R.drawable.c013;
				case 14:
					return R.drawable.c014;
					// Diamond
				case 102:
					return R.drawable.c102;
				case 103:
					return R.drawable.c103;
				case 104:
					return R.drawable.c104;
				case 105:
					return R.drawable.c105;
				case 106:
					return R.drawable.c106;
				case 107:
					return R.drawable.c107;
				case 108:
					return R.drawable.c108;
				case 109:
					return R.drawable.c109;
				case 110:
					return R.drawable.c110;
				case 111:
					return R.drawable.c111;
				case 112:
					return R.drawable.c112;
				case 113:
					return R.drawable.c113;
				case 114:
					return R.drawable.c114;
					// Hearts
				case 202:
					return R.drawable.c202;
				case 203:
					return R.drawable.c203;
				case 204:
					return R.drawable.c204;
				case 205:
					return R.drawable.c205;
				case 206:
					return R.drawable.c206;
				case 207:
					return R.drawable.c207;
				case 208:
					return R.drawable.c208;
				case 209:
					return R.drawable.c209;
				case 210:
					return R.drawable.c210;
				case 211:
					return R.drawable.c211;
				case 212:
					return R.drawable.c212;
				case 213:
					return R.drawable.c213;
				case 214:
					return R.drawable.c214;
					// Spades
				case 302:
					return R.drawable.c302;
				case 303:
					return R.drawable.c303;
				case 304:
					return R.drawable.c304;
				case 305:
					return R.drawable.c305;
				case 306:
					return R.drawable.c306;
				case 307:
					return R.drawable.c307;
				case 308:
					return R.drawable.c308;
				case 309:
					return R.drawable.c309;
				case 310:
					return R.drawable.c310;
				case 311:
					return R.drawable.c311;
				case 312:
					return R.drawable.c312;
				case 313:
					return R.drawable.c313;
				case 314:
					return R.drawable.c314;
			}

		return R.drawable.back;
	}

	public int getBackOfCard()
	{
		return R.drawable.back;
	}

	public static int getSlot()
	{
		return R.drawable.back;
	}

	public static String getSuitName(int suit)
	{
		assert (suit >= 0 && suit < 4);

		switch (suit)
		{
			case Card.CLUBS:
				return "clubs";
			case Card.DIAMONDS:
				return "diams";
			case Card.HEARTS:
				return "hearts";
			case Card.SPADES:
				return "spades";
		}

		return "";
	}

	public static String getSuitNameAsUnicode(int suit)
	{
		assert (suit >= 0 && suit < 4);

		switch (suit)
		{
			case Card.CLUBS:
				return "&#9827;";
			case Card.DIAMONDS:
				return "&#9830;";
			case Card.HEARTS:
				return "&#9829;";
			case Card.SPADES:
				return "&#9824;";
		}

		return "";
	}
}
