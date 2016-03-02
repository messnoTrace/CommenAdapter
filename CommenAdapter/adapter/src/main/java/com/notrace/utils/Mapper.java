package com.notrace.utils;

import android.support.annotation.VisibleForTesting;
import android.util.ArrayMap;

import com.notrace.views.IBindableLayout;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by notrace on 2016/3/1.
 */
public class Mapper {
    private Map<Class,List<Class<? extends IBindableLayout>>>mapping;
    private Set<Class<? extends IBindableLayout>> cachedViewClasses;

    private Map<Integer,Class<? extends IBindableLayout>>viewTypes;
    public Mapper()
    {
        mapping=new ArrayMap<>();
        viewTypes=new ArrayMap<>();
    }

    public Mapper(Map<Class,List<Class<? extends IBindableLayout>>>mockMapping,Map<Integer,Class<? extends IBindableLayout>>mockViewType){
        this.mapping=mockMapping;
        this.viewTypes=mockViewType;

    }



    public Mapper add(Class objectClass,Class<? extends IBindableLayout>viewClass)
    {
        if(mapping.containsKey(objectClass))
        {
            List<Class<? extends IBindableLayout>>classes=new ArrayList<>();
            classes.addAll(mapping.get(objectClass));
            classes.add(viewClass);
            mapping.put(objectClass,classes);
        }else {
            List<Class<? extends IBindableLayout>>list=new ArrayList<>();
            list.add(viewClass);
            mapping.put(objectClass,list);
        }
        viewTypes.put(viewTypeFromViewClass(viewClass), viewClass);
        clearCachedData();
        return this;
    }



    public boolean containsObjectClass(Class objectClass) {
        return mapping.containsKey(objectClass);
    }


    public boolean containsViewClass(Class<? extends IBindableLayout> viewClass) {
        return viewClasses().contains(viewClass);
    }


    public List<Class<? extends IBindableLayout>> get(Class objectClass) {
        return mapping.get(objectClass);
    }

    public int viewSize() {
        return viewClasses().size();
    }

    public int objectSize() {
        return objectClasses().size();
    }

    public Set<Class> objectClasses() {
        return mapping.keySet();
    }

    public Set<Class<? extends IBindableLayout>>viewClasses()
    {
        if (cachedViewClasses == null) {
            cachedViewClasses = new LinkedHashSet<>();
            for (Class classKey : mapping.keySet()) {
                List<Class<? extends IBindableLayout>> classes = mapping.get(classKey);
                cachedViewClasses.addAll(classes);
            }
        }
        return cachedViewClasses;
    }


    public static int viewTypeFromViewClass(Class<? extends IBindableLayout>viewClass)
    {
        return viewClass.getCanonicalName().hashCode();
    }


    public Class<? extends IBindableLayout>viewClassFromViewType(int viewType)
    {
        return viewTypes.get(viewType);
    }

    private void clearCachedData(){
        cachedViewClasses=null;
    }
    @VisibleForTesting Map<Class,List<Class<? extends IBindableLayout>>>asMap(){
        return mapping;
    }
}
