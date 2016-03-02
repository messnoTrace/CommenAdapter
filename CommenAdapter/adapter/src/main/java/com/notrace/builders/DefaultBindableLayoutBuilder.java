package com.notrace.builders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.notrace.utils.Mapper;
import com.notrace.utils.Reflections;
import com.notrace.views.IBindableLayout;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * Created by notrace on 2016/3/1.
 */
public class DefaultBindableLayoutBuilder implements BindableLayoutBuilder {


    @Override
    public View build(@NonNull ViewGroup parent, int viewType, Object item, @NonNull Mapper mapper) {
        Class<? extends IBindableLayout> viewClass=mapper.viewClassFromViewType(viewType);
        if(viewClass ==null){
            throw  new IllegalArgumentException("viewType not present int mapper");
        }
        try {
            Constructor constructor= Reflections.constructor(viewClass, Context.class);
            return (ViewGroup) constructor.newInstance(parent.getContext());

        }catch (Exception e)
        {
            throw new RuntimeException("please review IBindableLayout iterface",e);
        }

    }


    @Override
    public Class<? extends IBindableLayout> viewType(@NonNull Object item, int position, @NonNull Mapper mapper) {

        List<Class<? extends IBindableLayout>> classes = mapper.get(item.getClass());
        if (classes == null) {
            throw new IllegalArgumentException("Object class " + item.getClass() + "not found in mapper");
        }
        if (classes.size() == 1) {
            return classes.get(0);
        } else if (classes.size() > 1) {
            throw new RuntimeException("There are more than 1 view classes associated to the same object class. Please write SmartAdapter custom BindableLayoutBuilder for this case, and ensure allowsMultimapping returns true.");
        } else {
            throw new RuntimeException("There are no view classes associated to the object class.");
        }
    }

    @Override
    public boolean allowsMultimapping() {
        return false;
    }
}
