package com.example.lgy.shopping_lgy.application;

import android.app.Application;

import com.example.lgy.shopping_lgy.entity.User;

import org.xutils.x;


/**
 * Created by Administrator on 2016/10/9.
 * 1.MyApplication的初始化
 */

public class MyApplication extends Application {

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private User user=new User(1);//设置一个默认的用户：id=1

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
