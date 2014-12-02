package edu.virginia.cs2110.rlc4sv.thebasics.screens;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import edu.virginia.cs2110.rlc4sv.thebasics.R;
import edu.virginia.cs2110.rlc4sv.thebasics.objects.Player;
import edu.virginia.cs2110.rlc4sv.thebasics.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */

@SuppressLint("ClickableViewAccessibility")
public class MainGame extends Activity implements OnTouchListener {

	private OurView ov;
	private Player player;
	private int x, y;
	private MediaPlayer logoMusic;
	
	public void setPlayer(Player player){
		this.player = player;
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ov = new OurView(this);
		ov.setOnTouchListener(this);

		logoMusic = MediaPlayer.create(this, R.raw.dungeon_tremors);
		logoMusic.start();
		logoMusic.setLooping(true);

		x = y = 0;
		setContentView(ov);
	}

	protected void onResume() {
		super.onResume();
		ov.resume();
		try{
			logoMusic.start();
		} catch (IllegalStateException e){
			logoMusic = MediaPlayer.create(this, R.raw.dungeon_tremors);
			logoMusic.start();
			logoMusic.setLooping(true);
		}
	}

	protected void onPause() {
		super.onPause();
		ov.pause();
		logoMusic.release();
	}
	
	

	public boolean onTouch(View s, MotionEvent me) {
		OurView v = (OurView) s;
		if(player==null) return false; //if user taps the screen when level is initializing

		Rect left = new Rect(0, v.getHeight()- v.dh*2, v.dw, v.getHeight()-v.dh);
		Rect top = new Rect(v.dw, v.getHeight()- v.dh*3, v.dw*2, v.getHeight()-v.dh*2);
		Rect right = new Rect(v.dw*2, v.getHeight()- v.dh*2, v.dw*3, v.getHeight()-v.dh);
		Rect bottom =new Rect(v.dw, v.getHeight()- v.dh, v.dw*2, v.getHeight());
		Rect shoot1 =new Rect(v.dw*4, v.getHeight()- v.dh*2, v.dw*5, v.getHeight()-v.dh);
		Rect shoot2 =new Rect(v.dw*6, v.getHeight()- v.dh*2, v.dw*7, v.getHeight()-v.dh);
		Rect pause = new Rect(v.dw*8, v.getHeight()- v.dh*2, v.dw*9, v.getHeight()-v.dh);

		if (player.isDead) {
			ov.playSound(R.raw.game_over);
			Intent menuIntent = new Intent("edu.virginia.cs2110.rlc4sv.thebasics.GAMEOVER");
			menuIntent.putExtra("EXTRA_GHOSTS_KILLED" , player.ghostsKilled + "");
			menuIntent.putExtra("EXTRA_COINS_COLLECTED", player.score + "");
			startActivity(menuIntent);
		}

			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			switch(me.getAction()) {
			case MotionEvent.ACTION_DOWN:
				x = (int) me.getX();
				y = (int) me.getY();

				if(player== null)
					return false;
				if(!player.locked){
					if(left.contains(x, y)) {
						player.setDirection("left");
						player.setMove(true);
					} else if(top.contains(x, y)) {
						player.setDirection("up");
						player.setMove(true);
					} else if(right.contains(x, y)) {
						player.setDirection("right");
						player.setMove(true);
					} else if(bottom.contains(x, y)) {
						player.setDirection("down");
						player.setMove(true);
					} else if (player.getBounds().contains(x, y)){
						player.interact();
					} else if(shoot1.contains(x, y)) {
						Bitmap fireballSprites = BitmapFactory.decodeResource(getResources(), R.drawable.explode);
						ov.getLevel().spawnFireball(fireballSprites);
						ov.playSound(R.raw.fire);
					} else if(shoot2.contains(x, y)) {
						Bitmap iceboltSprites = BitmapFactory.decodeResource(getResources(), R.drawable.icebolt);
						ov.getLevel().spawnIcebolt(iceboltSprites);
						ov.playSound(R.raw.ice);
					} else if(pause.contains(x, y)) {
						ov.setIsPaused(!ov.getIsPaused());
					}
				}
				break;
			case MotionEvent.ACTION_MOVE:
				break; 
			case MotionEvent.ACTION_UP:
				player.setMove(false);
				break;
			}

			return true;
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater awesome = getMenuInflater();
		awesome.inflate(R.menu.main_menu, menu);
		return true;
	}

	public MediaPlayer getMusic() {
		return logoMusic;
	}
}
