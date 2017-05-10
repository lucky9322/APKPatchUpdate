package com.zdd.apkpatchupdate;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.zdd.mylibrary.BsPatchWorker;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((TextView) findViewById(R.id.tv_main)).setText("更新完成！\n当前版本号为 v" + getVersionName(MainActivity.this));

        findViewById(R.id.btn_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //并行任务
                new BsPatchWorker(MainActivity.this, new BsPatchWorker.InstallCallback() {
                    @Override
                    public void patchResult(boolean result) {

                    }
                }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
    }

    /**
     * 获取当前版本号
     *
     * @param context
     * @return
     */
    private String getVersionName(Context context) {
        String versionName = "";
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }
}
