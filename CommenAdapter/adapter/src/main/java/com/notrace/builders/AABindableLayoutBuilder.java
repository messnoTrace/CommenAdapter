package com.notrace.builders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.notrace.utils.Mapper;
import com.notrace.utils.Reflections;
import com.notrace.views.IBindableLayout;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by notrace on 2016/3/1.
 */
public class AABindableLayoutBuilder extends DefaultBindableLayoutBuilder{
    @Override
    public View build(@NonNull ViewGroup parent, int viewType, Object item, @NonNull Mapper mapper) {
        Class<? extends IBindableLayout> viewClass = mapper.viewClassFromViewType(viewType);
        if (viewClass == null)
        {
            throw new IllegalArgumentException("viewType not present in the mapper");
        }
        try {
            Method method = Reflections.method(viewClass, "build", Context.class);
            return (ViewGroup) method.invoke(null, parent.getContext());
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("View class is not SmartAdapter @EViewGroup generated by AndroidAnnotations", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Invocation Target", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Illegal Access ", e);
        }
    }
}