package com.notrace.adapter;

import android.support.annotation.UiThread;
import android.view.View;

import com.notrace.utils.ViewEventListener;

import java.util.List;

/**
 * Created by notrace on 2016/3/1.
 */
public interface IBasicAdapter {
    @UiThread
    void setItems(List items);

    @UiThread
    void addItem(Object item);

    @UiThread
    void delItem(Object item);

    @UiThread
    void addItems(List items);

    @UiThread
    void clearItems();

    ViewEventListener getViewEventListener();

    void setViewEventListener(ViewEventListener viewEventListener);

    void notifyAction(int actionId, Object object, int position, View view);

    void setAutoDataSetChanged(boolean enabled);

}
