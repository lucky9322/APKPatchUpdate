package com.zdd.net_library.http.func;


import com.zdd.net_library.exception.HttpTimeException;

import rx.functions.Func1;


/**
 * @CreateDate: 2017/5/8 下午5:08
 * @Author: lucky
 * @Description:
 * @Version: [v1.0]
 *    服务器返回数据判断
 */
public class ResulteFunc implements Func1<Object, Object> {
    @Override
    public Object call(Object o) {
        if (o == null || "".equals(o.toString())) {
            throw new HttpTimeException("数据错误");
        }
        return o;
    }
}
