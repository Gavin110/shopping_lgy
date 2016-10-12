package com.example.lgy.shopping_lgy.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.lgy.shopping_lgy.R;
import com.example.lgy.shopping_lgy.application.MyApplication;
import com.example.lgy.shopping_lgy.entity.Cart;
import com.example.lgy.shopping_lgy.entity.Product;
import com.example.lgy.shopping_lgy.utils.NetUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by dliu on 2016/10/8.
 * 商品详情
 */
public class ProductInfoActivity extends AppCompatActivity {

    @InjectView(R.id.prod_info_cart)
    Button prodInfoCart;
    @InjectView(R.id.prod_info_nowbuy)
    Button prodInfoNowbuy;
    @InjectView(R.id.prod_info_bottom)
    RelativeLayout prodInfoBottom;
    @InjectView(R.id.prod_image)
    ImageView prodImage;
    @InjectView(R.id.prod_info_tv_des)
    TextView prodInfoTvDes;
    @InjectView(R.id.prod_info_tv_price)
    TextView prodInfoTvPrice;
    @InjectView(R.id.prod_info_tv_pnum)
    TextView prodInfoTvPnum;
    @InjectView(R.id.prod_info_tv_fapiao)
    TextView prodInfoTvFapiao;
    @InjectView(R.id.prod_info_tv_baoyou)
    TextView prodInfoTvBaoyou;
    @InjectView(R.id.prod_info_tv_buynum)
    TextView prodInfoTvBuynum;
    @InjectView(R.id.subbt)
    Button subbt;
    @InjectView(R.id.edt)
    TextView edt;
    @InjectView(R.id.addbt)
    Button addbt;
    @InjectView(R.id.prod_info_tv_prod_record)
    TextView prodInfoTvProdRecord;
    @InjectView(R.id.lv_user_remark)
    ListView lvUserRemark;
    @InjectView(R.id.prod_info_tv_prod_comment)
    TextView prodInfoTvProdComment;
    @InjectView(R.id.prod_info_linearly)
    LinearLayout prodInfoLinearly;
    @InjectView(R.id.prod_info_scrollView)
    ScrollView prodInfoScrollView;

    TextView titleBarReddot;
    RelativeLayout titleBarRlCartView;


    Product product;//商品信息
    int allNumber;//所有购物车中商品数量

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);
        ButterKnife.inject(this);
        initView();
        initData();
        initEvent();
    }

    public void initView(){
        titleBarReddot= (TextView) findViewById(R.id.title_bar_reddot);
        titleBarRlCartView= (RelativeLayout) findViewById(R.id.title_bar_rl_cartview);//购物车的布局
    }


    public void initEvent(){
        //购物车布局点击事件
        titleBarRlCartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭当前的activity

                finish();
            }
        });
    }

    //初始化界面
    public void initData(){
        //获取传过来的ProductInfo
       Intent intent= getIntent();
       product= intent.getParcelableExtra("productInfo");

        if(product!=null) {
            //商品名称
            prodInfoTvDes.setText(product.getName());
            prodInfoTvPrice.setText(product.getPrice() + "");
            //....
        }
        //获取网络数据，显示用户加入购物车的商品总数量
        RequestParams requestParams=new RequestParams(NetUtil.url+"QueryCartServlet");
        //获取application的userId
        MyApplication myApplication= (MyApplication) getApplication();
        //传参数：userId
       requestParams.addQueryStringParameter("userId", myApplication.getUser().getUserId()+"");

       x.http().get(requestParams, new Callback.CommonCallback<String>() {
           @Override
           public void onSuccess(String result) {
               //
               Gson gson=new Gson();
               Type type=new TypeToken<List<Cart>>(){}.getType();
               Log.i("ProductInfoActivity", "onSuccess: "+result);
               List<Cart> carts= gson.fromJson(result,type);
               if(carts!=null&&carts.size()>0) {
                   //取出购物车中所有物品的总数
                   for (Cart cart : carts) {
                       allNumber += cart.getCollectNumber();
                   }
                   //显示在图标上
                   setDotText();
               }else{
                   titleBarReddot.setVisibility(View.GONE);//设置tv可见
               }

           }




           @Override
           public void onError(Throwable ex, boolean isOnCallback) {

           }

           @Override
           public void onCancelled(CancelledException cex) {

           }

           @Override
           public void onFinished() {

           }
       });




    }



    public void setDotText(){
        //显示在图标上
        titleBarReddot.setVisibility(View.VISIBLE);//设置tv可见
        Log.i("ProductInfoActivity",titleBarReddot.getText()+ "onSuccess: "+allNumber);
        titleBarReddot.setText(allNumber + "");
    }

    @OnClick({R.id.prod_info_cart, R.id.prod_info_nowbuy, R.id.subbt, R.id.addbt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.prod_info_cart:
                //加入购物车

                //改变文本值
                allNumber+=Integer.parseInt(edt.getText().toString());
                setDotText();

                //1、动画效果
               TranslateAnimation translateAnimation= (TranslateAnimation) AnimationUtils.loadAnimation(this,R.anim.shake);
                titleBarRlCartView.startAnimation(translateAnimation);//开始平移动画


                //2、添加到服务器
                RequestParams requestParams=new RequestParams(NetUtil.url+"InsertCartServlet");

                //传参数：购物车对象
                Cart cart=new Cart(product,((MyApplication)getApplication()).getUser().getUserId(),Integer.parseInt(edt.getText().toString()),new Timestamp(System.currentTimeMillis()));
                //cart转换成json
                Gson gson=new Gson();
                String cartJson=gson.toJson(cart);
                requestParams.addBodyParameter("cartInfo",cartJson);

                x.http().post(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.i("ProductInfoActivity", "onSuccess: "+result);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });

                break;
            case R.id.prod_info_nowbuy:
                break;
            case R.id.subbt:
           //减少数量:至少有2个；

                if(Integer.parseInt(edt.getText().toString())>1) {
                    edt.setText(Integer.parseInt(edt.getText().toString()) - 1 + "");
                }
                break;
            case R.id.addbt:
                //添加数量
                edt.setText(Integer.parseInt(edt.getText().toString())+1+"");
                break;
        }
    }
}
