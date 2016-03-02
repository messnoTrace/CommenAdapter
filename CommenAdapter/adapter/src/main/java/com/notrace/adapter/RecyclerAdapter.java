package com.notrace.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.notrace.builders.BindableLayoutBuilder;
import com.notrace.builders.DefaultBindableLayoutBuilder;
import com.notrace.utils.Mapper;
import com.notrace.utils.ThreadHelper;
import com.notrace.utils.ViewEventListener;
import com.notrace.views.BindableViewHolder;
import com.notrace.views.IBindableLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by notrace on 2016/3/1.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IBasicAdapter {
    protected Mapper mapper;
    protected List<Class> itemClassArray;
    protected List listItems;
    protected ViewEventListener viewEventListener;
    protected BindableLayoutBuilder builder;
    private boolean autoDataSetChanged = true;
    public RecyclerAdapter(Mapper mapper, List listItems) {
        this(mapper, listItems, createDefaultBuilder());
    }

    public RecyclerAdapter(Mapper mapper, List listItems, BindableLayoutBuilder builder) {
        this.listItems = listItems;
        this.mapper = mapper;
        if (builder == null) {
            this.builder = createDefaultBuilder();
        } else {
            this.builder = builder;
        }
        this.itemClassArray = new ArrayList<>(mapper.objectClasses());
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = builder.build(parent, viewType, null, mapper);
        return new BindableViewHolder((IBindableLayout) view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BindableViewHolder bindableViewHolder = (BindableViewHolder) holder;
        bindableViewHolder.setViewEventListener(viewEventListener);
        Object item = listItems.get(position);
        if (item != null) {
            bindableViewHolder.bind(item, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (listItems == null) {
            return 0;
        }
        Object object = listItems.get(position);
        return Mapper.viewTypeFromViewClass(builder.viewType(object, position, mapper));
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getItemCount() {
        return listItems == null ? 0 : listItems.size();
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
