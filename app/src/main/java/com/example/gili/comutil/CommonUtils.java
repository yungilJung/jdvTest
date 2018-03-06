package com.example.gili.comutil;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by gili on 2018-03-05.
 */

public class CommonUtils {

    public static TextView setAwesomeTextView(Activity av, int id){
        TextView tv = (TextView)av.findViewById(id);
        Typeface tf = Typeface.createFromAsset(av.getAssets(), "fa.ttf");
        tv.setTypeface(tf);
        return tv;
    }

    public static EditText getEditText(Activity av, int id){
        return (EditText)av.findViewById(id);
    }


    public static void setTextView(Activity av, int id, String value){
        ((TextView)av.findViewById(id)).setText(value);
    }

    public  static void showLongMessage(Activity av, String message){
        Toast.makeText(av, message, Toast.LENGTH_LONG).show();
    }

    public  static boolean checkEmptyValidation(EditText txt, String message){
        if(txt.getText().toString().equals("")){
            showLongMessage((Activity)txt.getParent(), message);
            return  false;
        }
        return true;
    }
}
