package com.ccf.cryptocurrency.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.adcolony.sdk.*;
import com.ccf.cryptocurrency.R;

public class TestFragment extends Fragment {

    final private String APP_ID = "app2852e62e1dc3449cb0";
    final private String ZONE_ID = "vz177d09eb9b104b69b4";
    final private String TAG = "AdColonyDemo";

    private int userId;
    private Button show_button;
    private ProgressBar progress;
    private AdColonyInterstitial ad;
    private AdColonyInterstitialListener listener;
    private AdColonyAdOptions ad_options;


    private OnFragmentInteractionListener mListener;

    public TestFragment() {
    }

    public static TestFragment newInstance(String param1, String param2) {
        TestFragment fragment = new TestFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String session = sharedPref.getString("user_session", "");
        userId = sharedPref.getInt("user_id", 0);

        /** Construct optional app options object to be sent with configure */
        AdColonyAppOptions app_options = new AdColonyAppOptions();
        app_options.setUserID(userId + "#" + "1");

        /**
         * Configure AdColony in your launching Activity's onCreate() method so that cached ads can
         * be available as soon as possible.
         */
        AdColony.configure(getActivity(), app_options, APP_ID, ZONE_ID);

        /** Optional user metadata sent with the ad options in each request */
        AdColonyUserMetadata metadata = new AdColonyUserMetadata()
                .setUserAge(26)
                .setUserEducation(AdColonyUserMetadata.USER_EDUCATION_BACHELORS_DEGREE)
                .setUserGender(AdColonyUserMetadata.USER_MALE);

        /** Ad specific options to be sent with request */
        ad_options = new AdColonyAdOptions()
                .enableConfirmationDialog(true)
                .enableResultsDialog(true)
                .setUserMetadata(metadata);

        /** Create and set a reward listener */
        AdColony.setRewardListener(new AdColonyRewardListener() {
            @Override
            public void onReward(AdColonyReward reward) {
                /** Query reward object for info here */
                System.out.println("On reward");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);

        progress = (ProgressBar) view.findViewById(R.id.progress);
        show_button = (Button) view.findViewById(R.id.showbutton);

        show_button.setEnabled(false);

        /**
         * Set up listener for interstitial ad callbacks. You only need to implement the callbacks
         * that you care about. The only required callback is onRequestFilled, as this is the only
         * way to get an ad object.
         */
        listener = new AdColonyInterstitialListener() {
            /** Ad passed back in request filled callback, ad can now be shown */
            @Override
            public void onRequestFilled(AdColonyInterstitial _ad) {
                ad = _ad;
                show_button.setEnabled(true);
                progress.setVisibility(View.INVISIBLE);
                System.out.println("onRequestFilled");
            }

            /** Ad request was not filled */
            @Override
            public void onRequestNotFilled(AdColonyZone zone) {
                progress.setVisibility(View.INVISIBLE);
                System.out.println("onRequestNotFilled");
            }

            /** Ad opened, reset UI to reflect state change */
            @Override
            public void onOpened(AdColonyInterstitial ad) {
                show_button.setEnabled(false);
                progress.setVisibility(View.VISIBLE);
                System.out.println("onOpened");
            }

            /** Request a new ad if ad is expiring */
            @Override
            public void onExpiring(AdColonyInterstitial ad) {
                show_button.setEnabled(false);
                progress.setVisibility(View.VISIBLE);
                AdColony.requestInterstitial(ZONE_ID, this, ad_options);
                System.out.println("onExpiring");
            }
        };

        /** Set up button to show an ad when clicked */
        show_button = (Button) view.findViewById(R.id.showbutton);
        show_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ad.show();
            }
        });

        if (ad == null || ad.isExpired()) {
            /**
             * Optionally update location info in the ad options for each request:
             * LocationManager location_manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
             * Location location = location_manager.getLastKnownLocation( LocationManager.GPS_PROVIDER );
             * ad_options.setUserMetadata( ad_options.getUserMetadata().setUserLocation( location ) );
             */
            progress.setVisibility(View.VISIBLE);
            AdColony.requestInterstitial(ZONE_ID, listener, ad_options);
        }


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.watch_video_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });*/
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
