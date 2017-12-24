package com.ccf.cryptocurrency.fragments;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ccf.cryptocurrency.R;
import com.ccf.cryptocurrency.entities.Wallet;

import java.util.List;

public class WithdrawlFragment extends Fragment {

    private Wallet wallet;
    private Context context;

    public WithdrawlFragment() {
    }

    public void init(Context c, Wallet wallet) {
        this.context = c;
        this.wallet = wallet;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_withdrawl, container, false);
    }
}
