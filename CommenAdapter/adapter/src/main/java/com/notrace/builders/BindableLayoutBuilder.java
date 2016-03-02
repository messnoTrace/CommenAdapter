package com.notrace.builders;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.notrace.utils.Mapper;
import com.notrace.views.IBindableLayout;

/**
 * Created by notrace on 2016/3/1.
 */
public interface BindableLayoutBuilder<T> {
    <Q extends View & IBindableLayout> Q build(@NonNull ViewGroup parent,int viewType,T item,@NonNull Mapper mapper);

    Class<? extends IBindableLayout>viewType(@NonNull T item,int position,@NonNull Mapper mapper);
    boolean allowsMultimapping();
}
