package com.votingsytem;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;



public class FAQ extends Activity {

	// ///Header

	LinearLayout back;
	TextView head;

	// ///

	FrameLayout fm1, fm2, fm3, fm4, fm5;
	TextView tx1, tx2, tx3, tx4, tx5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_faq);

		// ///Header

		head = (TextView) findViewById(R.id.head);
		head.setText("FAQ?");
		back = (LinearLayout) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		// ///
		// fm1.setVisibility(View.GONE);
		fm1 = (FrameLayout) findViewById(R.id.frm1);
		fm2 = (FrameLayout) findViewById(R.id.frm2);
		fm3 = (FrameLayout) findViewById(R.id.frm3);
		fm4 = (FrameLayout) findViewById(R.id.frm4);
		fm5 = (FrameLayout) findViewById(R.id.frm5);
		tx1 = (TextView) findViewById(R.id.txt1);
		tx2 = (TextView) findViewById(R.id.txt2);
		tx3 = (TextView) findViewById(R.id.txt3);
		tx4 = (TextView) findViewById(R.id.txt4);
		tx5 = (TextView) findViewById(R.id.txt5);

		tx1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (fm1.getVisibility() == View.GONE)
					fm1.setVisibility(View.VISIBLE);
				else
					fm1.setVisibility(View.GONE);
			}
		});

		tx2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (fm2.getVisibility() == View.GONE)
					fm2.setVisibility(View.VISIBLE);
				else
					fm2.setVisibility(View.GONE);
			}
		});

		tx3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (fm3.getVisibility() == View.GONE)
					fm3.setVisibility(View.VISIBLE);
				else
					fm3.setVisibility(View.GONE);
			}
		});

		tx4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (fm4.getVisibility() == View.GONE)
					fm4.setVisibility(View.VISIBLE);
				else
					fm4.setVisibility(View.GONE);
			}
		});

		tx5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (fm5.getVisibility() == View.GONE)
					fm5.setVisibility(View.VISIBLE);
				else
					fm5.setVisibility(View.GONE);
			}
		});

	}

}
