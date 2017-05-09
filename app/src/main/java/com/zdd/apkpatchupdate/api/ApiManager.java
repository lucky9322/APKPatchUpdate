package com.zdd.apkpatchupdate.api;

import android.app.Activity;
import android.util.Log;

import com.zdd.net_library.http.HttpManager;

import rx.Subscriber;

/**
 * @CreateDate: 2017/5/9 下午3:02
 * @Author: lucky
 * @Description:
 * @Version: [v1.0]
 */

public class ApiManager {

    private static Urls getUrls() {
        return HttpManager.getInstance().getReTrofit("https://api.douban.com/v2/movie/").create(Urls.class);
    }

    public static void getTopView(Activity activity, final ApiResultCallback callback) {

        HttpManager.getInstance().doHttpRequest(activity, getUrls().getTopView(0, 10),
                new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.i("zzzzz", "zzzz" + 1);
            }

            @Override
            public void onError(Throwable e) {
                Log.i("zzzzz", "zzzz" + 1 + e.getMessage());
            }

            @Override
            public void onNext(String o) {
                callback.result(o);
                Log.i("zzzzz", "zzzz" + 0);
            }
        });
    }

    public interface ApiResultCallback{
        void result(String result);
    }
}
