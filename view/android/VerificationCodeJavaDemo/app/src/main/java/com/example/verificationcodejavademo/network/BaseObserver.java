package com.example.verificationcodejavademo.network;


import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.example.verificationcodejavademo.R;
import com.google.gson.Gson;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Date:2020/5/18
 * author:wuyan
 * 对基础数据进行统一出路
 */
public abstract class BaseObserver<T> implements Observer<BaseResponse<T>> {
    private static final String TAG = "BaseObserver";

    private boolean mShowDialog;
    private ProgressDialog dialog;
    private Context mContext;
    private Disposable d;

    public abstract void onSuccess(T data);

    public abstract void onFailure(Throwable e, String errorMsg);

    public BaseObserver(Context mContext) {
        this.mContext = mContext;
    }

    public BaseObserver(Context mContext, boolean mShowDialog) {
        this.mContext = mContext;
        this.mShowDialog = mShowDialog;
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.d = d;
        if (!isConnected(mContext)) {
            Toast.makeText(mContext, "网络未连接", Toast.LENGTH_SHORT).show();
            if (d.isDisposed()) {
                d.dispose();
            }
        } else {
            if (dialog == null && mShowDialog == true) {
                dialog = new ProgressDialog(mContext, R.style.dialog);
                dialog.show();
            }
        }
    }

    //后给给返回结果
    @Override
    public void onNext(BaseResponse<T> response) {
        Log.i(TAG, "网络请求数据结果: " + new Gson().toJson(response));
        if (response.getRepCode().equals("0000")) {
            onSuccess(response.getRepData());
        } else {
            //返回其他错误码
            onFailure(null, "网络请求失败");
        }
    }

    //网络请求失败
    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "onError: " + e.getMessage());
        if (d.isDisposed()) {
            d.dispose();
        }
        hidDialog();
        onFailure(e, RxExceptionUtil.exceptionHandler(e));
    }

    @Override
    public void onComplete() {
        if (d.isDisposed()) {
            d.dispose();
        }
        hidDialog();
    }

    private void hidDialog() {
        if (dialog != null && mShowDialog == true) {
            dialog.dismiss();
            dialog = null;
        }
    }

    /**
     * 是否又网络连接
     */
    private static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {
            return false;
        }
        boolean available = info.isAvailable();
        return available;
    }
}
