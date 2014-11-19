package edu.virginia.cs2110.rlc4sv.thebasics.screens;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import edu.virginia.cs2110.rc4sv.thebasics.objects.Entity;
import edu.virginia.cs2110.rc4sv.thebasics.objects.Level;
import edu.virginia.cs2110.rc4sv.thebasics.objects.Player;
import edu.virginia.cs2110.rc4sv.thebasics.objects.Room;
import edu.virginia.cs2110.rc4sv.thebasics.objects.Sprite;
import edu.virginia.cs2110.rc4sv.thebasics.objects.Tile;
import edu.virginia.cs2110.rlc4sv.thebasics.R;

@SuppressLint({ "WrongCall", "DrawAllocation", "ClickableViewAccessibility" })
public class OurView extends SurfaceView implements Runnable{
	
	private Thread t = null;
	private SurfaceHolder holder;
	private boolean isItOK = false;
	private Bitmap playerSprites, ghostSprites, coinSprites, weaponSprites, directions;
	private Level myLevel;
	private Player player;
	private int dw, dh;
	public int offsetX, offsetY; //visual offset of level
	
	public OurView(Context context) {
		super(context);
		holder = getHolder();
		myLevel = new Level(4, 12); //debug level
		
		playerSprites = BitmapFactory.decodeResource(getResources(), R.drawable.spritesheet);
		ghostSprites = BitmapFactory.decodeResource(getResources(), R.drawable.gspritesheet);
		coinSprites = BitmapFactory.decodeResource(getResources(), R.drawable.coin_gold);
		weaponSprites = BitmapFactory.decodeResource(getResources(), R.drawable.weaponsprite);
		
		directions = BitmapFactory.decodeResource(getResources(), R.drawable.directions);
		dw = directions.getWidth();
		dh = directions.getHeight();

		//create level
		myLevel.addRoom(new Room(this, player, myLevel, 0, 0)); //debug room
		player = myLevel.spawnPlayer(this, playerSprites);
		myLevel.spawnGhosts(this, ghostSprites);
		myLevel.spawnCoins(this, coinSprites);
		myLevel.spawnWeapons(this, weaponSprites);
		
		for(Entity s : myLevel.getWorld())
			if (s instanceof Sprite)
				((Sprite) s).setWorld(myLevel.getWorld());
	}


	public void run() {
		while(isItOK == true) {
			if(!holder.getSurface().isValid()) {
				continue;
			}

			Canvas c = holder.lockCanvas();
			onDraw(c);
			holder.unlockCanvasAndPost(c);
		}
	}
	
	public Level getLevel(){
		return myLevel;
	}

	//order of draw matters
	protected void onDraw(Canvas canvas) {
		///bg
		canvas.drawARGB(255, 150, 150, 10);
		
		//level
		myLevel.onDraw(canvas);
		
		//gui
		Rect src = new Rect(0, 0, dw, dh);
		Rect dst = new Rect(0, getHeight()- dh, dw, getHeight());
		canvas.drawBitmap(directions, src, dst, null);
		//player health
		//player score
	}

	public void pause () {
		isItOK = false;
		while(true){
			try{
				t.join();
			} catch ( InterruptedException e)  {
				e.printStackTrace();;
			}
			break;
		}
		t = null;
	}
	
	public void resume(){
		isItOK = true;
		t = new Thread(this);
		t.start();
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Level getMyLevel() {
		return this.myLevel;
	}
}
