package com.hr.nipuream.gz.base;


import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import java.lang.ref.WeakReference;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {

    public BaseFragment() {
        // Required empty public constructor
    }

    private static MyHandler myHandler;

    private static class MyHandler extends Handler {

        private WeakReference<Activity> ref;

        public MyHandler(WeakReference<Activity> ref){
            this.ref = ref;
        }

        @Override
        public void handleMessage(Message msg) {
            ((BaseActivity)ref.get()).handleMessage(msg);
        }
    }


    public static Handler getMyHandler(){
        if(myHandler == null)
            return null;
        return myHandler;
    }

    public void handleMessage(Message msg){}





}
