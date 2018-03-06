package com.example.gili.comutil;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by gili on 2018-03-05.
 */

public class FontAwesome extends android.support.v7.widget.AppCompatTextView {
    public FontAwesome(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        init();
    }

    public FontAwesome(Context context) {
        super(context);
        init();
    }

    public FontAwesome(Context context, AttributeSet attrs){
        super(context, attrs);
        init();
    }

    private void init(){
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "/fa.ttf");
        setTypeface(tf);
    }


}
