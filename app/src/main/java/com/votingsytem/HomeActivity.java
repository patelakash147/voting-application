package com.votingsytem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;



public class HomeActivity extends Activity {

	FrameLayout fl_vote, fl_result, fl_profile, fl_schedule, fl_contact,
			fl_logout;
	Button faq;
	SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		
		sharedPreferences = getSharedPreferences("VotingSystem", MODE_PRIVATE);

		fl_vote = (FrameLayout) findViewById(R.id.flvote);
		fl_result = (FrameLayout) findViewById(R.id.flresult);
		fl_profile = (FrameLayout) findViewById(R.id.profile);
		fl_contact = (FrameLayout) findViewById(R.id.contact);
		fl_logout = (FrameLayout) findViewById(R.id.logout);
		fl_schedule = (FrameLayout) findViewById(R.id.schedule);
		faq = (Button) findViewById(R.id.faq);
		fl_vote.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent vote = new Intent(HomeActivity.this,
						VoteListActivity.class);
				startActivity(vote);
			}
		});

		fl_result.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent result = new Intent(HomeActivity.this, Result.class);
				startActivity(result);
			}
		});

		fl_profile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent profile = new Intent(HomeActivity.this, Profile.class);
				startActivity(profile);

			}
		});

		fl_schedule.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent schedule = new Intent(HomeActivity.this, Schedule.class);
				startActivity(schedule);

			}
		});

		fl_contact.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent cont = new Intent(HomeActivity.this, Contact.class);
				startActivity(cont);

			}
		});

		fl_logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				/*
				 * Intent logout = new Intent(HomeActivity.this,
				 * FirstPageActivity.class); startActivity(logout); finish();
				 */
				new AlertDialog.Builder(HomeActivity.this)
						.setTitle("Logout")
						.setMessage("Are you sure you want to Logout?")
						.setPositiveButton(android.R.string.yes,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										// continue with delete
										dialog.cancel();
										Intent i = new Intent(
												HomeActivity.this,
												FirstPageActivity.class);
										i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
										startActivity(i);
										finish();
										sharedPreferences.edit().clear()
												.commit();
									}
								})
						.setNegativeButton(android.R.string.no,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										// do nothing
										dialog.cancel();
									}
								}).setIcon(android.R.drawable.ic_dialog_alert)
						.show();
			}
		});

		faq.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent faq = new Intent(HomeActivity.this, FAQ.class);
				startActivity(faq);

			}
		});

	}
}
