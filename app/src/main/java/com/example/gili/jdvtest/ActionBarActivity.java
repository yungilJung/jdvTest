package com.example.gili.jdvtest;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.gili.comutil.CommonUtils;

public class ActionBarActivity extends AppCompatActivity {

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_bar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(R.drawable.a_1);

        TextView tv = (TextView)findViewById(R.id.txtContext);
        registerForContextMenu(tv);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            CommonUtils.showLongMessage(ActionBarActivity.this,"Home click");
            return true;
        }
        if(item.getItemId()==0){
            CommonUtils.showLongMessage(ActionBarActivity.this,"menu 1");
            return true;
        }
        if(item.getItemId()==1){
            CommonUtils.showLongMessage(ActionBarActivity.this,"menu 2");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       /* MenuItem item1 = menu.add(0,0,0,"술라이드 쇼");
        MenuItem item2 = menu.add(0,1,0, "앨버무추가");*/

        // xml 메뉴...

        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.menu4);
        SearchView search =(SearchView) menuItem.getActionView();

        return true;
        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0,0,0,"서버전송");
        menu.add(0,1,0,"보관함에 보관");
        menu.add(0,2,0,"삭제");



    }
}
