package com.example.gili.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gili.jdvtest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gili on 2018-03-06.
 */

public class CustomAdaptor extends ArrayAdapter<ListItem> {
    Context context;
    int resId;
    ArrayList<ListItem> items = new ArrayList<ListItem>();

    public CustomAdaptor(@NonNull Context context, int resource, ArrayList<ListItem> items) {
        super(context, resource, items);
        this.context = context;
        this.resId = resource;
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(resId, null);

        TextView txtNo = (TextView)convertView.findViewById(R.id.txtNo);
        TextView txtTitle = (TextView)convertView.findViewById(R.id.txtTitle);
        TextView txtContent = (TextView)convertView.findViewById(R.id.txtContent);

        final ListItem item = items.get(position);

        txtNo.setText(item.id);
        txtTitle.setText(item.title);
        txtContent.setText(item.content);

        return  convertView;
        //return super.getView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return items.size();
    }
}
