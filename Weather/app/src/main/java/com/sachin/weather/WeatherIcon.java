package com.sachin.weather;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.util.AttributeSet;
import android.widget.ImageView;

public class WeatherIcon extends ImageView {
    private String ICON_KEY = "aw_daemon_service_key_icon_num";
    private int imgNum = 0;

    public WeatherIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
        imgNum = Settings.System.getInt(context.getContentResolver(),ICON_KEY, 0);
        setIcon();
    }

    private void setIcon() {
        int resourceId = getResources().getIdentifier("drawable/icon_" + findDrawableId(imgNum), null, getContext().getPackageName());
        Drawable weatherIconDrawable = getResources().getDrawable(resourceId);
        setImageDrawable(weatherIconDrawable);
    }

    private int findDrawableId(int img) {
        switch (img) {
            case 1:
            case 2:
            case 9:
            case 10:
            case 27:
            case 28:
            default:
                return 0;
            case 3:
            case 4:
            case 5:
                return 1;
            case 6:
            case 7:
            case 8:
                return 2;
            case 11:
                return 3;
            case 12:
            case 13:
            case 39:
            case 40:
                return 4;
            case 14:
                return 5;
            case 15:
            case 41:
            case 42:
                return 6;
            case 16:
            case 17:
                return 7;
            case 18:
                return 8;
            case 19:
            case 43:
                return 9;
            case 20:
            case 21:
                return 10;
            case 22:
            case 23:
            case 44:
                return 11;
            case 24:
            case 25:
            case 26:
                return 12;
            case 29:
                return 13;
            case 30:
                return 14;
            case 31:
                return 15;
            case 32:
                return 16;
            case 33:
                return 17;
            case 34:
            case 35:
            case 36:
            case 37:
                return 18;
        }
    }

    @Override
    public void onAttachedToWindow(){
        super.onAttachedToWindow();
        setIcon();
    }
}
