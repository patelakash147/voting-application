package com.votingsytem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.votingsytem.database.DatabaseHelper;

public class RegistrationActivity extends Activity implements OnClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    String TAG = getClass().getSimpleName();

    Button submit, btn_rst;
    DatabaseHelper databaseHelper;
    EditText edtx_fname, edtx_lname, edtx_eid, edtx_mbno, edtx_pwd, edtx_vid,
            edtx_aano;
    RadioButton radio_male, radio_female;
    ProgressDialog dialog;
    SharedPreferences sharedPreferences;

    ///PALCES API
    AutoCompleteTextView auto_place;
    PlaceArrayAdapter mPlaceArrayAdapter;
    private static LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.5155324,-122.0958616), new LatLng(37.6644309,-121.8159966));
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        sharedPreferences = getSharedPreferences("VotingSystem", MODE_PRIVATE);

        edtx_fname = (EditText) findViewById(R.id.edtx_fname);
        edtx_lname = (EditText) findViewById(R.id.edtx_lname);
        edtx_eid = (EditText) findViewById(R.id.edtx_eid);
        edtx_mbno = (EditText) findViewById(R.id.edtx_mbno);
        edtx_pwd = (EditText) findViewById(R.id.edtx_pwd);
        edtx_vid = (EditText) findViewById(R.id.edtx_vid);
        edtx_aano = (EditText) findViewById(R.id.edtx_aano);
        radio_male = (RadioButton) findViewById(R.id.radio_male);
        radio_female = (RadioButton) findViewById(R.id.radio_female);
        auto_place = (AutoCompleteTextView) findViewById(R.id.auto_place);


        databaseHelper = new DatabaseHelper(this);
        databaseHelper.openDataBase();
        submit = (Button) findViewById(R.id.btn_lgn);
        btn_rst = (Button) findViewById(R.id.btn_rst);

        final String gender;
        if (radio_male.isChecked()) {
            gender = "male";
        } else {
            gender = "female";
        }
        submit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (validate()) {
                    dialog = dialog.show(RegistrationActivity.this, "Loading",
                            "Please Wait...!!");
                    dialog.setCancelable(false);
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            long userId = databaseHelper.insertData(edtx_fname
                                            .getText().toString(), edtx_lname.getText()
                                            .toString(), edtx_eid.getText().toString(),
                                    edtx_pwd.getText().toString(), gender,
                                    edtx_vid.getText().toString(), edtx_aano
                                            .getText().toString(), edtx_mbno
                                            .getText().toString());
                            dialog.dismiss();
                            databaseHelper.close();
                            sharedPreferences.edit().putLong("user_id", userId)
                                    .commit();
                            Intent submit = new Intent(
                                    RegistrationActivity.this,
                                    Registration_otp.class);
                            submit.putExtra("mobile_no", edtx_mbno.getText()
                                    .toString());
                            startActivity(submit);
                            finish();

                        }
                    }, 4000);

                }
            }
        });

        btn_rst.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                edtx_fname.setText("");
                edtx_lname.setText("");
                edtx_eid.setText("");
                edtx_mbno.setText("");
                edtx_pwd.setText("");
                edtx_vid.setText("");
                edtx_aano.setText("");
                radio_male.setChecked(false);
                radio_female.setChecked(false);
            }
        });
        requestLocationAccess();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub

    }

    private boolean validate() {

        if (edtx_fname.getText().toString().trim().length() < 1) {
            Toast.makeText(getApplicationContext(),
                    "FirstName Cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtx_lname.getText().toString().trim().length() < 1) {
            Toast.makeText(getApplicationContext(), "LastName Cannot be empty",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isValidEmail(edtx_eid.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(), "Email Id is not valid",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtx_mbno.getText().toString().trim().length() < 10) {
            Toast.makeText(getApplicationContext(),
                    "Mobile Should be 10 Digit", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtx_pwd.getText().toString().trim().length() < 1) {
            Toast.makeText(getApplicationContext(), "Password Cannot be empty",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtx_vid.getText().toString().trim().length() < 1) {
            Toast.makeText(getApplicationContext(), "Voter Id Cannot be empty",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtx_aano.getText().toString().trim().length() < 1) {
            Toast.makeText(getApplicationContext(), "Adhar No Cannot be empty",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                    .matches();
        }
    }

    public void requestLocationAccess() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        mLocationRequest.setFastestInterval(10000);

        auto_place.setOnItemClickListener(mAutocompleteClickListener);
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setCountry("US")
                .build();

        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, typeFilter);
        auto_place.setAdapter(mPlaceArrayAdapter);
    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i(TAG, "Selected: " + item.description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);

            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);

            Log.i(TAG, "Fetching details for ID: " + item.placeId);

        }
    };


    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {

                Log.e(TAG, "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }
            // Selecting the first object buffer.
            final Place place = places.get(0);
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    protected void stopLocationUpdates() {
        try {
            if (mGoogleApiClient != null) {
                LocationServices.FusedLocationApi.removeLocationUpdates(
                        mGoogleApiClient, this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
        super.onStart();
    }

    @Override
    public void onStop() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }
}
