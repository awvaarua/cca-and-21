package com.ccf.cryptocurrency.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.ccf.cryptocurrency.R;
import com.ccf.cryptocurrency.WebViewActivity;
import com.ccf.cryptocurrency.async.WithdrawlsAsyncTask;
import com.ccf.cryptocurrency.entities.CurrencyType;
import com.ccf.cryptocurrency.entities.Wallet;
import com.ccf.cryptocurrency.entities.Withdrawl;
import java.util.List;


public class WalletDetailFragment extends Fragment {

    private View view;
    private Context context;
    private Wallet wallet;
    private CurrencyType currency;

    private TextView amount;
    private ListView withdrawls_lv;
    private Button withdral_bt;
    private Activity activity;

    public WalletDetailFragment() {
    }

    public void init(Context c, Activity a, Wallet wallet, List<CurrencyType> currencies) {
        this.context = c;
        this.activity = a;
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

        amount = view.findViewById(R.id.detail_amount);
        withdrawls_lv = view.findViewById(R.id.withdrawl_list_view);

        withdrawls_lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Withdrawl item = (Withdrawl) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("txId", item.getTxId());
                startActivity(intent);
            }
        });

        amount.setText(wallet.getAmountWithCurrency());

        new WithdrawlsAsyncTask(context, view, wallet.getId()).execute();

        withdral_bt = (Button) view.findViewById(R.id.wt_button);
        withdral_bt.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                WithdrawlFragment wd = new WithdrawlFragment();
                wd.init(context, wallet);
                activity.getFragmentManager().beginTransaction().replace(R.id.rootLayout, wd).addToBackStack(null).commit();
            }
        });

        return view;
    }

    private CurrencyType searchCurrency(int id, List<CurrencyType> currencies) {
        for (CurrencyType c : currencies) {
            if (c.getId() == id) return c;
        }
        return null;
    }
}
