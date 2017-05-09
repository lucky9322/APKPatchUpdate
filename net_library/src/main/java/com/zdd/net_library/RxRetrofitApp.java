package com.zdd.net_library;

import android.app.Application;

/**
 * @CreateDate: 2017/5/7 上午11:26
 * @Author: lucky
 * @Description:
 * @Version: [v1.0]
 */

public class RxRetrofitApp {
    private static Application application;
    private static boolean debug;

    public static void init(Application application) {
        setApplication(application);
    }

    public static void init(Application application, boolean debug) {
        setApplication(application);
        setDebug(debug);
    }

    public static Application getApplication() {
        return application;
    }

    public static void setApplication(Application application) {
        RxRetrofitApp.application = application;
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        RxRetrofitApp.debug = debug;
    }
}
