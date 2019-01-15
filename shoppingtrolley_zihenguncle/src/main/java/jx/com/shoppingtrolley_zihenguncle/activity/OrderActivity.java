package jx.com.shoppingtrolley_zihenguncle.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jx.com.shoppingtrolley_zihenguncle.R;
import jx.com.shoppingtrolley_zihenguncle.address_adapter.AddressAdapter;
import jx.com.shoppingtrolley_zihenguncle.address_adapter.OrderListAdapter;
import jx.com.shoppingtrolley_zihenguncle.address_adapter.PopupRecycleAdapter;
import jx.com.shoppingtrolley_zihenguncle.bean.AddCarBean;
import jx.com.shoppingtrolley_zihenguncle.bean.AddressBean;
import jx.com.shoppingtrolley_zihenguncle.bean.CarBean;
import jx.com.shoppingtrolley_zihenguncle.mapbean.MapBean;
import jx.com.shoppingtrolley_zihenguncle.persenter.IPersenterImpl;
import jx.com.shoppingtrolley_zihenguncle.tool.VerifyUtils;
import jx.com.shoppingtrolley_zihenguncle.url.Apis;
import jx.com.shoppingtrolley_zihenguncle.view.IView;

/**
 * @author 郭淄恒
 * 提交订单页面
 */
public class OrderActivity extends AppCompatActivity implements IView {

    @BindView(R.id.total_num)
    TextView total_num;
    @BindView(R.id.popop_open)
    ImageView image_open;
    @BindView(R.id.total_price)
    TextView total_price;
    //没有地址时候显示的按钮
    @BindView(R.id.address_button)
    Button but_addsite;
    //默认显示的地址
    @BindView(R.id.address_relative)
    RelativeLayout address_relative;
    private PopupWindow popupWindow;

    private IPersenterImpl iPersenter;

    private PopupRecycleAdapter adapter;

    private List<AddressBean.Result> result;

    //显示默认地址的布局
    @BindView(R.id.popop_name)
    TextView popup_name;
    @BindView(R.id.popop_address)
    TextView popup_address;
    @BindView(R.id.popop_phone)
    TextView popop_phone;
    @BindView(R.id.car_go_to_pay)
    TextView goto_pay;

    //默认收货地址的ID
    private int address_id;

    @BindView(R.id.order_recycle)
    RecyclerView recyclerView;

    private OrderListAdapter orderListAdapter;
    private List<CarBean.Result> list;
    private double price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);

        iPersenter = new IPersenterImpl(this);

        adapter = new PopupRecycleAdapter(this);

        //设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        //请求地址列表------
        getList();
        //更改地址
        setAddress();
        Intent intent = getIntent();
        //总价
        price = intent.getDoubleExtra("price", 00.0);
        //总量
        int count = intent.getIntExtra("count", 0);
        list = (List<CarBean.Result>) intent.getSerializableExtra("list");
        SpannableString spannableString = new SpannableString("共"+count+"件商品");
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0000")), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        total_num.setText(spannableString);
        SpannableString spannableString1 = new SpannableString("需付款"+ price +"元");
        spannableString1.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0000")), 3, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        total_price.setText(spannableString1);
        //展示订单列表
        orderListAdapter = new OrderListAdapter(list,OrderActivity.this);
        recyclerView.setAdapter(orderListAdapter);





    }

    //请求地址列表------
    private void getList(){
        iPersenter.startRequestget(Apis.SITE_URL,AddressBean.class);
    }
    @OnClick({R.id.address_button,R.id.popop_open,R.id.car_go_to_pay})
    public void setOnclick(View v){
        switch (v.getId()){
            case R.id.address_button:
                startActivity(new Intent(OrderActivity.this,AddressActivity.class));
                break;
                //点击图片出现更多地址
            case R.id.popop_open:
                getMostSite();
                break;
                //点击去结算
            case R.id.car_go_to_pay:
                if(but_addsite.getVisibility()==View.VISIBLE){
                    VerifyUtils.getInstance().toast("暂无收货地址");
                }else {
                    creatOrder(address_id);
                }
                break;
                default:
                    break;
        }
    }

    //创建订单
    private void creatOrder(int address_id) {
        List<MapBean> map = new ArrayList<>();
        Gson gson = new Gson();
        for (int i = 0; i < list.size(); i++) {
            MapBean bean = new MapBean();
            bean.setCommodityId(list.get(i).getCommodityId());
            bean.setAmount(list.get(i).getCount());
            map.add(bean);
        }
        String s = gson.toJson(map);

       /* String str="[";
        for (CarBean.Result result:list){
            str+="{\"commodityId\":"+result.getCommodityId()+",\"amount\":"+result.getCount()+"},";
        }
        String substring = str.substring(0, str.length() - 1);
        substring+="]";*/

        Map<String,String> params = new HashMap<>();
        params.put("orderInfo",s);
        params.put("totalPrice",price+"");
        params.put("addressId",address_id+"");
        iPersenter.startRequest(Apis.CREAT_ORDER_URL,params,AddCarBean.class);
    }

    //弹窗之后更改数据
    private void setAddress(){
        adapter.setShowid(new PopupRecycleAdapter.setId() {
            @Override
            public void setShowId(int id) {
                for (int i = 0; i < result.size(); i++) {
                    if(result.get(i).getId()==id){
                        popup_name.setText(result.get(i).getRealName());
                        popop_phone.setText(result.get(i).getPhone());
                        popup_address.setText(result.get(i).getAddress());
                        address_id =  result.get(i).getId();
                    }
                }
            }
        });
    }

    //popupwindow弹框
    private void getMostSite() {
        View view = View.inflate(OrderActivity.this, R.layout.address_popup, null);
        //获取id，设置布局管理器
        RecyclerView popop_recyclerView = view.findViewById(R.id.popop_recycle);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        layoutmanager.setOrientation(OrientationHelper.VERTICAL);
        popop_recyclerView.setLayoutManager(layoutmanager);
        popop_recyclerView.setAdapter(adapter);
        //添加到popup
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(but_addsite);
    }

    @Override
    public void success(Object data) {
        if (data instanceof AddressBean){
            result = ((AddressBean) data).getResult();
            String message = ((AddressBean) data).getMessage();
            //判断有没有数据-----------并且判断地址或添加地址显示隐藏
            if(message.equals("你还没有收货地址，快去添加吧")){
                but_addsite.setVisibility(View.VISIBLE);
                address_relative.setVisibility(View.INVISIBLE);
            }else {
                but_addsite.setVisibility(View.INVISIBLE);
                address_relative.setVisibility(View.VISIBLE);
            }
            if(((AddressBean) data).getStatus().equals("0000")){
                adapter.setResults(((AddressBean) data).getResult());
            }
            for (int i = 0; i < result.size(); i++) {
                if(result.get(i).getWhetherDefault()==1){
                    //默认收货地址的ID
                    address_id = result.get(i).getId();
                    popup_name.setText(result.get(i).getRealName());
                    popop_phone.setText(result.get(i).getPhone());
                    popup_address.setText(result.get(i).getAddress());
                }
            }
        }
        if(data instanceof AddCarBean){
                VerifyUtils.getInstance().toast(((AddCarBean) data).getMessage());
                finish();
        }
    }

    @Override
    public void failed(String error) {
        VerifyUtils.getInstance().toast(error);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPersenter.detach();
    }
}
