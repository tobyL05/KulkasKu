package com.example.kulkasku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomBaseAdapter extends BaseAdapter {

    private Context ctx;
    private ArrayList<String> itemNames,expiryDates;
    private LayoutInflater inflater;
    public CustomBaseAdapter(Context ctx, ArrayList<String> itemNames, ArrayList<String> expiryDates){
        this.ctx = ctx;
        this.itemNames = itemNames;
        this.expiryDates = expiryDates;
        this.inflater = LayoutInflater.from(ctx);
    }
    @Override
    public int getCount() {
        return itemNames.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private void deleteItem(int pos){

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View convertView = inflater.inflate(R.layout.activity_custom_list_view, null);
        TextView itemname = convertView.findViewById(R.id.foodName);
        TextView expdate = convertView.findViewById(R.id.exp_date);
        itemname.setText(itemNames.get(i));
        expdate.setText(expiryDates.get(i));
        return convertView;

    }
}
