package com.votingsytem;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;



public class Forgotpwd extends Activity {

/////Header
	
	LinearLayout back;
	TextView head;
	
	/////
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgotpwd);
		
	/////Header
		
				head=(TextView)findViewById(R.id.head);
				head.setText("Forgot Password");
				back=(LinearLayout)findViewById(R.id.back);
				back.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
							finish();
					}
				});
				
				/////
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.forgot__password, menu);
		return true;
	}

}
