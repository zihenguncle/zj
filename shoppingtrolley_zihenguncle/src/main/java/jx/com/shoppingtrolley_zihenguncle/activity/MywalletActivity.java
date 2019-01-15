package jx.com.shoppingtrolley_zihenguncle.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.com.shoppingtrolley_zihenguncle.R;
import jx.com.shoppingtrolley_zihenguncle.base.BaseActivity;
import jx.com.shoppingtrolley_zihenguncle.iteminterval.SpacesItemDecoration;
import jx.com.shoppingtrolley_zihenguncle.order.bean.OrderBean;
import jx.com.shoppingtrolley_zihenguncle.pay.PayBean;
import jx.com.shoppingtrolley_zihenguncle.pay.Pay_Adapter;
import jx.com.shoppingtrolley_zihenguncle.persenter.IPersenterImpl;
import jx.com.shoppingtrolley_zihenguncle.url.Apis;
import jx.com.shoppingtrolley_zihenguncle.view.IView;

public class MywalletActivity extends BaseActivity implements IView {

    @BindView(R.id.text_money)
    TextView money;
    @BindView(R.id.pay_xrecycle)
    XRecyclerView xRecyclerView;
    private int page;
    //当前页显示条数
    private int COUNT = 5;
    private IPersenterImpl iPersenter;
    private Pay_Adapter adapter;

    @Override
    protected void initData() {
        page = 1;
        ButterKnife.bind(this);
        iPersenter = new IPersenterImpl(this);
        //设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(MywalletActivity.this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        xRecyclerView.setLayoutManager(layoutManager);
        int spacingInPixels = 11;
        xRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        adapter = new Pay_Adapter(this);
        xRecyclerView.setAdapter(adapter);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getData();
            }

            @Override
            public void onLoadMore() {
                getData();
            }
        });
        getData();

    }

    private void getData(){
        iPersenter.startRequestget(String.format(Apis.SELECT_PAY,page,COUNT),PayBean.class);
    }

    @Override
    protected int getViewById() {
        return R.layout.activity_mywallet;
    }

    @Override
    public void success(Object data) {
        if(data instanceof PayBean){
            int balance = ((PayBean) data).getResult().getBalance();
            money.setText(balance+"00.0");
            if(page == 1){
                adapter.setData(((PayBean) data).getResult().getDetailList());
            }else {
                adapter.addData(((PayBean) data).getResult().getDetailList());
            }
            page++;
            xRecyclerView.refreshComplete();
            xRecyclerView.loadMoreComplete();
        }
    }

    @Override
    public void failed(String error) {

    }
}
