package com.notrace.utils;

import android.os.Looper;

import com.notrace.BuildConfig;

/**
 * Created by notrace on 2016/3/1.
 */
public class ThreadHelper {

    public static void creashIfMainThread(){
        if(BuildConfig.DEBUG){
            if(Thread.currentThread()== Looper.getMainLooper().getThread())
            {
                throw new  IllegalStateException("This method should NOT be called from the Main Thread");
            }
        }
    }


    public static void crashIfBackgroundThread() {
        if (BuildConfig.DEBUG) {
            if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
                throw new IllegalStateException("This method should be called from the Main Thread");
            }
        }
    }
}
