package com.sachin.multipage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class PageChangeButton extends Button {
    int currentPage = 1;
    int which = 0;
    LinearLayout.LayoutParams params;
    public PageChangeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        final MultiPageView multiPageView = new MultiPageView(context,attrs);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (which){
                    case 0:

                        which = 1;
                        break;
                    case 1:

                        which = 0;
                        break;
                }
            }
        });
    }
}
