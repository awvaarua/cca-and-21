package com.ccf.cryptocurrency.async;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.ccf.cryptocurrency.R;
import com.ccf.cryptocurrency.entities.Withdrawl;
import com.ccf.cryptocurrency.lists.WithdrawlListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WithdrawlsAsyncTask extends AsyncTask<Void, Void, List<Withdrawl>> {

    private View view;
    private Context context;
    private ProgressBar progressBar;

    public WithdrawlsAsyncTask(Context context, View rootView) {
        this.view = rootView;
        this.context = context;
        this.progressBar = view.findViewById(R.id.list_withdrawls_pb);
    }

    @Override
    protected void onPreExecute() {
        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected List<Withdrawl> doInBackground(Void... params) {
        // everything in here gets executed in a separate thread
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList<Withdrawl> list = new ArrayList<>();
        list.add(new Withdrawl(123123123));
        list.add(new Withdrawl(123123123));
        list.add(new Withdrawl(123123123));
        list.add(new Withdrawl(123123123));
        list.add(new Withdrawl(123123123));
        return list;
    }

    @Override
    protected void onPostExecute(List<Withdrawl> items) {
        if (progressBar != null) progressBar.setVisibility(View.GONE);
        final ListView lv = view.findViewById(R.id.withdrawl_list_view);
        lv.setAdapter(new WithdrawlListAdapter(context, items));
    }
}
