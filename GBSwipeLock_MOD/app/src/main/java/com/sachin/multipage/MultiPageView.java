package com.sachin.multipage;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;

public class MultiPageView extends ViewGroup{

    LayoutParams params;

    public MultiPageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.getChildAt(0).setVisibility(GONE);
    }


    @Override
    public void onAttachedToWindow(){
        super.onAttachedToWindow();
        if (getChildCount() == 0){
            Log.d("No. of childs",""+0);
        }else {
            Log.d("No. of childs",""+getChildCount());
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
