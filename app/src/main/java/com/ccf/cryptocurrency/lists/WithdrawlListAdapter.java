package com.ccf.cryptocurrency.lists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ccf.cryptocurrency.R;
import com.ccf.cryptocurrency.entities.Withdrawl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ssb on 22/12/17.
 */

public class WithdrawlListAdapter extends BaseAdapter {

    private Context context;
    private List<Withdrawl> items;

    public WithdrawlListAdapter(Context c, List<Withdrawl> items) {
        this.context = c;
        this.items = items;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Withdrawl getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            // Create a new view into the list.
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.withdrawl_list_item, viewGroup, false);
        }

        // Set data into the view.
        TextView date = (TextView) view.findViewById(R.id.date_withdrawl);
        TextView amount = (TextView) view.findViewById(R.id.amount_withdrawl);

        Withdrawl item = this.items.get(i);
        //date.setText(item.getDate() + "");
        //amount.setText(item.getAmount() +  "");

        date.setText("23/12/1994");
        amount.setText("2132132131 DOGE");

        return view;
    }
}
