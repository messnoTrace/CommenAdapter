package com.notrace.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.notrace.utils.ViewEventListener;

/**
 * Created by notrace on 2016/3/1.
 */
public class BindableViewHolder<T> extends RecyclerView.ViewHolder {
    private IBindableLayout<T>bindableLayout;
    public BindableViewHolder(IBindableLayout<T>itemView)
    {
        super((View)itemView);
        bindableLayout=itemView;
    }

    public void bind(T t,int position){
        bindableLayout.bind(t,position);
    }
    public void setViewEventListener(ViewEventListener<T> listener){
        bindableLayout.setViewEventListener(listener);
    }
}
