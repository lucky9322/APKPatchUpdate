package com.zdd.apkpatchupdate.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zdd.apkpatchupdate.R;
import com.zdd.apkpatchupdate.api.ApiManager;
import com.zdd.net_library.base.BaseRxActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @CreateDate: 2017/5/4 下午3:24
 * @Author: lucky
 * @Description:
 * @Version: [v1.0]
 */

public class RetrofitActivity extends BaseRxActivity {

    @BindView(R.id.click_me_BN)
    Button mClickMeBN;
    @BindView(R.id.result_TV)
    TextView mResultTV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        ButterKnife.bind(this);
    }

    @OnClick({R.id.click_me_BN})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.click_me_BN:
                getMovie();
                break;
        }
    }

    //进行网络请求
    private void getMovie() {

        ApiManager.getTopView(this, new ApiManager.ApiResultCallback() {
            @Override
            public void result(String result) {
                mResultTV.setText(result);
            }
        });
    }
}
