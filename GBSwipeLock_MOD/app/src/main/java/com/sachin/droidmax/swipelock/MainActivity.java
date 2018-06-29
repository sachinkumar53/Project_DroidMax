package com.sachin.droidmax.swipelock;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ViewSwitcher;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewSwitcher switcher = (ViewSwitcher)findViewById(R.id.viewS);
        switcher.getCurrentView().setVisibility(View.GONE);
        switcher.getNextView().setVisibility(View.VISIBLE);
    }
}
