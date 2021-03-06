package com.ccf.cryptocurrency.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ccf.cryptocurrency.Infrastructure.ApiRestClient;
import com.ccf.cryptocurrency.entities.CurrencyType;
import com.ccf.cryptocurrency.entities.Wallet;
import com.ccf.cryptocurrency.lists.MyWalletRecyclerViewAdapter;
import com.ccf.cryptocurrency.R;
import com.ccf.cryptocurrency.lists.WalletRecyclerViewListener;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class WalletFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private View view;
    private Context context;
    private List<Wallet> wallets;
    private List<CurrencyType> currencies;

    public WalletFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wallet_list, container, false);
        if (view instanceof SwipeRefreshLayout) {
            mSwipeRefreshLayout = (SwipeRefreshLayout) view;
            mSwipeRefreshLayout.setRefreshing(true);
            context = view.getContext();
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    getWalletsRequest();
                }
            });
            getWallets();
            mListener = new WalletRecyclerViewListener(context, getActivity(), currencies);
        }
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Wallet item);
    }

    private void getWallets() {
        if (currencies == null) {
            getCurrenciesRequest();
        } else {
            getWalletsRequest();
        }
    }

    private void getCurrenciesRequest() {
        currencies = new ArrayList<CurrencyType>();
        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String session = sharedPref.getString("user_session", "");
        ApiRestClient.addHeaderAsync("Authorization", session);
        ApiRestClient.getAsync("/currencytypes", null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject item = response.getJSONObject(i);
                        CurrencyType c = new CurrencyType(item.getInt("id"), item.getString("name"), item.getString("shortName"), item.getString("icon"));
                        currencies.add(c);
                    } catch (JSONException e) {

                    }
                }
                getWalletsRequest();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                mSwipeRefreshLayout.setRefreshing(false);
                Snackbar.make(view.findViewById(R.id.list_wallets), R.string.error_connection, Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }

    private CurrencyType searchCurrency(int id) {
        for (CurrencyType c : currencies) {
            if (c.getId() == id) return c;
        }
        return null;
    }

    private void getWalletsRequest() {
        wallets = new ArrayList<Wallet>();
        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String session = sharedPref.getString("user_session", "");
        int userId = sharedPref.getInt("user_id", 0);

        ApiRestClient.addHeaderAsync("Authorization", session);
        ApiRestClient.getAsync("/customers/" + userId + "/wallets", null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject item = response.getJSONObject(i);
                        Wallet w = new Wallet(item.getInt("id"), item.getDouble("amount"), searchCurrency(item.getInt("currencyTypeId")));
                        wallets.add(w);
                    } catch (JSONException e) {

                    }
                }
                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list_wallets);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(new MyWalletRecyclerViewAdapter(wallets, mListener));
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                mSwipeRefreshLayout.setRefreshing(false);
                Snackbar.make(view.findViewById(R.id.list_wallets), R.string.error_connection, Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }
}
