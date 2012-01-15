package de.djapp.layout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.LinearLayout;

public class LinearLayoutWithBorder extends LinearLayout
{

	private int lineColor = Color.BLACK;

	public LinearLayoutWithBorder(Context context)
	{
		super(context);
	}

	public void setLineColor(String color)
	{
		this.lineColor = Color.parseColor(color);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		Rect rect = new Rect();
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(this.lineColor);
		paint.setStrokeWidth(1);
		getLocalVisibleRect(rect);
		// rect.set(0, 0, rect.right - 1, rect.bottom - 1);
		// canvas.drawRect(rect, paint);
		canvas.drawLine(0, rect.bottom - 1, rect.right, rect.bottom - 1, paint);

	}
}
