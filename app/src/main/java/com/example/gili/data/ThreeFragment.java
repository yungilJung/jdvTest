package com.example.gili.data;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gili.jdvtest.R;

/**
 * Created by gili on 2018-03-08.
 */

public class ThreeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentlayout, container, false);
       // return super.onCreateView(inflater, container, savedInstanceState);
    }
}
