package com.geminno.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by Administrator on 2016/10/9.
 */

public abstract class BaseFragment extends Fragment {
    //找控件
    //界面数据初始化
    //设置事件


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        initEvent();

    }

    public abstract void initView();  //找控件
    public abstract void initEvent();  //设置控件的事件
    public abstract void initData();   //设置界面初始值
}
