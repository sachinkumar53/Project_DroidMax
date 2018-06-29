package com.sachin.weather;

import android.content.Context;
import android.provider.Settings;
import android.util.AttributeSet;
import android.widget.TextView;

public class CityName extends TextView {
    String cityName;
    String CITY_KEY = "aw_daemon_service_key_city_name";

    public CityName(Context context, AttributeSet attrs) {
        super(context, attrs);
        upadteView();
    }

    private void upadteView() {
        cityName = Settings.System.getString(getContext().getContentResolver(),CITY_KEY);
        setText(cityName);
    }

    @Override
    public void onAttachedToWindow(){
        super.onAttachedToWindow();
        upadteView();
    }
}
