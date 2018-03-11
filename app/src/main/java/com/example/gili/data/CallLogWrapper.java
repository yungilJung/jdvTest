package com.example.gili.data;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gili.jdvtest.R;

/**
 * Created by gili on 2018-03-11.
 */

public class CallLogWrapper {
    public ImageView personImageView;
    public TextView nameView;
    public TextView dateView;
    public ImageView dialorImageView;

    public CallLogWrapper(View root){
        personImageView = (ImageView)root.findViewById(R.id.main_item_preson);
        nameView = (TextView)root.findViewById(R.id.main_item_name);
        dateView = (TextView)root.findViewById(R.id.main_item_date);
        dialorImageView = (ImageView)root.findViewById(R.id.main_item_dialer);
    }


}
