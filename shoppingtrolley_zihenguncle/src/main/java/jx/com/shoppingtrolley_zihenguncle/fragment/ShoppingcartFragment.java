package jx.com.shoppingtrolley_zihenguncle.fragment;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jx.com.shoppingtrolley_zihenguncle.R;
import jx.com.shoppingtrolley_zihenguncle.activity.OrderActivity;
import jx.com.shoppingtrolley_zihenguncle.base.BaseFragment;
import jx.com.shoppingtrolley_zihenguncle.bean.CarBean;
import jx.com.shoppingtrolley_zihenguncle.car_adapter.CarAdapter;
import jx.com.shoppingtrolley_zihenguncle.iteminterval.SpacesItemDecoration;
import jx.com.shoppingtrolley_zihenguncle.persenter.IPersenterImpl;
import jx.com.shoppingtrolley_zihenguncle.tool.VerifyUtils;
import jx.com.shoppingtrolley_zihenguncle.url.Apis;
import jx.com.shoppingtrolley_zihenguncle.view.IView;

/**
 * @author 郭淄恒
 * 购物车页面
 */
public class ShoppingcartFragment extends BaseFragment implements IView {

    @BindView(R.id.car_recycle)
    RecyclerView recyclerView;
    @BindView(R.id.car_all_chekbox)
    CheckBox checkBox;
    @BindView(R.id.car_total_price)
    TextView total_price;
    @BindView(R.id.car_go_to_pay)
    TextView goto_pay;

    @BindView(R.id.shop_num)
    TextView shop_num;
    IPersenterImpl iPersenter;

    private CarAdapter adapter;
    private CarBean bean;

    private int num;
    private double price;
    @Override
    protected void initData(View view) {
        //绑定
        ButterKnife.bind(this,view);
        iPersenter = new IPersenterImpl(this);
        iPersenter.startRequestget(Apis.SELECTCAR_URL,CarBean.class);
        //设置布局管理器
        setLayout();

        adapter = new CarAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        adapter.setCallBack(new CarAdapter.EditCallBack() {
            @Override
            public void editcallback() {
                num = 0;
                price = 0;
                int count = 0;
                List<CarBean.Result> result = bean.getResult();
                for (int i = 0; i < result.size(); i++) {
                    count += result.get(i).getCount();
                    if(result.get(i).isCheck()){
                        num += result.get(i).getCount();
                        price += result.get(i).getPrice()*result.get(i).getCount();
                    }
                }
                //判断所有的数量是否大于，如果大于的就是没有全部选中
                if(num < count){
                    checkBox.setChecked(false);
                }else {
                    checkBox.setChecked(true);
                }

                total_price.setText("￥"+price);
                goto_pay.setText("去结算（"+num+"）");
                shop_num.setText("共"+count+"件宝贝");
            }
        });
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<CarBean.Result> result = bean.getResult();
                num = 0;
                price = 0;
                for (int i = 0; i < result.size(); i++) {
                    result.get(i).setCheck(checkBox.isChecked());
                    num += result.get(i).getCount();
                    price += result.get(i).getCount()*result.get(i).getCount();
                }
                if(checkBox.isChecked()){
                    total_price.setText("总价:"+price);
                    goto_pay.setText("去结算"+num);
                }else {
                    total_price.setText("总价:"+0.0);
                    goto_pay.setText("去结算"+0.0);
                }
                adapter.notifyDataSetChanged();
            }
        });

    }

    @OnClick({R.id.car_go_to_pay})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.car_go_to_pay:
                if (num == 0){
                    toast("请选择要购买的商品");
                }else {
                    int commodityId = 0;
                    int count = 0;
                    List<CarBean.Result> list = new ArrayList<>();
                    List<CarBean.Result> result = bean.getResult();
                    for(int i=0;i<result.size();i++){
                        if(result.get(i).isCheck()){
                            list.add(new CarBean.Result(result.get(i).getCommodityId(),
                                    result.get(i).getCommodityName(),
                                    result.get(i).getPic(),
                                    result.get(i).getPrice(),
                                    result.get(i).getCount(),
                                    result.get(i).isCheck()
                            ));
                        }
                    }
                    Intent intent = new Intent(getActivity(), OrderActivity.class);
                    intent.putExtra("price",price);
                    intent.putExtra("count",num);
                    intent.putExtra("list", (Serializable) list);
                    startActivity(intent);
                }
                break;
                default:
                    break;
        }
    }

    private void setLayout(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        int spacingInPixels = 20;
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
    }
    @Override
    protected int getViewById() {
        return R.layout.fragment_shoppingcart;
    }

    @Override
    public void success(Object data) {
        if(data instanceof CarBean){
            bean = (CarBean) data;
            adapter.setData(bean.getResult());
        }
    }

    @Override
    public void failed(String error) {
        toast(error);
    }
    private void toast(String s){
        Toast.makeText(getActivity(),s+"",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        iPersenter.detach();
    }
}
