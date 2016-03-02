package com.notrace.views;

import android.view.View;

import com.notrace.utils.ViewEventListener;

/**
 * Created by notrace on 2016/3/1.
 */
public interface IBindableLayout<T> {
    void bind(T item,int position);
    void bind(T item);
    void setViewEventListener(ViewEventListener<T> viewEventListener);
    ViewEventListener<T> getViewEventListener();
    void notifyItemAction(int actionId,T tehItem,View view);

}
