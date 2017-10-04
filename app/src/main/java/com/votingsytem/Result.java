package com.votingsytem;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;


import com.votingsytem.pojo.Result_POJO;

import java.util.ArrayList;

public class Result extends Activity {

	// ///Header
	LinearLayout back;
	TextView head;

	// ///

	Spinner spinner_year;
	TextView txt_year;
	ListView listview;
	Adapter_Result adapter_Result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		// ///Header
		listview = (ListView) findViewById(R.id.listview);
		head = (TextView) findViewById(R.id.head);
		head.setText("Result");
		back = (LinearLayout) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		// ///

		spinner_year = (Spinner) findViewById(R.id.spinner_year);
		txt_year = (TextView) findViewById(R.id.txt_year);

		spinner_year.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
									   int arg2, long arg3) {
				// TODO Auto-generated method stub
				switch (arg2) {
				case 0:
					txt_year.setText("2014");
					break;
				case 1:
					txt_year.setText("2015");
					break;
				case 2:
					txt_year.setText("2016");
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

		ArrayList<Result_POJO> arrayList = new ArrayList<>();
		Result_POJO result_POJO;

		result_POJO = new Result_POJO();
		result_POJO.setHolder_name("Vishnu Patel");
		result_POJO.setParty_name("BJP");
		result_POJO.setResult(44);
		arrayList.add(result_POJO);

		result_POJO = new Result_POJO();
		result_POJO.setHolder_name("Rahul Gandhi");
		result_POJO.setParty_name("Congress");
		result_POJO.setResult(22);
		arrayList.add(result_POJO);

		result_POJO = new Result_POJO();
		result_POJO.setHolder_name("Vipul Vaghela");
		result_POJO.setParty_name("AAp");
		result_POJO.setResult(17);
		arrayList.add(result_POJO);

		result_POJO = new Result_POJO();
		result_POJO.setHolder_name("Suresh Verma");
		result_POJO.setParty_name("BSP");
		result_POJO.setResult(11);
		arrayList.add(result_POJO);

		result_POJO = new Result_POJO();
		result_POJO.setHolder_name("Ketan Parmar");
		result_POJO.setParty_name("NCP");
		result_POJO.setResult(9);
		arrayList.add(result_POJO);

		adapter_Result = new Adapter_Result(arrayList, Result.this);
		listview.setAdapter(adapter_Result);

	}

}
