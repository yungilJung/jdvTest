package com.example.gili.jdvtest;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;


public class NavigationDrawerActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;

    boolean isDrawerOpend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        toolbar = (Toolbar)findViewById(R.id.toolbar_drawer);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true); // 툴바의 제목을 표시를 한다.

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                isDrawerOpend = true;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                isDrawerOpend = false;
            }
        };
        drawerLayout.addDrawerListener(toggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 삼단 메뉴 아이콘 출력을 한다.
        toggle.syncState();                                   // 삼단 메뉴 아이콘 출력을 한다. 두라인이 함께 실행되어야 됨.
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(isDrawerOpend){
            drawerLayout.closeDrawers();
        }
        else {
            super.onBackPressed();
        }
    }
}
