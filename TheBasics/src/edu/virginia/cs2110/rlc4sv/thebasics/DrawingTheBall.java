package edu.virginia.cs2110.rlc4sv.thebasics;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;


public class DrawingTheBall extends View {

	public DrawingTheBall(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		Rect ourRect = new Rect();
		ourRect.set(0, 0, canvas.getWidth(), canvas.getHeight()/2);
	}

}