package com.example.gili.jdvtest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;

/**
 * Created by gili on 2018-03-05.
 */

public class SettingPreferenceFragment extends PreferenceFragment {
    SharedPreferences prefs;

    ListPreference soundPreference;
    ListPreference keywordSoundPreference;
    PreferenceScreen keywordsScreen;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings_preference);

        soundPreference = (ListPreference)findPreference("sound_list");
        keywordSoundPreference = (ListPreference)findPreference("keyword_sound_list");
        keywordsScreen = (PreferenceScreen)findPreference("keyword_screen");

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if(!prefs.getString("sound_list","").equals("")){
            soundPreference.setSummary(prefs.getString("sound_list","카톡"));
        }

        if(!prefs.getString("keyword_sound_list","").equals("")){
            keywordSoundPreference.setSummary(prefs.getString("keyword_sound_list","카톡"));
        }

        if(prefs.getBoolean("keyword",false)){
            keywordsScreen.setSummary("사용");
        }
        else{
            keywordsScreen.setSummary("사용안함");
        }

        prefs.registerOnSharedPreferenceChangeListener(prefListener);
    }

    SharedPreferences.OnSharedPreferenceChangeListener prefListener =
            new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                    if(s.equals("sound_list")){
                        soundPreference.setSummary(prefs.getString("sound_list","카톡"));
                    }
                    if(s.equals("keyword_sound_list")){
                        keywordSoundPreference.setSummary(prefs.getString("keyword_sound_list","카톡"));
                    }
                    if(s.equals("keyword")){
                        if(prefs.getBoolean("keyword",false)){
                            keywordsScreen.setSummary("사용");
                        }
                        else{
                            keywordsScreen.setSummary("사용안함");
                        }
                    }

                }
            };
}
