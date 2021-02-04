package com.example.citizens.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.viewpager.widget.ViewPager;

import java.util.jar.Attributes;

public class MyViewPager extends ViewPager {

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
//        setPageTransformer(true, new DefaultTransformer());
    }

    @Override
    public boolean canScrollHorizontally(int direction) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent me) {
//        boolean intercept = super.onInterceptTouchEvent(swapEvent(me));
//        swapEvent(me);
        boolean intercept = super.onInterceptTouchEvent(me);
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent me) {
//        return super.onTouchEvent(swapEvent(me));
        return super.onTouchEvent(me);
    }

    private MotionEvent swapEvent(MotionEvent me) {
        float width = getWidth();
        float height = getHeight();
        float swappedX = (me.getY() / height) * width;
        float swappedY = (me.getX() / width) * height;
        me.setLocation(swappedX, swappedY);
        return me;
    }

    class DefaultTransformer implements ViewPager.PageTransformer {
        public static final String TAG = "simple";
        @Override
        public void transformPage(@NonNull View page, float position) {
            float transX = page.getWidth() * -position;
            page.setTranslationX(transX);
            float transY = page.getHeight() * position;
            page.setTranslationY(transY);
        }
    }
}
