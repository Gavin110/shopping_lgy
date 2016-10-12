package com.geminno.fragment;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lgy.shopping_lgy.R;
import com.example.lgy.shopping_lgy.entity.Product;
import com.example.lgy.shopping_lgy.shopping.ProductInfoActivity;
import com.example.lgy.shopping_lgy.utils.CommonAdapter;
import com.example.lgy.shopping_lgy.utils.NetUtil;
import com.example.lgy.shopping_lgy.utils.ViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/10/9.
 */

public class MainpageFragment extends BaseFragment {


    private static final int REQUSETCODE = 1;
    @InjectView(R.id.query)
    EditText mQuery;
    @InjectView(R.id.search_clear)
    ImageButton mSearchClear;
    @InjectView(R.id.tv_search)
    TextView mTvSearch;
    @InjectView(R.id.id_prod_list_sort_left)
    TextView mIdProdListSortLeft;
    @InjectView(R.id.prod_list_rl_pop)
    RelativeLayout mProdListRlPop;
    @InjectView(R.id.id_prod_list_sort_right)
    TextView mIdProdListSortRight;
    @InjectView(R.id.id_prod_list_sort_right_trangle)
    ImageView mIdProdListSortRightTrangle;
    @InjectView(R.id.prod_list_pop_two)
    RelativeLayout mProdListPopTwo;
    @InjectView(R.id.id_prod_list_sort)
    LinearLayout mIdProdListSort;
    @InjectView(R.id.id_prod_list_sort_line1)
    View mIdProdListSortLine1;
    @InjectView(R.id.lv_goods)
    ListView mLvGoods;

    //商品名称
    String productName;
    int orderFlag = 0;
    int pageNo = 1;
    int pageSize = 9;
    List<Product> products = new ArrayList<>();//存放商品信息

    CommonAdapter<Product> goodsAdapter;
    private static final String TAG = "MainpageFragment";
    List<String> popContents = new ArrayList<String>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_page, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {
        mLvGoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //跳转到商品详情页面
                Intent intent=new Intent(getActivity(), ProductInfoActivity.class);

                //点击item的商品信息
                intent.putExtra("productInfo",products.get(position));
                //startActivityForResult(intent,REQUSETCODE);
                startActivity(intent);
                



            }
        });

    }

    @Override
    public void initData() {
        getData();  //获取网络数据
        popContents.add("价格从高到低");
        popContents.add("价格从低到高");

    }

    private void getData() {
        //界面初始化数据：listview显示数据
        //xutils获取网络数据
        Log.i(TAG, "test_getData()");
        //public static final String url="http://10.40.5.49:8080/shoppingWeb_lgy/";
        String url = NetUtil.url + "QueryProductServlet";//访问网络的url
        RequestParams requestParams = new RequestParams(url);
        requestParams.addQueryStringParameter("productName", productName);
        requestParams.addQueryStringParameter("orderFlag", orderFlag + "");//排序标记
        requestParams.addQueryStringParameter("pageNo", pageNo + "");
        requestParams.addQueryStringParameter("pageSize", pageSize + "");
        Log.i(TAG, url);
        Log.e(TAG, "getData: " + requestParams);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Log.i(TAG, "onSuccess:" + result);
                //json转换成List<Product>
                Gson gson = new Gson();
                Type type = new TypeToken<List<Product>>() {
                }.getType();

                List<Product> newProducts=new ArrayList<Product>();
                newProducts=gson.fromJson(result,type);//解析成List<Product>
                products.clear();//清空原来的数据
                products.addAll(newProducts);
                if (goodsAdapter == null) {
                    goodsAdapter = new CommonAdapter<Product>(getActivity(), products, R.layout.prod_list_item) {  //抽象类中的抽象方法必须要实现

                        @Override
                        protected void convert(ViewHolder viewHolder, Product product, int position) {
                            //取出控件，赋值
                            TextView tv = viewHolder.getViewById(R.id.prod_list_item_tv);
                            tv.setText(product.getName());//商品名称

                            TextView tvPrice = viewHolder.getViewById(R.id.prod_list_item_tv2);
                            tvPrice.setText("￥" + product.getPrice());
                            //其他控件赋值

                        }
                    };
                    mLvGoods.setAdapter(goodsAdapter);
                } else {
                    goodsAdapter.notifyDataSetChanged();
                }

                //products.clear();

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e(TAG, ex.getMessage());

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.search_clear, R.id.tv_search, R.id.id_prod_list_sort_left, R.id.id_prod_list_sort_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_clear:
                mQuery.setText(null);
                break;
            case R.id.tv_search:
                productName = mQuery.getText().toString().trim();
                getData();
                break;
            case R.id.id_prod_list_sort_left:
                orderFlag=1;
                getData();
                break;
            case R.id.id_prod_list_sort_right:
                //综合排序
                initPopupWindow(view);
                break;
        }
    }

    //创建popupwindow:v(点击的按钮)
    private void initPopupWindow(View v) {
        //content    自定义view
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.lv_zonghe_paixu,null);

        //listView设置数据源
        ListView lv = ((ListView) view.findViewById(R.id.lv_zonghe_paixu));
        ArrayAdapter myArrayAdapter = new ArrayAdapter(getActivity(),R.layout.lv_item_zonghe_paixu,popContents);
        lv.setAdapter(myArrayAdapter);
        final PopupWindow popupWindow = new PopupWindow(view,ViewGroup.LayoutParams.MATCH_PARENT,200);


        //popupwiondow外面点击，popupwindow消失
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        //显示在v的下边

        popupWindow.showAsDropDown(v);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //关闭popupWindow
                popupWindow.dismiss();
                //排序
                if (position==0){
                    orderFlag = 2;
                }else if (position==1){
                    orderFlag = 3;
                }
                //重新获取数据,按照价格排序
                getData();
            }
        });

    }

}
