package de.djapp.layout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextViewWithBorder extends TextView
{

	private int lineColor = Color.BLACK;

	public TextViewWithBorder(Context context)
	{
		super(context);
	}

	public TextViewWithBorder(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	public TextViewWithBorder(Context context, AttributeSet attrs)
	{
		super(context, attrs);
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
