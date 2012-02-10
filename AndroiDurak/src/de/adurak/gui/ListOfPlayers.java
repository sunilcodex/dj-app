package de.adurak.gui;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.adurak.R;
import de.adurak.game.AbstractPlayer;
import de.adurak.gui.controller.Controller;

public class ListOfPlayers extends LinearLayout
{

	private Controller controller;

	public ListOfPlayers(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public void setController(Controller controller)
	{
		this.controller = controller;
	}

	public void updateView()
	{
		this.removeAllViews();

		List<AbstractPlayer> listOfPlayers = this.controller.getGame().getTable().getPlayers();

		for (AbstractPlayer player : listOfPlayers)
		{
			boolean isAttacker = this.controller.getGame().getTable().isAttacker(player);
			boolean isDefender = this.controller.getGame().getTable().isDefender(player);

			TextView playerName = new TextView(this.getContext());
			playerName.setText(player.getName());
			playerName.setPadding(5, listOfPlayers.indexOf(player) == 0 ? 5 : 0, 5, 5);

			Drawable attackerIcon = getResources().getDrawable(R.drawable.sword);
			attackerIcon.setBounds(0, 0, 16, 16);
			Drawable defenderIcon = getResources().getDrawable(R.drawable.shield_silver);
			defenderIcon.setBounds(0, 0, 16, 16);

			if (isAttacker)
				playerName.setCompoundDrawables(attackerIcon, null, null, null);
			else if (isDefender)
				playerName.setCompoundDrawables(defenderIcon, null, null, null);

			this.addView(playerName);
		}

	}
}
