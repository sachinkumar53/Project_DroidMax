package com.sachin.weather;

import android.content.Context;
import android.provider.Settings;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class WeatherLayout extends LinearLayout {
    private int isVisible;

    public WeatherLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        isVisible = Settings.System.getInt(context.getContentResolver(),"weather",0);
        if (isVisible == 1){
            setVisibility(VISIBLE);
        }else{
            setVisibility(GONE);
        }
    }
}
