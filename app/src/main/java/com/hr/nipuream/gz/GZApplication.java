package com.hr.nipuream.gz;

import android.app.Activity;
import android.app.Application;
import com.android.volley.RequestQueue;
import com.android.volley.cache.DiskLruBasedCache;
import com.android.volley.cache.plus.SimpleImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.myapp.MyEventBusIndex;
import com.hr.nipuream.gz.dao.db.DaoMaster;
import com.hr.nipuream.gz.dao.db.DaoSession;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.greendao.database.Database;
import java.util.Iterator;
import java.util.WeakHashMap;

/**
 * 描述：应用入口
 * 作者：Nipuream
 * 时间: 2016-07-21 16:28
 * 邮箱：571829491@qq.com
 */
public class GZApplication extends Application {

    public static final boolean ENCRYPTED = false;

    private static GZApplication instance;

    public static GZApplication getInstance() {
        return instance;
    }

    private WeakHashMap<String, Activity> actLists = new WeakHashMap<String, Activity>();

    public void putAct(Activity act) {
        if (actLists != null) {
            actLists.put(act.getClass().getSimpleName(), act);
        }
    }

    public void clearAct() {
        if (actLists != null && actLists.size() > 0) {
            Iterator<String> keys = actLists.keySet().iterator();
            while (keys.hasNext()) {
                Activity act = actLists.get( keys.next());
                if (act != null)
                    act.finish();
            }
        }
    }

    private DaoSession daoSession;

    public DaoSession getDaoSession() {
        return daoSession;
    }

    private RequestQueue mQueue;

    public RequestQueue getmQueue() {
        return mQueue;
    }

    private EventBus eventBus;

    public EventBus getEventBus() {
        return eventBus;
    }

    private SimpleImageLoader mImageLoader;

    public SimpleImageLoader getmImageLoader() {
        return mImageLoader;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mQueue = Volley.newRequestQueue(this);
        eventBus = EventBus.builder().addIndex(new MyEventBusIndex()).
                installDefaultEventBus();
        DiskLruBasedCache.ImageCacheParams cacheParams = new DiskLruBasedCache.ImageCacheParams(getApplicationContext(), "gzCache");
        cacheParams.setMemCacheSizePercent(0.5f);
        mImageLoader = new SimpleImageLoader(getApplicationContext(),cacheParams);

        DaoMaster.DevOpenHelper helper =
                new DaoMaster.DevOpenHelper(this, ENCRYPTED ? "gz-db-encrypted" : "gz-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }
}
