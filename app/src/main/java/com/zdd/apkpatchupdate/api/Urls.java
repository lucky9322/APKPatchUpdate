package com.zdd.apkpatchupdate.api;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @CreateDate: 2017/5/9 下午3:01
 * @Author: lucky
 * @Description:
 * @Version: [v1.0]
 */

public interface Urls {

    @GET("top250")
    Observable<String> getTopView(@Query("start") int start, @Query("count") int count);
}
