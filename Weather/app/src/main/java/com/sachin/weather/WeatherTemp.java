package com.sachin.weather;

import android.content.Context;
import android.provider.Settings;
import android.util.AttributeSet;
import android.widget.TextView;

public class WeatherTemp extends TextView {
    private int temp;
    private String scale;
    private String TEMP_KEY = "aw_daemon_service_key_current_temp";
    private String SCALE_KEY = "aw_daemon_service_key_temp_scale";
    private int SCALE_TYPE = 0;
    private String currentTemp;

    public WeatherTemp(Context context, AttributeSet attrs) {
        super(context, attrs);
        updateView(context);
    }

    private void updateView(Context context) {

        temp = (int) Settings.System.getFloat(context.getContentResolver(), TEMP_KEY, 0.0F);
        SCALE_TYPE = Settings.System.getInt(context.getContentResolver(), SCALE_KEY, 0);
        currentTemp = String.valueOf(temp);
        if (SCALE_TYPE == 1){
            scale = "˚C";
        }else {
            scale = "˚F";
        }
        if (currentTemp == null){
            setVisibility(GONE);
        }else {
            setVisibility(VISIBLE);
            setText(currentTemp+scale);
        }
    }

    @Override
    public void onAttachedToWindow(){
        super.onAttachedToWindow();
        updateView(getContext());
    }
}
