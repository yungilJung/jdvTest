package com.example.gili.data;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.gili.jdvtest.R;

/**
 * Created by gili on 2018-03-08.
 */

public class FourFragment extends PreferenceFragment {
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addPreferencesFromResource(R.xml.settings_preference);
    }
}
