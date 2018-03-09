package com.example.gili.data;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;

/**
 * Created by gili on 2018-03-08.
 */

public class TwoFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        return super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Dialog Fragments");
        builder.setMessage("Dialog .....");
        builder.setPositiveButton("OK",null);
        AlertDialog dialog = builder.create();
        return  dialog;
    }

}
