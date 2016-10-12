package com.example.lgy.shopping_lgy.shopping;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.lgy.shopping_lgy.R;
import com.geminno.fragment.MainpageFragment;
import com.geminno.fragment.PersonCenterFragment;
import com.geminno.fragment.ShoppingCartFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "MainpageFragment";
    Fragment[] fragments;
    MainpageFragment mainPageFragment;//主页
    ShoppingCartFragment shopingCartFragment;//购物车
    PersonCenterFragment personCenterFragment;//个人中心
    //按钮的数组，一开始第一个按钮被选中
    Button[] tabs;
    int oldIndex;//用户看到的item
    int newIndex;//用户即将看到的item

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "MainActivity:onCreate");
        //初始化fragment
        mainPageFragment = new MainpageFragment();
        shopingCartFragment = new ShoppingCartFragment();
        personCenterFragment = new PersonCenterFragment();

        //所有fragment的数组
        fragments = new Fragment[]{mainPageFragment, shopingCartFragment, personCenterFragment};


        //设置按钮的数组
        tabs = new Button[3];
        tabs[0] = (Button) findViewById(R.id.button1);//主页的button
        tabs[1] = (Button) findViewById(R.id.button2);//购物车button
        tabs[2] = (Button) findViewById(R.id.button3);//个人中心button
        tabs[0].setOnClickListener(this);
        tabs[1].setOnClickListener(this);
        tabs[2].setOnClickListener(this);

        //if的作用是保存状态,防止横屏时,重新加载
        if (savedInstanceState == null) {
            //界面初始显示第一个fragment;添加第一个fragment,并且缓存第二个Fragment界面
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragments[0]).commit();
            //初始时按钮一被选中
            tabs[0].setSelected(true);
        }


    }
    //程序异常退出时,调用
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    //程序恢复时,调用
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                newIndex = 0;  //选中第一项
                break;
            case R.id.button2:   //选中第二项
                newIndex = 1;
                break;
            case R.id.button3:    //选中第三项
                newIndex = 2;
                break;
        }
        FragmentTransaction mFragmentTransaction;
        //如果选择的项不是当前选中项，则替换；否则，不做操作
        if (newIndex != oldIndex) {
            mFragmentTransaction = getSupportFragmentManager().beginTransaction();
            mFragmentTransaction.hide(fragments[oldIndex]);    //隐藏当前fragment

            //如何选中项没有添加过,则添加
            if (!fragments[newIndex].isAdded()) {
                //添加fragment
                mFragmentTransaction.add(R.id.fragment_container, fragments[newIndex]);
            }
            //显示当前项
            mFragmentTransaction.show(fragments[newIndex]).commit();
        }
        //之前选中的项被取消
        tabs[oldIndex].setSelected(false);

        //当前的选项被选中
        tabs[newIndex].setSelected(true);

        //当前选项变为选中项
        oldIndex = newIndex;

    }
}