package com.zdd.mylibrary;


/**
 * @CreateDate:   2017/5/3 下午3:23
 * @Author:       lucky
 * @Description:
 * @Version:      [v1.0]
 *
 */
public class BsPatchJNI {

    static {
        System.loadLibrary("bspatch");
    }

    /**
     * 将增量文件合成为新的Apk
     * @param oldApkPath 当前Apk路径
     * @param newApkPath 合成后的Apk保存路径
     * @param patchPath 增量文件路径
     * @return
     */
    public static native int patch(String oldApkPath, String newApkPath, String patchPath);
}
