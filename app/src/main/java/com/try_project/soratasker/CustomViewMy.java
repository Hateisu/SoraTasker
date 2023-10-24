package com.try_project.soratasker;

import android.content.Context;
import android.widget.RelativeLayout;

import androidx.cardview.widget.CardView;

public class CustomViewMy extends CardView {

    @Override
    public void setScrollBarStyle(int style) {
        super.setScrollBarStyle(style);
    }

    public CustomViewMy(Context context) {
        super(context, null, R.style.CardViewScrollerStyle);
    }
}
