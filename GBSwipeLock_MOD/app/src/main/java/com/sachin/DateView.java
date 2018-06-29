package com.sachin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.AttributeSet;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateView extends TextView {
    Context mContext;
    IntentFilter filter;
    BroadcastReceiver receiver;

    public DateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDate();
        mContext = context;

        filter = new IntentFilter(Intent.ACTION_TIME_CHANGED);
        filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        filter.addAction(Intent.ACTION_TIME_TICK);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                setDate();
            }
        };
    }

    private void setDate() {
        setText(new SimpleDateFormat("E, MMM dd").format(Calendar.getInstance().getTime()));
    }

    @Override
    public void onAttachedToWindow(){
        super.onAttachedToWindow();
        mContext.registerReceiver(receiver,filter);
    }

    @Override
    public void onDetachedFromWindow(){
        super.onDetachedFromWindow();
        mContext.unregisterReceiver(receiver);
    }
}
