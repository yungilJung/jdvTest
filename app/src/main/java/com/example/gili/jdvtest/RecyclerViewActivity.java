package com.example.gili.jdvtest;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gili.comutil.DBHelper;
import com.example.gili.comutil.DBHelper3;
import com.example.gili.data.DataVO;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    final String today = "2017-07-01";
    final String yesterday = "2017-06-30";

    boolean addToday;
    boolean addYeaderDay;
    boolean addAnotherDay;
    RecyclerView recyclerView;
    List<ItemVO> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        list=new ArrayList<ItemVO>();

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        DBHelper3 helper = new DBHelper3(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select name, date from tb_data ", null);
        while (cursor.moveToNext()) {
            if (cursor.getString(1).equals(today) && !addToday) {
                HeaderItem item = new HeaderItem();
                item.headerTitle = "오늘";
                list.add(item);
                addToday = true;
            }
            if (cursor.getString(1).equals(yesterday) && !addYeaderDay) {
                HeaderItem item = new HeaderItem();
                item.headerTitle = "어제";
                list.add(item);
                addYeaderDay = true;
            }
            if (!(cursor.getString(1).equals(today) || cursor.getString(1).equals(yesterday)) && !addAnotherDay) {
                HeaderItem item = new HeaderItem();
                item.headerTitle = "이전";
                list.add(item);
                addAnotherDay = true;
            }
            DataItem item = new DataItem();
            item.date = cursor.getString(0);
            item.name = cursor.getString(1);
            list.add(item);
        }
        cursor.close();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(list));
        recyclerView.addItemDecoration(new MyDecoration());
    }

    abstract  class ItemVO{
        public static final int TYPE_HEADER = 0;
        public static final  int TYPE_DATA = 1;

        abstract  int getType();
    }

    class HeaderItem extends ItemVO {
        String headerTitle;

        @Override
        int getType() {
            return ItemVO.TYPE_HEADER;
        }
    }

    class DataItem extends ItemVO{
        String name;
        String date;

        @Override
        int getType() {
            return ItemVO.TYPE_DATA;
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder{
        public TextView headerView;
        public HeaderViewHolder(View itemView){
            super(itemView);
            headerView = (TextView)itemView.findViewById(R.id.mission3_item_header);
        }
    }

    private class DataViewHolder extends RecyclerView.ViewHolder{
        public TextView nameView;
        public TextView dateView;
        public ImageView personView;
        public DataViewHolder(View itemView){
            super(itemView);
            nameView = (TextView)itemView.findViewById(R.id.mission3_item_name);
            dateView = (TextView)itemView.findViewById(R.id.mission3_item_date);
            personView = (ImageView)itemView.findViewById(R.id.mission3__item_person);
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private final List<ItemVO> list;
        public MyAdapter(List<ItemVO> list){
            this.list = list;
        }

        @Override
        public int getItemViewType(int position) {
            return list.get(position).getType();
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if(viewType == ItemVO.TYPE_HEADER){
                View view = LayoutInflater.from(RecyclerViewActivity.this).inflate(R.layout.mission3_item_header, parent, false);
                return new HeaderViewHolder(view);
            }
            else{
                View view = LayoutInflater.from(RecyclerViewActivity.this).inflate(R.layout.mission3_item_data, parent, false);
                return new DataViewHolder(view);
            }
        }


        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ItemVO itemVO = list.get(position);
            if(itemVO.getType()==ItemVO.TYPE_HEADER){
                HeaderViewHolder viewHolder = (HeaderViewHolder)holder;
                HeaderItem headerItem = (HeaderItem)itemVO;
                viewHolder.headerView.setText(headerItem.headerTitle);
            }else{
                DataViewHolder viewHolder = (DataViewHolder)holder;
                DataItem dataItem = (DataItem)itemVO;
                viewHolder.nameView.setText(dataItem.name);
                viewHolder.dateView.setText(dataItem.date);
                int count = position % 5;
                if(count == 0){
                    ((GradientDrawable)viewHolder.personView.getBackground()).setColor(0xff009688);
                }else if(count==1){
                    ((GradientDrawable)viewHolder.personView.getBackground()).setColor(0xff4285f4);
                }else if(count==2){
                    ((GradientDrawable)viewHolder.personView.getBackground()).setColor(0xff039be5);
                }else if(count==3){
                    ((GradientDrawable)viewHolder.personView.getBackground()).setColor(0xff9c27b0);
                }else if(count==4){
                    ((GradientDrawable)viewHolder.personView.getBackground()).setColor(0xff0097a7);
                }
            }
        }

        /**
         * Returns the total number of items in the data set held by the adapter.
         *
         * @return The total number of items in this adapter.
         */
        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class  MyDecoration extends  RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int index = parent.getChildAdapterPosition(view);
            ItemVO itemVO = list.get(index);
            if(itemVO.getType()==ItemVO.TYPE_DATA){
                view.setBackgroundColor(0xFFFFFFFF);
                ViewCompat.setElevation(view, 10.0f);
            }
            outRect.set(20,10,20,10);
        }
    }

}
