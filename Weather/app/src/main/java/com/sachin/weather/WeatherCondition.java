package com.sachin.weather;

import android.content.Context;
import android.provider.Settings;
import android.util.AttributeSet;
import android.widget.TextView;

public class WeatherCondition extends TextView {
    private String condition;
    private String KEY = "aw_daemon_service_key_weather_text";

    public WeatherCondition(Context context, AttributeSet attrs) {
        super(context, attrs);
        condition = Settings.System.getString(context.getContentResolver(),KEY);
        updateView();
    }

    private void updateView() {
        if (condition == null){
            setVisibility(GONE);
        }else {
            setVisibility(VISIBLE);
            setText(condition);
        }
    }

    @Override
    public void onAttachedToWindow(){
        super.onAttachedToWindow();
        updateView();
    }
}
