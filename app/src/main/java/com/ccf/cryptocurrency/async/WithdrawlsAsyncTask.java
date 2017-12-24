package com.ccf.cryptocurrency.async;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.ccf.cryptocurrency.Infrastructure.ApiRestClient;
import com.ccf.cryptocurrency.R;
import com.ccf.cryptocurrency.entities.Withdrawl;
import com.ccf.cryptocurrency.lists.WithdrawlListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WithdrawlsAsyncTask extends AsyncTask<Void, Void, List<Withdrawl>> {

    private View view;
    private Context context;
    private ProgressBar progressBar;
    private int walletId;

    public WithdrawlsAsyncTask(Context context, View rootView, int walletId) {
        this.view = rootView;
        this.context = context;
        this.progressBar = view.findViewById(R.id.list_withdrawls_pb);
        this.walletId = walletId;
    }

    @Override
    protected void onPreExecute() {
        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected List<Withdrawl> doInBackground(Void... params) {
        JSONArray response = ApiRestClient.get("/wallets/"+ 1 +"/withdrawals");
        ArrayList<Withdrawl> list = new ArrayList<>();

        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject o = response.getJSONObject(i);
                String dateStr = o.getString("date");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse(dateStr);
                list.add(new Withdrawl(o.getString("txId"), date, o.getDouble("amount")));
            } catch (JSONException e) { } catch (ParseException e) {
            }
        }
        return list;
    }

    @Override
    protected void onPostExecute(List<Withdrawl> items) {
        if (progressBar != null) progressBar.setVisibility(View.GONE);
        final ListView lv = view.findViewById(R.id.withdrawl_list_view);
        lv.setAdapter(new WithdrawlListAdapter(context, items));
    }
}
