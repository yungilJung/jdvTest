package com.example.gili.jdvtest;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.example.gili.comutil.CommonUtils;

public class SpannableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spannable);


        String data = "복수초 \n 이른봄 설산에...";
        SpannableStringBuilder builder = new SpannableStringBuilder(data);
        int start = data.indexOf("복수초");
        if(start>-1){
            int end=start+"복수초".length();
            StyleSpan style = new StyleSpan(Typeface.BOLD);
            builder.setSpan(style, start, end+2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        TextView tv = (TextView)findViewById(R.id.txtSpan);
        tv.setText(builder);


        String html = "<font color='RED'>엘레지</font> <br> 테스트 ";
        TextView tvHtml = (TextView)findViewById(R.id.txtHtml);
        tvHtml.setText(Html.fromHtml(html));

        Resources res = getResources();
        String[] KnownColors = res.getStringArray(R.array.array_voice);

        ArrayAdapter<String> adaptor = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, KnownColors);

        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.actvTest);
        autoCompleteTextView.setAdapter(adaptor);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setDropDownWidth(300);
    }
}
