package com.example.gili.data;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gili.jdvtest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gili on 2018-03-11.
 */



public class CallLogAdapter extends ArrayAdapter<CallLogVO> {

    Context context;
    int resId;
    ArrayList<CallLogVO> datas;

    public CallLogAdapter(@NonNull Context context, int resource, ArrayList<CallLogVO> objects) {
        super(context, resource);
        this.context = context;
        this.resId = resource;
        this.datas = objects;
    }

    @Override
    public int getCount() {
        return datas.size();
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(resId, null);

            CallLogWrapper wrapper = new CallLogWrapper(convertView);
            convertView.setTag(wrapper);
        }
        CallLogWrapper wrapper = (CallLogWrapper)convertView.getTag();

        ImageView personImageView = wrapper.personImageView;
        TextView nameView = wrapper.nameView;
        TextView dateView = wrapper.dateView;
        ImageView dialerImageView = wrapper.dialorImageView;

        final CallLogVO vo = datas.get(position);
        nameView.setText(vo.name);
        dateView.setText(vo.date);

        if(vo.photo != null && vo.photo.equals("yes")){
         //   personImageView.setImageResource(R.drawable.hong);
            personImageView.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.hong, null));
        }else {
         //   personImageView.setImageResource(R.drawable.ic_person);
            personImageView.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_person, null));
        }

        if(vo.phone != null && !vo.phone.equals("")){
            dialerImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setAction(intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:"+vo.phone));
                    context.startActivity(intent);
                }
            });
        }
        return convertView;
//        return super.getView(position, convertView, parent);
    }
}
