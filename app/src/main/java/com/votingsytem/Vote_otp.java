package com.votingsytem;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.votingsytem.database.DatabaseHelper;
import com.votingsytem.pojo.User_POJO;

import java.security.SecureRandom;
import java.util.Random;

public class Vote_otp extends Activity {

	Button next;
	DatabaseHelper databaseHelper;
	User_POJO user = new User_POJO();
	SharedPreferences sharedPreferences;
	EditText edtx;
	String candidate_id;
	// for SMS
	private final static String INTENT_ACTION_SENT = "com.votingsytem.INTENT_ACTION_SENT";
	private final static String INTENT_ACTION_DELIVERY = "com.votingsytem.INTENT_ACTION_DELIVERY";
	private final static int REQUEST_CODE_ACTION_SENT = 1;
	private static final int REQUEST_CODE_ACTION_DELIVERY = 2;
	private BroadcastReceiver smsSentDeliveredReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vote_otp);
		databaseHelper = new DatabaseHelper(getApplicationContext());
		databaseHelper.openDataBase();
		sharedPreferences = getSharedPreferences("VotingSystem", MODE_PRIVATE);
		user = databaseHelper.getProfile(""
				+ sharedPreferences.getLong("user_id", 1));

		if (getIntent().getExtras() != null) {
			/*
			 * Toast.makeText(getApplicationContext(),
			 * getIntent().getStringExtra("candidate_id"), 2000).show();
			 */
			candidate_id = getIntent().getStringExtra("candidate_id");
		}

		final String OTP = generatePassword();
		String completeMessage = "Your OTP is: " + OTP;
		sendSMS(user.mobile, completeMessage);

		edtx = (EditText) findViewById(R.id.edtx);
		next = (Button) findViewById(R.id.votenext);
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (edtx.getText().toString().equalsIgnoreCase(OTP)) {
					sharedPreferences.edit().putBoolean("vote_complete", true)
							.commit();
					Intent finish = new Intent(Vote_otp.this,
							HomeActivity.class);
					startActivity(finish);
					finish();
					Toast.makeText(getApplicationContext(),
							"Vote Submitted..!! Thanks", 3000).show();
				}
			}
		});

	}

	public static String generatePassword() {
		String chars = "abcdefghijklmnopqrstuvwxyz"
				+ "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789!@%$%&^?|~'\"#+="
				+ "\\*/.,:;[]()-_<>";

		final int PW_LENGTH = 20;
		Random rnd = new SecureRandom();
		StringBuilder pass = new StringBuilder();
		for (int i = 0; i < PW_LENGTH; i++)
			pass.append(chars.charAt(rnd.nextInt(chars.length())));
		return pass.toString();
	}

	// Logic of SMS sending
	private void sendSMS(String number, String message) {
		/*
		 * create intent instance and pass INTENT_ACTION_SENT INTENT_ACTION_SENT
		 * is used to send an sms on GSM
		 */
		Intent sentIntent = new Intent(INTENT_ACTION_SENT);
		/*
		 * create pendingintent instance and pass this as context
		 * instance,REQUEST_CODE_ACTION_SENT and FLAG_UPDATE_CURRENT
		 * REQUEST_CODE_ACTION_SENT=1 defined at top FLAG_UPDATE_CURRENT: Flag
		 * for use with getActivity(Context, int, Intent, int),
		 * getBroadcast(Context, int, Intent, int), and getService(Context, int,
		 * Intent, int): if the described PendingIntent already exists, then
		 * keep it but its replace its extra data with what is in this new
		 * Intent. This can be used if you are creating intents where only the
		 * extras change, and don't care that any entities that received your
		 * previous PendingIntent will be able to launch it with your new extras
		 * even if they are not explicitly given to it.
		 */
		PendingIntent pendingSentIntent = PendingIntent.getBroadcast(this,
				REQUEST_CODE_ACTION_SENT, sentIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		/*
		 * create intent instance and pass INTENT_ACTION_DELIVERY
		 * INTENT_ACTION_DELIVERY is used to receive an sms delivery on GSM
		 */
		Intent deliveryIntent = new Intent(INTENT_ACTION_DELIVERY);
		/*
		 * create pendingintent instance and pass this as context
		 * instance,REQUEST_CODE_ACTION_DELIVERY and FLAG_UPDATE_CURRENT
		 * REQUEST_CODE_ACTION_DELIVERY=2 defined at top
		 * FLAG_UPDATE_CURRENT:Flag for use with getActivity(Context, int,
		 * Intent, int), getBroadcast(Context, int, Intent, int), and
		 * getService(Context, int, Intent, int): if the described PendingIntent
		 * already exists, then keep it but its replace its extra data with what
		 * is in this new Intent. This can be used if you are creating intents
		 * where only the extras change, and don't care that any entities that
		 * received your previous PendingIntent will be able to launch it with
		 * your new extras even if they are not explicitly given to it.
		 */
		PendingIntent pendingDeliveryIntent = PendingIntent.getBroadcast(this,
				REQUEST_CODE_ACTION_DELIVERY, deliveryIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		// Create instance of SmsManager and get the default instance of the Sms
		// manager
		SmsManager smsManager = SmsManager.getDefault();

		/*
		 * Second parameter is the service center number. Use null if you wantto
		 * use the default number
		 */
		smsManager.sendTextMessage(number, null, message, pendingSentIntent,
				pendingDeliveryIntent);
	}

}
