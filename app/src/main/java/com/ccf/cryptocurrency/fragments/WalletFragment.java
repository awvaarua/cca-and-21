package com.ccf.cryptocurrency.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ccf.cryptocurrency.Infrastructure.ApiRestClient;
import com.ccf.cryptocurrency.LoadDataActivity;
import com.ccf.cryptocurrency.UserActivity;
import com.ccf.cryptocurrency.lists.MyWalletRecyclerViewAdapter;
import com.ccf.cryptocurrency.R;
import com.ccf.cryptocurrency.dummy.DummyContent;
import com.ccf.cryptocurrency.dummy.DummyContent.DummyItem;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class WalletFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;
    private View view;
    private Context context;

    public WalletFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wallet_list, container, false);
        if (view instanceof RecyclerView) {
            context = view.getContext();
            getWallets();
            /*
            FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.watch_video_fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
            */
        }
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(DummyItem item);
    }

    private void getWallets() {

        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String session = sharedPref.getString("user_session", "");
        int userId = sharedPref.getInt("user_id", 0);

        ApiRestClient.addHeader("Authorization", session);
        ApiRestClient.get("/customers/"+userId+"/wallets", null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String sessionId = response.getString("id");
                    RecyclerView recyclerView = (RecyclerView) view;
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setAdapter(new MyWalletRecyclerViewAdapter(DummyContent.ITEMS, mListener));

                } catch (JSONException e) {
                    System.out.println(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                System.out.println(errorResponse);
            }
        });
    }
}
