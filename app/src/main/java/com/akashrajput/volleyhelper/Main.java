package com.akashrajput.volleyhelper;

import android.app.Application;
import android.text.TextUtils;

import com.akashrajput.volleyhelper.network.LruBitmapCache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class Main extends Application {

    public static final String TAG = Main.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;


    private static Main mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        //queue = Volley.newRequestQueue(this);
    }

    public static synchronized Main getInstance() {
        return mInstance;
    }



    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}
