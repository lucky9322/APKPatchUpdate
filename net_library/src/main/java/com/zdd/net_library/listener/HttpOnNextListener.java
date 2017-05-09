package com.zdd.net_library.listener;

import com.zdd.net_library.exception.ApiException;

/**
 * @CreateDate: 2017/5/7 下午10:55
 * @Author: lucky
 * @Description:
 * @Version: [v1.0]
 * <p>
 * 成功的回调处理
 */

public interface HttpOnNextListener {
    /**
     * @param result
     * @param method
     */
    void onNext(String result, String method);

    void onError(ApiException e);
}
