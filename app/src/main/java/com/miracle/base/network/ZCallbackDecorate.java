package com.miracle.base.network;

import android.app.Dialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Response;

public class ZCallbackDecorate extends ZCallback {
    String TAG = "ZCallbackDecorate";
    ZCallback mBase;

    Runnable runnable1;
    Runnable runnable2;

    public Runnable getRunnable() {
        return runnable1;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable1 = runnable;
    }

    public Runnable getRunnable2() {
        return runnable2;
    }

    public void setRunnable2(Runnable runnable2) {
        this.runnable2 = runnable2;
    }

    public ZCallbackDecorate(ZCallback mBase) {
        this.mBase = mBase;
    }

    @Override
    protected void onSuccess(Object zResponse) {
        mBase.onSuccess(zResponse);
    }

    @Override
    public void onCacheSuccess(Object zResponse) {
        mBase.onCacheSuccess(zResponse);
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        if(runnable1 != null)
            runnable1.run();
        Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
        mBase.onFailure(call, t);
        Log.d(TAG, "onFailure() called end with: call = [" + call + "], t = [" + t + "]");
        if(runnable2 != null)
            runnable2.run();
    }

    @Override
    public void onFinish(Call call) {
        mBase.onFinish(call);
    }

    @Override
    public INetStatusUI getNetStatusUI() {
        return mBase.getNetStatusUI();
    }

    @Override
    public void setNetStatusUI(INetStatusUI mNetStatusUI) {
        mBase.setNetStatusUI(mNetStatusUI);
    }

    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mBase.getSwipeRefreshLayout();
    }

    @Override
    public void setSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        mBase.setSwipeRefreshLayout(swipeRefreshLayout);
    }

    @Override
    public Dialog getDialog() {
        return mBase.getDialog();
    }

    @Override
    public void setDialog(Dialog dialog) {
        mBase.setDialog(dialog);
    }

    @Override
    public String getCachKey() {
        return mBase.getCachKey();
    }

    @Override
    public void setCachKey(String cachKey) {
        mBase.setCachKey(cachKey);
    }

    @Override
    public void onResponse(Call call, Response response) {
        Log.d(TAG, "onResponse() called with: call = [" + call + "], response = [" + response + "]");
        mBase.onResponse(call, response);
        Log.d(TAG, "onResponse() called end with: call = [" + call + "], response = [" + response + "]");
    }

    @Override
    public void handlePlaceHolder(int code) {
        mBase.handlePlaceHolder(code);
    }
}
