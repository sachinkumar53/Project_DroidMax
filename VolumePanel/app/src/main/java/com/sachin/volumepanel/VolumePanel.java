package com.sachin.volumepanel;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.media.AudioManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

public class VolumePanel extends Activity {
    private AudioManager audioManager;

    private boolean isVibrationAllowed;

    private int curCall = 0;
    private int curMedia = 0;
    private int curRinger = 0;
    private int curNotif = 0;
    private int curSystem = 0;

    private SeekBar call;
    private SeekBar media;
    private SeekBar system;
    private SeekBar notif;
    private SeekBar ring;

    private ImageView expandVolumePanel;
    private ImageView callImg;
    private ImageView ringImg;
    private ImageView mediaImg;
    private ImageView notifImg;
    private ImageView systemImg;

    private LinearLayout mainLayout;
    private LinearLayout secondaryLayout;

    private View callLayout;

    public void onCreate(Bundle bundle) {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(bundle);
        setContentView(R.layout.volume_panel);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.TOP;

        init();
        if (audioManager.getMode() == AudioManager.MODE_IN_CALL){
            createInCallView();
            setInCallImages();
        }else{
            createView();
            setImages();
        }
    }

    public void init() {

        isVibrationAllowed = (Settings.System.getInt(getContentResolver(), Settings.System.VIBRATE_IN_SILENT, 1) == 1);

        mainLayout = (LinearLayout)findViewById(R.id.main_layout);
        callLayout = getLayoutInflater().inflate(R.layout.volume_panel_in_call,null);

        secondaryLayout = (LinearLayout) findViewById(R.id.secondary_layout);

        //initialize imageViews
        expandVolumePanel = (ImageView) findViewById(R.id.panel_expand);
        callImg = (ImageView)callLayout.findViewById(R.id.ring_img_in_call);
        ringImg = (ImageView) findViewById(R.id.ring_img);
        mediaImg = (ImageView) findViewById(R.id.media_img);
        notifImg = (ImageView) findViewById(R.id.notif_img);
        systemImg = (ImageView) findViewById(R.id.sys_img);

        //initialize seek bars
        call = (SeekBar)callLayout.findViewById(R.id.volume_seek_ringer_in_call);
        media = (SeekBar) findViewById(R.id.volume_seek_media);
        ring = (SeekBar) findViewById(R.id.volume_seek_ringer);
        system = (SeekBar) findViewById(R.id.volume_seek_system);
        notif = (SeekBar) findViewById(R.id.volume_seek_notif);

        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        curCall = audioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL);
        curMedia = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        curRinger = audioManager.getStreamVolume(AudioManager.STREAM_RING);
        curNotif = audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
        curSystem = audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
    }

    public void createView() {

        expandVolumePanel.setImageResource(R.drawable.ic_volume_panel_extend);
        expandVolumePanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandVolumePanel.setVisibility(View.GONE);
                secondaryLayout.setVisibility(View.VISIBLE);
            }
        });

        //set seek max
        ring.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_RING));
        media.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        notif.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION));
        system.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM));

        //set seek progress
        ring.setProgress(curRinger);
        media.setProgress(curMedia);
        notif.setProgress(curNotif);
        system.setProgress(curSystem);

        //set onSeekProgressChangeListener
        ring.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_RING, i, 0);
                while (i == 0){
                    if (isVibrationAllowed){

                    }
                }
                curRinger = i;
                if (audioManager.getMode() == AudioManager.MODE_IN_CALL){
                    setInCallImages();
                }else {
                    setImages();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        media.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);
                curMedia = i;
                if (audioManager.getMode() == AudioManager.MODE_IN_CALL){
                    setInCallImages();
                }else {
                    setImages();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        notif.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, i, 0);
                curNotif = i;
                if (audioManager.getMode() == AudioManager.MODE_IN_CALL){
                    setInCallImages();
                }else {
                    setImages();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        system.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, i, 0);
                curSystem = i;
                if (audioManager.getMode() == AudioManager.MODE_IN_CALL){
                    setInCallImages();
                }else {
                    setImages();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void setImages() {
        if (audioManager.getMode() == AudioManager.RINGER_MODE_VIBRATE){
            ringImg.setImageResource(R.drawable.ic_volume_vibrate);
        }
        if (curRinger == 0) {
            if (isVibrationAllowed){
                ringImg.setImageResource(R.drawable.ic_volume_vibrate);
            }else {
                ringImg.setImageResource(R.drawable.ic_volume_ringer_off);
            }
        }else{
            ringImg.setImageResource(R.drawable.ic_volume_ringer);
        }

        if (curMedia == 0) {
            mediaImg.setImageResource(R.drawable.ic_volume_media_off);
        } else {
            mediaImg.setImageResource(R.drawable.ic_volume_media);
        }

        if (curNotif == 0) {
            notifImg.setImageResource(R.drawable.ic_volume_notif_off);
        } else {
            notifImg.setImageResource(R.drawable.ic_volume_notif);
        }

        if (curSystem == 0) {
            systemImg.setImageResource(R.drawable.ic_volume_system_off);
        } else {
            systemImg.setImageResource(R.drawable.ic_volume_system);
        }

    }

    public void createInCallView() {
        expandVolumePanel.setImageResource(R.drawable.ic_volume_panel_extend);
        expandVolumePanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandVolumePanel.setVisibility(View.GONE);
                secondaryLayout.setVisibility(View.VISIBLE);
            }
        });

        //set images
        setInCallImages();

        //set seek max
        ring.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL));
        call.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_RING));
        media.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        notif.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION));
        system.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM));

        //set seek progress
        ring.setProgress(curCall);
        call.setProgress(curRinger);
        media.setProgress(curMedia);
        notif.setProgress(curNotif);
        system.setProgress(curSystem);

        ring.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, i, 0);
                curCall = i;
                if (audioManager.getMode() == AudioManager.MODE_IN_CALL) {
                    setInCallImages();
                } else {
                    setImages();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        call.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_RING,i,0);
                while (i == 0){
                    if (isVibrationAllowed){
                        audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                    }
                }
                curRinger = i;
                if (audioManager.getMode() == AudioManager.MODE_IN_CALL){
                    setInCallImages();
                }else {
                    setImages();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}});

        media.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,i,0);
                curMedia = i;
                if (audioManager.getMode() == AudioManager.MODE_IN_CALL){
                    setInCallImages();
                }else {
                    setImages();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        notif.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION,i,0);
                curNotif = i;
                if (audioManager.getMode() == AudioManager.MODE_IN_CALL){
                    setInCallImages();
                }else {
                    setImages();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        system.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM,i,0);
                curSystem = i;
                if (audioManager.getMode() == AudioManager.MODE_IN_CALL){
                    setInCallImages();
                }else {
                    setImages();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //addviews
        mainLayout.addView(callLayout, 1);
    }

    private void setInCallImages() {
        ringImg.setImageResource(R.drawable.ic_volume_call);

        if (curRinger == 0) {
            if (isVibrationAllowed) {
                callImg.setImageResource(R.drawable.ic_volume_vibrate);
            } else {
                callImg.setImageResource(R.drawable.ic_volume_ringer_off);
            }
        } else {
            callImg.setImageResource(R.drawable.ic_volume_ringer);
        }

        if (curMedia == 0) {
            mediaImg.setImageResource(R.drawable.ic_volume_media_off);
        } else {
            mediaImg.setImageResource(R.drawable.ic_volume_media);
        }

        if (curNotif == 0) {
            notifImg.setImageResource(R.drawable.ic_volume_notif_off);
        } else {
            notifImg.setImageResource(R.drawable.ic_volume_notif);
        }

        if (curSystem == 0) {
            systemImg.setImageResource(R.drawable.ic_volume_system_off);
        } else {
            systemImg.setImageResource(R.drawable.ic_volume_system);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Rect dialogBounds = new Rect();
        getWindow().getDecorView().getHitRect(dialogBounds);

        if (!dialogBounds.contains((int) event.getX(), (int) event.getY()) && event.getAction() == MotionEvent.ACTION_DOWN) {
            finish();
        }
        return super.dispatchTouchEvent(event);
    }

    private void updateSeekProgress(int i) {
        int index;
        if (audioManager.getMode() == AudioManager.MODE_IN_CALL){
            index = ring.getProgress();
            ring.setProgress(index+i);
            setInCallImages();
            curCall = index +i;
        }else if (audioManager.isMusicActive()){
            index = media.getProgress();
            media.setProgress(index+i);
            curMedia = index+i;
        }else {
            index = ring.getProgress();
            ring.setProgress(index+i);
            setImages();
            curRinger = index+i;
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode,KeyEvent keyEvent){
        super.onKeyDown(keyCode, keyEvent);
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP){
            updateSeekProgress(+1);
        }else if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
            updateSeekProgress(-1);
        }
        return false;
    }
}
