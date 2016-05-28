package com.swiftpot.android.teletrosky.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.swiftpot.android.teletrosky.R;
import com.swiftpot.android.teletrosky.services.QuickStartPreferences;
import com.swiftpot.android.teletrosky.services.RegistrationIntentService;

import java.util.Locale;


public class FragmentHome extends Fragment implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    protected static final String TAG = "FragmentHome";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 1001;
    int isAvailableCode ;
    private boolean isReceiverRegistered;

    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;

    private TextView tvLocationLoading;
    private AutoCompleteTextView edtDestinationName;
    private ImageButton btnNext ;
    private String[] areaNames = {"Okponglo","Oyibi","Odonna","Awoshie","Kaneshie"};


    //private OnFragmentInteractionListener mListener;

    public FragmentHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentHome.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHome newInstance(String param1, String param2) {
        FragmentHome fragment = new FragmentHome();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onResume() {
        super.onResume();
        //check again in OnResume
        //checkGoogleServicesPresenceAndAct();
        registerReceiver();
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mRegistrationBroadcastReceiver);
        isReceiverRegistered = false;
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        if (checkGoogleServicesPresenceAndAct()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(getActivity(), RegistrationIntentService.class);
            getActivity().startService(intent);
        }

        //buildGoogleApiClient();

        tvLocationLoading = (TextView)v.findViewById(R.id.tvLocationLoading);
        edtDestinationName = (AutoCompleteTextView)v.findViewById(R.id.edtLocationName);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,areaNames);

        edtDestinationName.setAdapter(adapter);
        edtDestinationName.setThreshold(1);


        edtDestinationName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tvLocationLoading.setVisibility(View.VISIBLE);
                double cost = 33.2; String busStop = "4";
                String dataLoc = "Cost="+cost+"Ghs\n"+busStop+" Bus Stops ";
                String s = adapterView.getItemAtPosition(i).toString();
                System.out.println("Vallue of item at position = "+s);
                if (s.equalsIgnoreCase("Okponglo")){
                    cost = 5.20;  busStop = "4";
                    dataLoc = "Cost="+cost+"Ghs\n"+busStop+" Bus Stops ";
                    tvLocationLoading.setText(dataLoc);
                }else if((edtDestinationName.getText().toString().equals("Oyibi"))){
                    cost = 3.20;  busStop = "2";
                    dataLoc = "Cost="+cost+"Ghs\n"+busStop+" Bus Stops ";
                    tvLocationLoading.setText(dataLoc);
                }else if((edtDestinationName.getText().toString().equals("Odonna"))){
                    cost = 5.70;  busStop = "2";
                    dataLoc = "Cost="+cost+"Ghs\n"+busStop+" Bus Stops ";
                    tvLocationLoading.setText(dataLoc);
                }else{
                    tvLocationLoading.setText(dataLoc);
                }
            }
        });
        btnNext = (ImageButton)v.findViewById(R.id.btnHomeNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uriFormat = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:%f,%f(TeleTrosky)", mLastLocation.getLatitude(), mLastLocation.getLongitude());
                //String uri = String.format(Locale.ENGLISH, "geo:%f,%f", mLastLocation.getLatitude(), mLastLocation.getLongitude());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uriFormat));
                startActivity(intent);
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buildGoogleApiClient();
    }

    private void registerReceiver(){
        if(!isReceiverRegistered) {
            LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mRegistrationBroadcastReceiver,
                    new IntentFilter(QuickStartPreferences.REGISTRATION_COMPLETE));
            isReceiverRegistered = true;
        }
    }
    /**
     * Builds a GoogleApiClient. Uses the addApi() method to request the LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(FragmentHome.this)
                .addOnConnectionFailedListener(FragmentHome.this)
                .addApi(LocationServices.API)
                .build();
    }



    @SuppressWarnings("MissingPermission")
    @Override
    public void onConnected(@Nullable Bundle bundle) {
// Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {

            tvLocationLoading.setText(String.format(Locale.US,"%s: %f  %s: %f", "Lattitude",
                    mLastLocation.getLatitude(),"Longitude",mLastLocation.getLongitude()));
//            mLongitudeText.setText(String.format("%s: %f", mLongitudeLabel,
//                    mLastLocation.getLongitude()));
        } else {
            Toast.makeText(getActivity(), "No Location Found!!", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
// The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }


    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    public boolean checkGoogleServicesPresenceAndAct(){
        boolean isServicePresent = false;
        if(isServicesPresent()){
            //do nothing and continue execution as its present already
            isServicePresent = true;
        }else{
            if (GoogleApiAvailability.getInstance().isUserResolvableError(isAvailableCode)) {
                showErrorDialog(isAvailableCode);

            } else {
                Toast.makeText(getActivity(), "This device is not supported.Install Google Play Services", Toast.LENGTH_LONG).show();
                getActivity().finish();
            }
            //isServicePresent = false; //unneccessary since isServicePresent is already false
        }

        return isServicePresent;
    }

    public void showErrorDialog(int errorCode){
        GoogleApiAvailability.getInstance().getErrorDialog(getActivity(),errorCode,REQUEST_CODE_RECOVER_PLAY_SERVICES).show();
    }
    public boolean isServicesPresent(){
        boolean serviceStatus = false;

        isAvailableCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getActivity());

        if(isAvailableCode == ConnectionResult.SUCCESS){
            serviceStatus = true;
        }
        else{
            //serviceStatus is already false
        }

        return serviceStatus;
    }
}
