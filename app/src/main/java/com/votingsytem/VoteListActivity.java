package com.votingsytem;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.votingsytem.database.DatabaseHelper;
import com.votingsytem.pojo.Vote_POJO;

import java.util.ArrayList;

public class VoteListActivity extends Activity {

	// ///HeaderList

	LinearLayout back;
	TextView head;

	// ///

	ImageView imgbtn, img_btnn;
	Adapter_VoteList adapter_votelist;
	DatabaseHelper databaseHelper;
	ListView listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vote_list);
		databaseHelper = new DatabaseHelper(getApplicationContext());
		// ///Header
		listview = (ListView) findViewById(R.id.listview);
		head = (TextView) findViewById(R.id.head);
		head.setText("Candidate List");
		back = (LinearLayout) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	
		databaseHelper.openDataBase();
		ArrayList<Vote_POJO> main_items = new ArrayList<Vote_POJO>();
		main_items = databaseHelper.getPoliticianList();
		databaseHelper.close();
		adapter_votelist = new Adapter_VoteList(main_items, this);
		listview.setAdapter(adapter_votelist);
	}
}
