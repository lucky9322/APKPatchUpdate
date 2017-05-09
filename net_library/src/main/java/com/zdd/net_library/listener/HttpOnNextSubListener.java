package com.zdd.net_library.listener;

import rx.Observable;

/**
 * @CreateDate: 2017/5/7 下午11:00
 * @Author: lucky
 * @Description:
 * @Version: [v1.0]
 */

public interface HttpOnNextSubListener {


    /**
     * ober成功回调
     *
     * @param observable
     */
    void onNext(Observable observable);
}
