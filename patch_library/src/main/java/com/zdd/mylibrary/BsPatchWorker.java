package com.zdd.mylibrary;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.zdd.mylibrary.util.ApkUtils;
import com.zdd.mylibrary.util.SignUtils;

import java.io.File;

/**
 * @CreateDate: 2017/5/9 下午6:32
 * @Author: lucky
 * @Description:
 * @Version: [v1.0]
 */

public class BsPatchWorker extends AsyncTask<Void, Void, Boolean> {

    private static final String SDCARD_PATH = Environment.getExternalStorageDirectory() + File.separator;
    private static final String PATCH_FILE = "old-to-new.patch";
    private static final String NEW_APK_FILE = "new.apk";


    private Context mContext;
    private InstallCallback mInstallCallback;

    public BsPatchWorker(Context context, InstallCallback callback) {
        mContext = context;
        this.mInstallCallback = callback;
    }


    @Override
    protected Boolean doInBackground(Void... params) {
        String oldApkPath = ApkUtils.getCurApkPath(mContext);
        File oldApkFile = new File(oldApkPath);
        File patchFile = new File(getPatchFilePath());
        if (oldApkFile.exists() && patchFile.exists()) {
            Log("正在合并增量文件...");
            String newApkPath = getNewApkFilePath();
            BsPatchJNI.patch(oldApkPath, newApkPath, getPatchFilePath());
//                //检验文件MD5值
//                return Signtils.checkMd5(oldApkFile, MD5);

            Log("增量文件的MD5值为：" + SignUtils.getMd5ByFile(patchFile));
            Log("新文件的MD5值为：" + SignUtils.getMd5ByFile(new File(newApkPath)));

            return true;
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        mInstallCallback.patchResult(result);
        if (result) {
            Log("合并成功，开始安装");
            ApkUtils.installApk(mContext, getNewApkFilePath());
        } else {
            Log("合并失败");
        }
    }

    /**
     * 增量文件的路径
     *
     * @return
     */
    private String getPatchFilePath() {
        return SDCARD_PATH + PATCH_FILE;
    }

    /**
     * 生成的apk放置的路径
     *
     * @return
     */
    private String getNewApkFilePath() {
        return SDCARD_PATH + NEW_APK_FILE;
    }


    /**
     * 打印日志
     *
     * @param log
     */
    private void Log(String log) {
        Log.e("MainActivity", log);
    }

    /**
     * 增量文件合并结果回调
     */
    public interface InstallCallback {
        void patchResult(boolean result);
    }
}
