package edu.virginia.cs2110.rlc4sv.thebasics.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import edu.virginia.cs2110.rlc4sv.thebasics.R;

/**
 * @author
 * Team 103-04
 * arb4jr, jm2af, rlc4sv, sds7yd, zaf2xk
 */

public class SplashScreen extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        
        Thread logoTimer = new Thread(){
        	public void run() {
        		try{
        			sleep(5000);
        			Intent menuIntent = new Intent("edu.virginia.cs2110.rlc4sv.thebasics.MENU");
        			startActivity(menuIntent);
        		} catch (InterruptedException e) {
					e.printStackTrace();
				}
        		
        		finally {
        			finish();
        		}
        	}
        };
        logoTimer.start();
    }

	protected void onPause() {
		super.onPause();
	}

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
