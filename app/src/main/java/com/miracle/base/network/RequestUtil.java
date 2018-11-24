package com.miracle.base.network;

import android.text.TextUtils;
import android.util.Log;

import com.baidu.platform.comapi.map.A;
import com.miracle.base.util.GsonUtil;
import com.miracle.base.util.sqlite.SQLiteUtil;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Michael on 2018/11/2 18:43 (星期五)
 */
public class RequestUtil {

    /**
     * 先取缓存，后请求数据覆盖
     *
     * @param call
     * @param callback
     */
    public static void cacheUpdate(Call call, ZCallback callback) {
        String key = callback.getCachKey();
        if (TextUtils.isEmpty(key)) {
            call.enqueue(callback);
        } else {
            Object body = parseJson(SQLiteUtil.getString(key), callback);
            if (body != null) {
                callback.onCacheSuccess(body);
            }
            call.enqueue(callback);
        }
    }

    /**
     * @param call
     * @param callback
     * @TODO 先取请求数据, 失败了使用缓存
     */
    public static void requestIfFailedUseCache(Call call, ZCallback callback) {

        ZCallbackDecorate proxy = null;
        if (!(callback instanceof ZCallbackDecorate)) {
            proxy = new ZCallbackDecorate(callback);
            proxy.setRunnable(new Runnable() {
                @Override
                public void run() {
                    Log.d("xxxxxxxxx", "run xxxzzz 111111111");
                }
            });

            proxy.setRunnable2(new Runnable() {
                @Override
                public void run() {
                    Log.d("xxxxxxxxx", "run xxxzzz 222222222");
                }
            });
        } else {
            proxy = (ZCallbackDecorate) callback;
        }
        String key = proxy.getCachKey();
        if (TextUtils.isEmpty(key)) {
            call.enqueue(proxy);
        } else {
            Object body = parseJson(SQLiteUtil.getString(key), proxy);
            if (body != null) {
                callback.onCacheSuccess(body);
            }
            call.enqueue(proxy);
        }

    }

    /**
     * 有缓存，用缓存，不请求数据
     * 无缓存，请求数据
     *
     * @param call
     * @param callback
     */
    public static void cachePrior(Call call, ZCallback callback) {
        String key = callback.getCachKey();
        if (TextUtils.isEmpty(key)) {
            call.enqueue(callback);
        } else {
            Object body = parseJson(SQLiteUtil.getString(key), callback);
            if (body != null) {
                callback.onCacheSuccess(body);
            } else {
                call.enqueue(callback);
            }
        }
    }

    private static Object parseJson(String json, ZCallback callback) {
        Type mGenericSuperclass;
        Type genericSuperclass = callback.getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            mGenericSuperclass = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        } else {
            mGenericSuperclass = Object.class;
        }
        return GsonUtil.json2Obj(json, mGenericSuperclass);
    }
}
