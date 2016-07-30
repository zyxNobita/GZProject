package com.hr.nipuream.gz.base;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.hr.nipuream.gz.GZApplication;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {

    public BaseFragment() {
        // Required empty public constructor
    }

    private static MyHandler myHandler;


    public static class MyHandler extends Handler{

        private WeakReference<Fragment> ref;

        public MyHandler(WeakReference<Fragment> ref){
            this.ref = ref;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ((BaseFragment)(ref.get())).handlerGZData(msg);
        }
    }

    public void handlerGZData(Message msg){
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GZApplication.getInstance().getEventBus().register(this);
        myHandler = new MyHandler(new WeakReference<Fragment>(this));
        registerTask();
    }

    public static Handler getMyHandler(){
        if(myHandler == null)
            return null;
        return myHandler;
    }


    public void registerTask(){};


    @Override
    public void onDestroy() {
        GZApplication.getInstance().getEventBus().unregister(this);
        super.onDestroy();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateGzData(Object object){

    }

}
