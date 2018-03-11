package com.example.gili.jdvtest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.gili.comutil.DBHelper2;
import com.example.gili.data.CallLogAdapter;
import com.example.gili.data.CallLogVO;

import java.util.ArrayList;

public class PhoneActivity extends AppCompatActivity {

    boolean callPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED){
            callPermission  = true;
        }

        if(!callPermission){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 200);
        }

        ArrayList<CallLogVO> datas = new ArrayList<>();

        ListView listView = (ListView)findViewById(R.id.lstView);
        DBHelper2 helper2 = new DBHelper2(this);
        SQLiteDatabase db = helper2.getWritableDatabase();
        Cursor cursor = db.rawQuery("select name, photo, date, phone from tb_calllog", null);
        while (cursor.moveToNext()){
            CallLogVO vo = new CallLogVO();
            vo.name = cursor.getString(0);
            vo.photo = cursor.getString(1);
            vo.date = cursor.getString(2);
            vo.phone = cursor.getString(3);
            datas.add(vo);
        }

        CallLogAdapter adapter = new CallLogAdapter(this, R.layout.main_list_item, datas);
        listView.setAdapter(adapter);


    }
}
