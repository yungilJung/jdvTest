package com.example.gili.comutil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gili on 2018-03-05.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context){
        super(context, "memodb", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String memoSQL = " create table tb_memo " +
                "(_id integer primary key autoincrement, "+
                " title, "+
                " content )";
        sqLiteDatabase.execSQL(memoSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if(i1==DATABASE_VERSION){
            sqLiteDatabase.execSQL("drop table tb_memo ");
            onCreate(sqLiteDatabase);
        }
    }
}
