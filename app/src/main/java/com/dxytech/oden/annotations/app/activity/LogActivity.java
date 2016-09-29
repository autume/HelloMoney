package com.dxytech.oden.annotations.app.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dxytech.oden.annotations.R;
import com.dxytech.oden.annotations.app.adapter.DividerItemDecoration;
import com.dxytech.oden.annotations.app.adapter.LogAdapter;
import com.dxytech.oden.annotations.app.utils.L;
import com.dxytech.oden.annotations.app.utils.T;
import com.dxytech.oden.annotations.core.MyPrefs_;
import com.dxytech.oden.annotations.model.HongbaoInfo;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.NoTitle;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.List;

/**
 * 项目名称：Hongbaotest
 * 类描述：
 * 创建人：oden
 * 创建时间：2016/1/28 18:10
 */
@NoTitle
@EActivity(R.layout.activity_log)
public class LogActivity extends Activity {

    protected LogAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    @Pref
    MyPrefs_ myPrefs;

    @ViewById
    TextView tv_success_num;

    @ViewById
    TextView tv_money;

    @ViewById
    RecyclerView mRecyclerView;

    @AfterViews
    public void init() {
        tv_success_num.setText(myPrefs.successNum().get()+"");
        tv_money.setText(myPrefs.totalMoney().get() + "");

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        List<HongbaoInfo> hongbaoInfoList =  HongbaoInfo.getAll();
        mAdapter = new LogAdapter(hongbaoInfoList);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void performBack(View view) {
        super.onBackPressed();
    }

    public void performClear(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("温馨提醒").setMessage("是否清除所有数据");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                HongbaoInfo.deleteALL();
                mAdapter.deleteAll();
            }
        }).setNegativeButton("取消", null);
        builder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
