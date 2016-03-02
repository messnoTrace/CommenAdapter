package com.notrace.utils;

import android.view.View;

/**
 * Created by notrace on 2016/3/1.
 */
public interface ViewEventListener<T> {
    void onViewEvent(int actionId,T item,int position,View view);
}
