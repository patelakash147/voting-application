package com.votingsytem;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;

import com.votingsytem.database.DatabaseHelper;

public class SplashActivity extends Activity {

	Handler handler = new Handler();
	ProgressBar progressBar;
	public static DatabaseHelper myDbHelper;
	SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		myDbHelper = new DatabaseHelper(this);
		sharedPreferences = getSharedPreferences("VotingSystem", MODE_PRIVATE);

		try {
			myDbHelper.createDataBase();
			// myDbHelper.openDataBase();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		progressBar = (ProgressBar) findViewById(R.id.progree_splash);
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				progressBar.setVisibility(View.GONE);
				if (sharedPreferences.getBoolean("intro_complete", false)) {
					Intent i = new Intent(SplashActivity.this, LoginActivity.class);
					startActivity(i);
					finish();
				} else {
					Intent i = new Intent(SplashActivity.this, FirstPageActivity.class);
					startActivity(i);
					finish();
				}
			}
		}, 3000);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}

}
