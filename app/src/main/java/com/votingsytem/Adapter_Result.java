package com.votingsytem;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.votingsytem.R;
import com.votingsytem.pojo.Result_POJO;

import java.util.ArrayList;

public class Adapter_Result extends BaseAdapter {

	ArrayList<Result_POJO> main_item_list;
	Context ctxt;
	Activity activity;
	int[] profile = { R.drawable.suresh, R.drawable.rahul, R.drawable.ketan,
			R.drawable.rahul, R.drawable.vishnu };
	int[] party = { R.drawable.bjp, R.drawable.congress, R.drawable.app,
			R.drawable.bsp, R.drawable.ncp };

	public Adapter_Result(ArrayList<Result_POJO> main_items, Activity activity) {
		this.main_item_list = main_items;
		this.activity = activity;
		this.ctxt = activity.getApplicationContext();
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

		vi = inflater.inflate(R.layout.custom_single_row_result, arg2, false);
		TextView txt_party_name = (TextView) vi.findViewById(R.id.txt_party_name);
		TextView partyname = (TextView) vi.findViewById(R.id.partyname);

		TextView tv_result = (TextView) vi.findViewById(R.id.tv_result);
		ImageView img_pro = (ImageView) vi.findViewById(R.id.img_pro);
		ImageView party_img = (ImageView) vi.findViewById(R.id.party_img);
		tv_result.setText("" + main_item_list.get(position).getResult());

		img_pro.setImageResource(profile[position]);
		party_img.setImageResource(party[position]);
		partyname.setText(main_item_list.get(position).getHolder_name());
		txt_party_name.setText(main_item_list.get(position).getHolder_name());
		return vi;
	}
}
