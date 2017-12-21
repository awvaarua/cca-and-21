package com.ccf.cryptocurrency.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ccf.cryptocurrency.Infrastructure.ApiRestClient;
import com.ccf.cryptocurrency.R;
import com.ccf.cryptocurrency.entities.CurrencyType;
import com.ccf.cryptocurrency.entities.Wallet;
import com.ccf.cryptocurrency.lists.MyWalletRecyclerViewAdapter;
import com.ccf.cryptocurrency.lists.WalletRecyclerViewListener;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class WalletDetailFragment extends Fragment {

    private View view;
    private Context context;
    private Wallet wallet;
    private CurrencyType currency;

    public WalletDetailFragment() {
    }

    public void init(Wallet wallet, List<CurrencyType> currencies) {
        this.wallet = wallet;
        this.currency = searchCurrency(wallet.getCurrencyType().getId(), currencies);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wallet_detail, container, false);
        return view;
    }

    private CurrencyType searchCurrency(int id, List<CurrencyType> currencies) {
        for (CurrencyType c : currencies) {
            if (c.getId() == id) return c;
        }
        return null;
    }
}
