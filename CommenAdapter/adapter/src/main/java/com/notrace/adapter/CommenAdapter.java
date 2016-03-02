package com.notrace.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.notrace.builders.BindableLayoutBuilder;
import com.notrace.builders.DefaultBindableLayoutBuilder;
import com.notrace.utils.Mapper;
import com.notrace.utils.ThreadHelper;
import com.notrace.utils.ViewEventListener;
import com.notrace.views.IBindableLayout;

import java.util.List;

/**
 * Created by notrace on 2016/3/1.
 */
public class CommenAdapter extends BaseAdapter implements IBasicAdapter {

    protected Mapper mapper;
    protected  List listItems;
    protected ViewEventListener viewEventListener;
    protected BindableLayoutBuilder builder;
    protected boolean autoDataSetChanged=true;

    public CommenAdapter(Mapper mapper,List listItems)
    {
        this(mapper,listItems,createDefaultBuilder());

    }
    public CommenAdapter(Mapper mapper,List listItems,BindableLayoutBuilder builder){
        this.listItems=listItems;
        this.mapper=mapper;
        if(builder==null){
            this.builder=createDefaultBuilder();
        }else {
            this.builder=builder;
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (listItems == null) {
            return 0;
        }
        Object object = getItem(position);
        return Mapper.viewTypeFromViewClass(builder.viewType(object, position, mapper));

    }

    @Override
    public int getViewTypeCount() {
        return mapper.viewSize();
    }

    @Override
    public int getCount() {
        return listItems == null ? 0 : listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems == null ? null : listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = builder.build(parent, getItemViewType(position), getItem(position), mapper);
        }

        if (view != null) {
            IBindableLayout bindableLayout = (IBindableLayout) view;
            bindableLayout.setViewEventListener(viewEventListener);
            bindableLayout.bind(getItem(position), position);
        }
        return view;
    }

    @Override
    public void setItems(List items) {
        ThreadHelper.crashIfBackgroundThread();
        listItems = items;
        if (autoDataSetChanged) {
            notifyDataSetChanged();
        }
    }

    @Override
    public void addItem(Object item) {
        ThreadHelper.crashIfBackgroundThread();
        listItems.add(item);
        if (autoDataSetChanged) {
            notifyDataSetChanged();
        }
    }

    @Override
    public void delItem(Object item) {
        ThreadHelper.crashIfBackgroundThread();
        listItems.remove(item);
        if (autoDataSetChanged) {
            notifyDataSetChanged();
        }
    }

    @Override
    public void addItems(List items) {
        ThreadHelper.crashIfBackgroundThread();
        listItems.addAll(items);
        if (autoDataSetChanged) {
            notifyDataSetChanged();
        }
    }

    @Override
    public void clearItems() {
        ThreadHelper.crashIfBackgroundThread();
        listItems.clear();
        if (autoDataSetChanged) {
            notifyDataSetChanged();
        }
    }

    @Override
    public ViewEventListener getViewEventListener() {
        return viewEventListener;
    }

    @Override
    public void setViewEventListener(ViewEventListener viewEventListener) {
        this.viewEventListener = viewEventListener;
    }

    @Override
    public void notifyAction(int actionId, Object object, int position, View view) {
        if (viewEventListener != null) {
            viewEventListener.onViewEvent(actionId, object, position, view);
        }
    }

    @Override
    public void setAutoDataSetChanged(boolean enabled) {
        this.autoDataSetChanged = enabled;
    }

    private static BindableLayoutBuilder createDefaultBuilder() {
        return new DefaultBindableLayoutBuilder();
    }
}
