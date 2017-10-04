package com.votingsytem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.votingsytem.database.DatabaseHelper;

public class LoginActivity extends Activity implements OnClickListener {

	Button lgn, rst;
	EditText uname, pwd;
	Button forgot_pwd;
	DatabaseHelper databaseHelper;
	SharedPreferences sharedPreferences;
	ProgressDialog dialog;
	Button btn_rst;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sharedPreferences = getSharedPreferences("VotingSystem", MODE_PRIVATE);
		if (sharedPreferences.getBoolean("isLogin", false)) {
			Intent log = new Intent(LoginActivity.this, HomeActivity.class);
			startActivity(log);
			finish();
		}
		setContentView(R.layout.activity_login);
		databaseHelper = new DatabaseHelper(getApplicationContext());
		uname = (EditText) findViewById(R.id.edtx_uname);
		pwd = (EditText) findViewById(R.id.edtx_pwd);
		lgn = (Button) findViewById(R.id.btn_lgn);
		forgot_pwd = (Button) findViewById(R.id.frgtpwd);
		btn_rst = (Button) findViewById(R.id.btn_rst);

		btn_rst.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				uname.setText("");
				pwd.setText("");
			}
		});
		lgn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (validate()) {
					dialog = dialog.show(LoginActivity.this, "Loading",
							"Please Wait...!!");
					dialog.setCancelable(false);
					new Handler().postDelayed(new Runnable() {
						public void run() {
							databaseHelper.openDataBase();
							if (databaseHelper.containsRecord(uname.getText()
									.toString(), pwd.getText().toString())) {
								dialog.dismiss();
								Toast.makeText(getApplicationContext(),
										"Successful LoggedIn", 3000).show();
								Intent log = new Intent(LoginActivity.this,
										HomeActivity.class);
								startActivity(log);
								finish();
								databaseHelper.close();
								sharedPreferences.edit()
										.putBoolean("isLogin", true).commit();
							} else {
								Toast.makeText(getApplicationContext(),
										"Invalid VoterID/Password", 3000)
										.show();
							}
						}
					}, 3000);

				}
			}
		});

		forgot_pwd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent log = new Intent(LoginActivity.this, Forgotpwd.class);
				startActivity(log);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

	private boolean validate() {

		if (uname.getText().toString().trim().length() < 1) {
			Toast.makeText(getApplicationContext(), "VoterID Cannot be empty",
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (pwd.getText().toString().trim().length() < 1) {
			Toast.makeText(getApplicationContext(), "Password Cannot be empty",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

}
