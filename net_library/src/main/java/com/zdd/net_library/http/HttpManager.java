package com.zdd.net_library.http;

import android.util.Log;

import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.android.FragmentEvent;
import com.zdd.net_library.RxRetrofitApp;
import com.zdd.net_library.config.RequestConfig;
import com.zdd.net_library.base.BaseRxActivity;
import com.zdd.net_library.base.BaseRxFragment;
import com.zdd.net_library.exception.RetryWhenNetworkException;
import com.zdd.net_library.http.func.ExceptionFunc;
import com.zdd.net_library.http.func.ResulteFunc;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @CreateDate: 2017/5/7 上午11:30
 * @Author: lucky
 * @Description:
 * @Version: [v1.0]
 */

public class HttpManager {

    private static RequestConfig mRequestConfig;

    public HttpManager() {
        mRequestConfig = new RequestConfig();
    }

    private static HttpManager mHttpManager;

    public static HttpManager getInstance() {
        if (null == mHttpManager) {
            synchronized (HttpManager.class) {
                mHttpManager = new HttpManager();
            }
        }
        return mHttpManager;
    }


    public void doHttpRequest(Object object, Observable observable, Subscriber subscriber) {

        observable.retryWhen(new RetryWhenNetworkException(mRequestConfig.getRetryCount(),
                mRequestConfig.getRetryDelay(), mRequestConfig.getRetryIncreaseDelay()))
                /*异常处理*/
                .onErrorResumeNext(new ExceptionFunc());
        /*生命周期管理*/
        if (object instanceof BaseRxFragment) {
            observable.compose(((BaseRxFragment) object).bindUntilEvent(FragmentEvent.DESTROY));
        } else if (object instanceof BaseRxActivity) {
            observable.compose(((BaseRxActivity) object).bindUntilEvent(ActivityEvent.DESTROY));
        }
        /*返回数据统一判断*/
        observable.map(new ResulteFunc())
                /*http请求线程*/
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                /*回调线程*/
                .observeOn(AndroidSchedulers.mainThread())
                /*事件订阅*/
                .subscribe(subscriber);

    }

    /**
     * 使用RequestConfig 配置中的baseurl
     *
     * @return
     */
    public Retrofit getReTrofit() {
        return getReTrofit(mRequestConfig.getBaseUrl());
    }

    /**
     * 获取 Retrofit 并动态设置baseurl
     *
     * @param baseUrl
     * @return
     */
    public Retrofit getReTrofit(String baseUrl) {
        //手动创建一个OkHttpClient并设置超时时间缓存等设置
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(mRequestConfig.getConnectionTime(), TimeUnit.SECONDS);
        if (RxRetrofitApp.isDebug()) {
            builder.addInterceptor(getHttpLoggingInterceptor());
        }

        /*创建retrofit对象*/
        final Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
        return retrofit;
    }


    /**
     * 日志输出
     * 自行判定是否添加
     *
     * @return
     */
    private HttpLoggingInterceptor getHttpLoggingInterceptor() {
        //日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("RxRetrofit", "Retrofit====Message:" + message);
            }
        });
        loggingInterceptor.setLevel(level);
        return loggingInterceptor;
    }
}
