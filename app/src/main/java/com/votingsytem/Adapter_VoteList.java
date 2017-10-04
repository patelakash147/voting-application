package com.votingsytem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.votingsytem.pojo.Vote_POJO;

import java.util.ArrayList;

public class Adapter_VoteList extends BaseAdapter {

	ArrayList<Vote_POJO> main_item_list;
	Context ctxt;
	Activity activity;
	int[] profile = { R.drawable.suresh, R.drawable.rahul, R.drawable.ketan,
			R.drawable.rahul, R.drawable.vishnu };
	int[] party = { R.drawable.bjp, R.drawable.congress, R.drawable.app,
			R.drawable.bsp, R.drawable.ncp };
	SharedPreferences sharedPreferences;

	public Adapter_VoteList(ArrayList<Vote_POJO> main_items, Activity activity) {
		this.main_item_list = main_items;
		this.activity = activity;
		this.ctxt = activity.getApplicationContext();
		this.sharedPreferences = activity.getSharedPreferences("VotingSystem",
				activity.MODE_PRIVATE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return main_item_list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View vi = arg1;
		LayoutInflater inflater = (LayoutInflater) ctxt
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		vi = inflater.inflate(R.layout.custom_single_row_vote, arg2, false);

		TextView partyname = (TextView) vi.findViewById(R.id.textView1);
		TextView holder_name = (TextView) vi.findViewById(R.id.txt_party_name);
		final ImageView img_btn = (ImageView) vi.findViewById(R.id.img_btn);
		final ImageView img_btnn = (ImageView) vi.findViewById(R.id.img_btnn);
		ImageView img_pro = (ImageView) vi.findViewById(R.id.img_pro);
		ImageView img_logo = (ImageView) vi.findViewById(R.id.img_logo);

		img_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				img_btn.setVisibility(View.GONE);
				img_btnn.setVisibility(View.VISIBLE);

				if (sharedPreferences.getBoolean("vote_complete", false)) {
					Toast.makeText(ctxt, "Vote is already Submitted..!!", 3000)
							.show();
				} else {
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							img_btn.setVisibility(View.VISIBLE);
							img_btnn.setVisibility(View.GONE);
							Intent next = new Intent(activity, Vote_otp.class);
							next.putExtra("candidate_id", ""
									+ main_item_list.get(position)
											.getCandidate_id());
							activity.startActivity(next);
							activity.finish();
						}
					}, 200);
				}
			}
		});

		img_pro.setImageResource(profile[position]);
		img_logo.setImageResource(party[position]);

		partyname.setText(main_item_list.get(position).getHolder_name());
		holder_name.setText(main_item_list.get(position).getParty_name());

		return vi;
	}
}
