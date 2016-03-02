package com.notrace.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by notrace on 2016/3/1.
 */
public class Reflections {
    private static Map<String,Method>methods=new HashMap<>();
    private static Map<String,Constructor>constructors=new HashMap<>();

    public static Method method(Class clzz,String name,Class<?>...params)throws NoSuchMethodException
    {
        List<String>paramClassNames=new ArrayList<>();
        for(Class<?> c:params)
        {
            paramClassNames.add(c.getCanonicalName());
        }
        final String fullName=clzz.getCanonicalName()+"."+name+"("+join("+",paramClassNames)+")";
        if(!methods.containsKey(fullName))
        {
            Method method=clzz.getMethod(name,params);
            methods.put(fullName,method);

        }
        return methods.get(fullName);
    }


    public static Constructor constructor(Class clazz, Class<?>... params) throws NoSuchMethodException {
        List<String> paramClassNames = new ArrayList<>();
        for (Class<?> c : params) {
            paramClassNames.add(c.getCanonicalName());
        }
        final String fullName = clazz.getCanonicalName() + "(" + join("+", paramClassNames) + ")";

        if (!constructors.containsKey(fullName)) {
            Constructor constructor = clazz.getConstructor(params);
            constructors.put(fullName, constructor);
        }

        return constructors.get(fullName);
    }

    private static String join(CharSequence delimiter,Iterable tokens)
    {
        StringBuilder sb=new StringBuilder();
        boolean firstTime=true;
        for(Object token:tokens) {
            if (firstTime)
            {
                firstTime=false;

            }else
            {
                sb.append(delimiter);
            }
            sb.append(token);
        }
        return sb.toString();
    }
}

