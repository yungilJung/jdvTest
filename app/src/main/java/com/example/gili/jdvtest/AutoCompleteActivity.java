package com.example.gili.jdvtest;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class AutoCompleteActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, TextWatcher {

    EditText editText;
    ListView listView;

    ArrayList<String> base;
    ArrayList<SpannableStringBuilder> datas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_complete);

        editText = (EditText)findViewById(R.id.txtSearch);
        listView = (ListView)findViewById(R.id.lstSearchView);

        listView.setOnItemClickListener(this);
        editText.addTextChangedListener(this);

        base = new ArrayList<String>();
        base.add("우편번호 검색");
        base.add("지도 검색");
        base.add("대법원 검색");
        base.add("나의 사진 검색");
        base.add("주소 검색");
        base.add("다음지도 검색");
        base.add("검색등록 검색");

        datas = new ArrayList<SpannableStringBuilder>();
        ArrayAdapter<SpannableStringBuilder> adapter = new ArrayAdapter<SpannableStringBuilder>(this, android.R.layout.simple_list_item_1, datas);
        listView.setAdapter(adapter);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
        datas = new ArrayList<SpannableStringBuilder>();
        for(String txt:base){
            if(txt.contains(s)){
                int startPos = txt.indexOf(s.toString());
                int endPos = startPos+s.length();
                SpannableStringBuilder builder = new SpannableStringBuilder(txt);
                builder.setSpan(new ForegroundColorSpan(Color.BLUE), startPos, endPos, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                builder.setSpan(new StyleSpan(Typeface.BOLD),  startPos, endPos, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                datas.add(builder);
            }
        }
        ArrayAdapter<SpannableStringBuilder> adapter = new ArrayAdapter<SpannableStringBuilder>(this, android.R.layout.simple_list_item_1, datas);
        listView.setAdapter(adapter);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        editText.setText(datas.get(i).toString());
        datas = new ArrayList<SpannableStringBuilder>();
        ArrayAdapter<SpannableStringBuilder> adapter = new ArrayAdapter<SpannableStringBuilder>(this, android.R.layout.simple_list_item_1, datas);
        listView.setAdapter(adapter);
    }
}
