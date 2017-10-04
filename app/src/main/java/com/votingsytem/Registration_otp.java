package com.votingsytem;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



import java.security.SecureRandom;
import java.util.Random;

public class Registration_otp extends Activity {

	Button nxt;
	EditText edtx_passwrod;
	// for SMS
	private final static String INTENT_ACTION_SENT = "com.votingsytem.INTENT_ACTION_SENT";
	private final static String INTENT_ACTION_DELIVERY = "com.votingsytem.INTENT_ACTION_DELIVERY";
	private final static int REQUEST_CODE_ACTION_SENT = 1;
	private static final int REQUEST_CODE_ACTION_DELIVERY = 2;
	private BroadcastReceiver smsSentDeliveredReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration_otp);
		edtx_passwrod = (EditText) findViewById(R.id.edtx_passwrod);
		final String OTP = generatePassword();
		String completeMessage = "Your OTP is: " + OTP;
		if (getIntent().getExtras() != null) {
			sendSMS(getIntent().getStringExtra("mobile_no"), completeMessage);
		}
		nxt = (Button) findViewById(R.id.next);
		nxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if (edtx_passwrod.getText().toString().equalsIgnoreCase(OTP)) {
					Intent next = new Intent(Registration_otp.this,
							CameraPhotoCapture.class);
					startActivity(next);
					finish();
				} else {
					Toast.makeText(getApplicationContext(),
							"You have entered wrong OTP", 3000).show();
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registration_otp, menu);
		return true;
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
