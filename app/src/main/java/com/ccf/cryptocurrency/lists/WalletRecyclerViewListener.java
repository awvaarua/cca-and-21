package com.ccf.cryptocurrency.lists;

import android.app.Activity;
import android.content.Context;

import com.ccf.cryptocurrency.R;
import com.ccf.cryptocurrency.entities.CurrencyType;
import com.ccf.cryptocurrency.entities.Wallet;
import com.ccf.cryptocurrency.fragments.WalletDetailFragment;
import com.ccf.cryptocurrency.fragments.WalletFragment;

import java.util.List;

public class WalletRecyclerViewListener implements WalletFragment.OnListFragmentInteractionListener {

    private Context context;
    private Activity activity;
    private List<CurrencyType> currencies;

    public WalletRecyclerViewListener(Context ctx, Activity act, List<CurrencyType> currencies) {
        context = ctx;
        activity = act;
        this.currencies = currencies;
    }

    @Override
    public void onListFragmentInteraction(Wallet item) {
        WalletDetailFragment detailFragment = new WalletDetailFragment();
        detailFragment.init(item, currencies);
        activity.getFragmentManager().beginTransaction().replace(R.id.rootLayout, detailFragment).addToBackStack(null).commit();
    }
}
