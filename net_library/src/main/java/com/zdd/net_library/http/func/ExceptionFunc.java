package com.zdd.net_library.http.func;

import android.util.Log;

import com.zdd.net_library.exception.FactoryException;

import rx.Observable;
import rx.functions.Func1;


/**
 * @CreateDate: 2017/5/8 下午5:10
 * @Author: lucky
 * @Description:
 * @Version: [v1.0]
 *
 *    异常处理
 */

public class ExceptionFunc implements Func1<Throwable, Observable> {
    @Override
    public Observable call(Throwable throwable) {
        Log.e("Tag", "-------->" + throwable.getMessage());
        return Observable.error(FactoryException.analysisExcetpion(throwable));
    }
}
