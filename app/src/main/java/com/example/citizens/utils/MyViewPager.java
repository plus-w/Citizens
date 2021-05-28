package com.example.citizens.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.viewpager.widget.ViewPager;

import java.util.jar.Attributes;

public class MyViewPager extends ViewPager {

    private boolean isPagingEnabled = false;

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
//        setPageTransformer(true, new DefaultTransformer());
    }

//    @Override
//    public boolean canScrollHorizontally(int direction) {
//        return false;
//    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent me) {
//        boolean intercept = super.onInterceptTouchEvent(swapEvent(me));
//        swapEvent(me);
        return isPagingEnabled && super.onInterceptTouchEvent(me);
    }

    @Override
    public boolean onTouchEvent(MotionEvent me) {
//        return super.onTouchEvent(swapEvent(me));
        return isPagingEnabled && super.onTouchEvent(swapEvent(me));
    }

    @Override
    public boolean canScrollHorizontally(int direction) {
        return isPagingEnabled && super.canScrollHorizontally(direction);
    }

    @Override
    public boolean executeKeyEvent(KeyEvent event) {
        return isPagingEnabled && super.executeKeyEvent(event);
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
