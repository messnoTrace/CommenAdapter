package com.notrace;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;

import com.notrace.adapter.CommenAdapter;
import com.notrace.adapter.RecyclerAdapter;
import com.notrace.builders.BindableLayoutBuilder;
import com.notrace.utils.Mapper;
import com.notrace.utils.ViewEventListener;
import com.notrace.views.IBindableLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by notrace on 2016/3/1.
 */
public class SmartAdapter {
    private SmartAdapter() {

    }

    /**
     * Include the object list that is going to be represented in our collection view.
     *
     * @param items model list to be displayed in the listing
     * @return fluid interface for more settings
     */
    public static MultiAdaptersCreator items(@NonNull List<?> items) {
        return new MultiAdaptersCreator(items);
    }

    /**
     * Initiates the fluid chain without any models to be represented. You should include them in
     * further calls to the adapter with the addItems method.
     *
     * @return fluid interface for more settings
     */
    public static MultiAdaptersCreator empty() {
        return new MultiAdaptersCreator(new ArrayList<>());
    }

    public static class MultiAdaptersCreator {
        private Mapper mapper;
        private List elements;
        private BindableLayoutBuilder builder;
        private ViewEventListener listener;

        public MultiAdaptersCreator(List<?> elements) {
            this.mapper = new Mapper();
            this.elements = elements;
        }

        /**
         * Binds the models to their views. The views should inherit from BindableLayout, so their
         * bind methods assign the values from the model to the view widgets.
         *
         * @param objectClass Class of the object (model) class
         * @param viewClass   Class of the view (layout) class
         * @return fluid interface for more settings
         */
        public MultiAdaptersCreator map(
                @NonNull Class objectClass, @NonNull Class<? extends IBindableLayout> viewClass) {
            mapper.add(objectClass, viewClass);
            return this;
        }

        /**
         * Changes the internal Mapper instance to a new one. The mapper is an object with all the
         * mappings between objects and views. This will overwrite all the @see #map(Class, Class)
         * calls done so far, so beware of that.
         *
         * @param mapper mappings for objects to views
         * @return fluid interface for more settings
         */
        @VisibleForTesting
        MultiAdaptersCreator mapper(@NonNull Mapper mapper) {
            this.mapper = mapper;
            return this;
        }

        /**
         * Sets a new builder different from the default one @see io.nlopez.smartadapters.builders.DefaultBindableLayoutBuilder
         * so the views can be created based on the models with your own actions.
         *
         * @param builder new builder instance
         * @return fluid interface for more settings
         */
        public MultiAdaptersCreator builder(BindableLayoutBuilder builder) {
            this.builder = builder;
            return this;
        }

        /**
         * Sets a new listener for all the collection related events. All the events fired within
         * the views with the proper notifyItemAction calls will get to this listener.
         *
         * @param listener callback for actions control
         * @return fluid interface for more settings
         */
        public MultiAdaptersCreator listener(@NonNull ViewEventListener listener) {
            this.listener = listener;
            return this;
        }

        /**
         * Returns the instantiated adapter for AbsListView inherited widgets (ListView, GridView)
         * created with all the parameters already set. It is based on BaseAdapter adapters.
         *
         * @return adapter for {@code AbsListView} based on {@code BaseAdapter}
         */
        public CommenAdapter adapter() {
            CommenAdapter response = new CommenAdapter(mapper, elements, builder);
            response.setViewEventListener(listener);
            return response;
        }

        /**
         * Returns the instantiated adapter for RecyclerView
         *
         * @return adapter based on {@code RecyclerView.Adapter}
         */
        public RecyclerAdapter recyclerAdapter() {
            RecyclerAdapter response = new RecyclerAdapter(mapper, elements, builder);
            response.setViewEventListener(listener);
            return response;
        }

        /**
         * Assigns the created adapter to the given {@code AbsListView} inherited widget (ListView, GridView).
         *
         * @param widget ListView, GridView, ie any widget inheriting from {@code AbsListView}
         * @return assigned adapter
         */
        public CommenAdapter into(@NonNull AbsListView widget) {
            validateMapper();
            CommenAdapter adapter = adapter();
            widget.setAdapter(adapter);
            return adapter;
        }

        /**
         * Assigns the created adapter to the given {@code RecyclerView}.
         *
         * @param recyclerView instance of RecyclerView
         * @return assigned adapter
         */
        public RecyclerAdapter into(@NonNull RecyclerView recyclerView) {
            validateMapper();
            RecyclerAdapter adapter = recyclerAdapter();
            recyclerView.setAdapter(adapter);
            return adapter;
        }

        private void validateMapper() {
            if (builder != null && builder.allowsMultimapping()) {
                return;
            }

            // We want to check if multimapping is disabled and we are using more than 1 view per object
            // No builder = allowsMultimapping->FALSE
            for (Class clazz : mapper.objectClasses()) {
                List<Class<? extends IBindableLayout>> viewClasses = mapper.get(clazz);
                if (viewClasses.size() > 1) {
                    throw new IllegalArgumentException("Object class " + clazz + " bound to more than 1 view class. You need to use a custom BindableLayoutBuilder that allows multimapping.");
                }
            }
        }
    }
}
