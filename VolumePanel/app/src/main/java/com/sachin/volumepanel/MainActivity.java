package com.sachin.volumepanel;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

public class MainActivity extends Activity {
    AudioManager audioManager;
    int maxSystemVolume,maxCallVolume,maxNotificationVolume,maxMediaVolume;
    int curCallVolume,curNotificationVolume,curMediaVolume,curSystemVolume;
    LinearLayout mainLayout,layout,layout1,layout2,layout3;
    LinearLayout linearLayout,linearLayout1,linearLayout2;
    SeekBar volRing, volMedia, volNotifcation, volSystem, volMusic, volCall;
    ImageView imageView,imageView1,imageView2;
    BroadcastReceiver receiver;
    IntentFilter filter;
    ImageView callImage,mediaImage,systemImage,notifImage,ringImage;
    int callVol,sysVol,ringVol,mediaVol,notifVol;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams volumeSliderDialog = getWindow().getAttributes();
        volumeSliderDialog.gravity = Gravity.TOP | Gravity.CENTER;
        setContentView(getLayout("layout/volume_aryan"));
        //initialize
        callVol = 0;
        sysVol =0;
        ringVol = 0;
        mediaVol = 0;
        notifVol = 0;

        mainLayout = (LinearLayout)findViewById(getLayout("id/mainlayout"));

        //Main Volume SeekBars
        volRing = (SeekBar) findViewById(getLayout("id/volume_seek_ringer"));
        volMedia = (SeekBar) findViewById(getLayout("id/volume_seek_media"));
        volNotifcation = (SeekBar) findViewById(getLayout("id/volume_seek_notif"));
        volSystem = (SeekBar) findViewById(getLayout("id/volume_seek_system"));

        //Conditional SeekBars
        volMusic = (SeekBar) findViewById(getLayout("id/volume_music"));
        volCall = (SeekBar) findViewById(getLayout("id/call_seek"));

        //main layouts
        layout = (LinearLayout) findViewById(getLayout("id/ringer_layout"));
        layout1 = (LinearLayout) findViewById(getLayout("id/media_layout"));
        layout2 = (LinearLayout) findViewById(getLayout("id/notify_layout"));
        layout3 = (LinearLayout) findViewById(getLayout("id/system_layout"));

        //hide all on create
        layout1.setVisibility(View.GONE);
        layout2.setVisibility(View.GONE);
        layout3.setVisibility(View.GONE);

        //conditional layouts
        linearLayout = (LinearLayout) findViewById(getLayout("id/muisc_layout"));
        linearLayout1 = (LinearLayout) findViewById(getLayout("id/call_layout"));
        linearLayout2 = (LinearLayout) findViewById(getLayout("id/media_layout"));

        //Extend buttons
        imageView = (ImageView) findViewById(getLayout("id/extend_main"));
        imageView1 = (ImageView) findViewById(getLayout("id/extend_media"));
        imageView2 = (ImageView) findViewById(getLayout("id/extend_call"));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setVisibility(View.GONE);
                layout1.setVisibility(View.VISIBLE);
                layout2.setVisibility(View.VISIBLE);
                layout3.setVisibility(View.VISIBLE);
            }
        });

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView1.setVisibility(View.GONE);
                layout.setVisibility(View.VISIBLE);
                layout2.setVisibility(View.VISIBLE);
                layout3.setVisibility(View.VISIBLE);
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView2.setVisibility(View.GONE);
                layout.setVisibility(View.VISIBLE);
                layout1.setVisibility(View.VISIBLE);
                layout2.setVisibility(View.VISIBLE);
                layout3.setVisibility(View.VISIBLE);
            }
        });

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        initImages();

        if (audioManager.isMusicActive()) {
            imageView.setVisibility(View.GONE);
            imageView2.setVisibility(View.GONE);
            layout.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
            linearLayout2.setVisibility(View.GONE);
        } else {
            linearLayout.setVisibility(View.GONE);
        }

        if (audioManager.getMode() == audioManager.MODE_IN_CALL) {
            linearLayout1.setVisibility(View.VISIBLE);
            layout.setVisibility(View.GONE);
            imageView1.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
        } else {
            linearLayout1.setVisibility(View.GONE);
        }

        maxCallVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);
        curCallVolume = audioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL);
        volCall.setMax(maxCallVolume);
        volCall.setProgress(curCallVolume);

        volCall.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, progress, 0);
                callVol = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        int maxRingVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
        final int curRingVolume = audioManager.getStreamVolume(AudioManager.STREAM_RING);
        volRing.setMax(maxRingVolume);
        volRing.setProgress(curRingVolume);
        volRing.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                audioManager.setStreamVolume(AudioManager.STREAM_RING, arg1, 0);
                ringVol = arg1;
            }
        });

        maxNotificationVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION);
        curNotificationVolume = audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
        volNotifcation.setMax(maxNotificationVolume);
        volNotifcation.setProgress(curNotificationVolume);
        volNotifcation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, arg1, 0);
                notifVol = arg1;
            }
        });

        maxMediaVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        curMediaVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        volMedia.setMax(maxMediaVolume);
        volMedia.setProgress(curMediaVolume);
        volMedia.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, arg1, 0);
                mediaVol = arg1;
            }
        });

        volMusic.setMax(maxMediaVolume);
        volMusic.setProgress(curMediaVolume);
        volMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, arg1, 0);
                mediaVol = arg1;
            }
        });

        maxSystemVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
        curSystemVolume = audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
        volSystem.setMax(maxSystemVolume);
        volSystem.setProgress(curSystemVolume);
        volSystem.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, arg1, 0);
                sysVol = arg1;
                if(arg1 == 0){
                    systemImage.setImageResource(getLayout("drawable/ic_audio_ring_notif_mute"));
                }
            }
        });

        filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                finish();
            }
        };
        registerReceiver(receiver,filter);

    }

    private void initImages() {
        ringImage = (ImageView)findViewById(getLayout("id/ring_img"));
        mediaImage = (ImageView)findViewById(getLayout("id/media_img"));
        systemImage = (ImageView)findViewById(getLayout("id/sys_img"));
        notifImage = (ImageView)findViewById(getLayout("id/notif_img"));
        callImage = (ImageView)findViewById(getLayout("id/call_img"));

    }

    public int getLayout(String name){
        int resourceId = getResources().getIdentifier(name, null, getPackageName());
        return resourceId;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Rect dialogBounds = new Rect();
        getWindow().getDecorView().getHitRect(dialogBounds);

        if (!dialogBounds.contains((int) event.getX(), (int) event.getY()) && event.getAction() == MotionEvent.ACTION_DOWN) {
            this.finish();
        }
        return super.dispatchTouchEvent(event);
    }

}