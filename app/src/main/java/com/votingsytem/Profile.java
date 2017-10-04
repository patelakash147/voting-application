package com.votingsytem;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.votingsytem.database.DatabaseHelper;
import com.votingsytem.pojo.User_POJO;

import java.io.File;

public class Profile extends Activity {

	// ///Header

	LinearLayout back;
	TextView head;
	ImageView img_propic;
	TextView tv_voter, tv_name, tv_mobile, tv_adhar, tv_gender;

	// ///

	Button change;
	DatabaseHelper databaseHelper;
	User_POJO user = new User_POJO();
	SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		databaseHelper = new DatabaseHelper(getApplicationContext());
		databaseHelper.openDataBase();
		sharedPreferences = getSharedPreferences("VotingSystem", MODE_PRIVATE);

		user = databaseHelper.getProfile(""
				+ sharedPreferences.getLong("user_id", 1));
		// ///Header

		head = (TextView) findViewById(R.id.head);
		head.setText("Profile");
		back = (LinearLayout) findViewById(R.id.back);

		img_propic = (ImageView) findViewById(R.id.img_propic);
		tv_voter = (TextView) findViewById(R.id.tv_voter);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_mobile = (TextView) findViewById(R.id.tv_mobile);
		tv_adhar = (TextView) findViewById(R.id.tv_adhar);
		tv_gender = (TextView) findViewById(R.id.tv_gender);

		tv_voter.setText(user.voterid);
		tv_name.setText(user.first_name + " " + user.last_name);
		tv_mobile.setText(user.mobile);
		tv_adhar.setText(user.adharno);
		tv_gender.setText(user.gender);

		File imgFile = new File(user.profile_pic);

		if (imgFile.exists()) {

			Bitmap myBitmap = BitmapFactory.decodeFile(imgFile
					.getAbsolutePath());

			img_propic.setImageBitmap(myBitmap);

		}

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		// ///

		change = (Button) findViewById(R.id.chng_pwd);
		change.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent chng = new Intent(Profile.this, Changepwd.class);
				startActivity(chng);

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

}
