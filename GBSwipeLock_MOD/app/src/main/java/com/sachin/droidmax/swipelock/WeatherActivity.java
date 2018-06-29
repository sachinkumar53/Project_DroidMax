package com.sachin.droidmax.swipelock;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class WeatherActivity extends Activity {
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.weather_layout);

        String condition = Settings.System.getString(getContentResolver(), "aw_daemon_service_key_weather_text");
        int SCALE = Settings.System.getInt(getContentResolver(), "aw_daemon_service_key_temp_scale", 0);
        String realScale;
        if (SCALE == 1) {
            realScale = "˚C";
        } else {
            realScale = "˚F";
        }
        imageView = (ImageView) findViewById(R.id.imageView);
        float curTemp = Settings.System.getFloat(getContentResolver(), "aw_daemon_service_key_current_temp", 0f);
        int Temp = (int) curTemp;
        int img = Settings.System.getInt(getContentResolver(), "aw_daemon_service_key_icon_num", 0);

        int resourceId = getResources().getIdentifier("drawable/icon_" + findDrawableId(img), null, getPackageName());
        Drawable weatherIconDrawable = getResources().getDrawable(resourceId);
        imageView.setImageDrawable(weatherIconDrawable);
        String cityName = Settings.System.getString(getContentResolver(), "aw_daemon_service_key_city_name");
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(condition);
        TextView textView1 = (TextView) findViewById(R.id.textView2);
        textView1.setText(cityName);
        TextView textView2 = (TextView) findViewById(R.id.textView3);
        textView2.setText("  " + Temp + realScale + "  " + img);

        int bool = Settings.System.getInt(getContentResolver(), "aw_daemon_service_key_app_service_status", 0);
        String str = Settings.System.getString(getContentResolver(), "aw_daemon_service_key_loc_code");
        Log.d("Service", "" + bool + str);
    }

    private int findDrawableId(int paramInt) {
        switch (paramInt) {
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
}
