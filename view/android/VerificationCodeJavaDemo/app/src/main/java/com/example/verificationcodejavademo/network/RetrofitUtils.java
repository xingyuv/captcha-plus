package com.example.verificationcodejavademo.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Date:2020/5/19
 * author:wuyan
 * Retrofit封装
 */
public class RetrofitUtils {
    private static final String TAG = "RetrofitUtils";
    //设置默认超时时间
    public static final int DEFAULT_TIME = 10;
    private static ServerApi mServerApi;

    public static ServerApi getServerApi() {
        if (mServerApi == null) {
            synchronized (RetrofitUtils.class) {
                if (mServerApi == null) {
                    mServerApi = new RetrofitUtils().getRetrofit();
                }
            }
        }
        return mServerApi;
    }

    public ServerApi getRetrofit() {
        ServerApi serverApi = initRetrofit(initOkHttp()).create(ServerApi.class);
        return serverApi;
    }

    /**
     * 初始化Retrofit
     */
    private Retrofit initRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(ServerApi.BaseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加Rxjava支持
                .addConverterFactory(GsonConverterFactory.create())//添加GSON解析：返回数据转换成GSON类型
                .build();

    }

    private OkHttpClient initOkHttp() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient().newBuilder()
                .readTimeout(DEFAULT_TIME, TimeUnit.SECONDS)//设置读取超时时间
                .connectTimeout(DEFAULT_TIME, TimeUnit.SECONDS)//设置请求超时时间
                .writeTimeout(DEFAULT_TIME, TimeUnit.SECONDS)//设置写入超时时间
                .retryOnConnectionFailure(true)//设置出现错误进行重新连接
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }
}
