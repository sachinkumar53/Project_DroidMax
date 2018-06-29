package com.sachin.weather;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.provider.Settings;

public class WeatherSettings extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.droid_max_weather);
        final CheckBoxPreference checkBoxPreference = (CheckBoxPreference)findPreference(getString(R.string.weather_key));
        checkBoxPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if (checkBoxPreference.isChecked()){
                    Settings.System.putInt(getContentResolver(),"weather",1);
                }else {
                    Settings.System.putInt(getContentResolver(),"weather",0);
                }
                return true;
            }
        });
    }
}