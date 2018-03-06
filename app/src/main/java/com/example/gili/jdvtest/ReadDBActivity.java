package com.example.gili.jdvtest;

import android.content.ClipData;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

import com.example.gili.comutil.CommonUtils;
import com.example.gili.comutil.DBHelper;
import com.example.gili.data.CustomAdaptor;
import com.example.gili.data.ListItem;

import java.util.ArrayList;
import java.util.List;

public class ReadDBActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_db);

        ListView lstView = (ListView)findViewById(R.id.listView);
        DBHelper helper = new DBHelper(this);
        try (SQLiteDatabase db = helper.getReadableDatabase()) {
            Cursor cursor = db.rawQuery("select _id, title, content from tb_memo order by _id desc ", null);
           /* while(cursor.moveToNext()){
                CommonUtils.setTextView(ReadDBActivity.this, R.id.lblTitle, cursor.getString(0));
                CommonUtils.setTextView(ReadDBActivity.this, R.id.lblContent, cursor.getString(1));
            }*/

            /*ArrayAdapter arrary = new ArrayAdapter(this,android.R.layout.simple_list_item_1, data);
            lstView.setAdapter(arrary);*/

           /* SimpleAdapter adpater = new SimpleAdapter(this, data, android.R.layout.simple_list_item_1, new String[]{"_id","title"},new int[]{android.R.id.text1, android.R.id.text2});
            lstView.setAdapter(adpater);*/

           /* CursorAdapter cursorAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_2, cursor, new String[]{"_id","title"},new int[]{android.R.id.text1, android.R.id.text2},
                    CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

            lstView.setAdapter(cursorAdapter);*/

            ArrayList<ListItem> datas = new ArrayList<>();
            while(cursor.moveToNext()){
                ListItem item = new ListItem();
                item.id = cursor.getString(0);
                item.title = cursor.getString(1);
                item.content = cursor.getString(2);
                datas.add(item);
            }

            db.close();

            CustomAdaptor adpator = new CustomAdaptor(this, R.layout.item_list, datas);
            lstView.setAdapter(adpator);
        }

    }
}
