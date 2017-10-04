package com.votingsytem;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;



public class Contact extends Activity {

	// ///Header

	LinearLayout back;
	TextView head;

	// ///

	Spinner spinner_city;
	TextView txt_city, txt_add;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);

		// ///Header

		head = (TextView) findViewById(R.id.head);
		head.setText("Contact Us");
		back = (LinearLayout) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		// ///
		txt_city = (TextView) findViewById(R.id.city_name);
		txt_add = (TextView) findViewById(R.id.city_add);
		spinner_city = (Spinner) findViewById(R.id.spinner_city);
		spinner_city.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
									   int arg2, long arg3) {
				// TODO Auto-generated method stub
				switch (arg2) {

				case 0:
					txt_city.setText("Select");
					break;
				case 1:
					txt_city.setText("Rajkot");
					txt_add.setText("Rajkot-360001");
					break;
				case 2:
					txt_city.setText("Ahmedabad");
					txt_add.setText("Ahmedabad-300000");
					break;
				case 3:
					txt_city.setText("Gandhinagar");
					txt_add.setText("Gandhinagar-333333");
					break;
				case 4:
					txt_city.setText("Baroda");
					txt_add.setText("Baroda-343434");
					break;
				case 5:
					txt_city.setText("Surat");
					txt_add.setText("Surat-383838");
					break;

				default:
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});

	}

}
