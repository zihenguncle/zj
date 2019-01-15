package jx.com.shoppingtrolley_zihenguncle.order.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.com.shoppingtrolley_zihenguncle.R;
import jx.com.shoppingtrolley_zihenguncle.base.BaseFragment;
import jx.com.shoppingtrolley_zihenguncle.bean.AddCarBean;
import jx.com.shoppingtrolley_zihenguncle.iteminterval.SpacesItemDecoration;
import jx.com.shoppingtrolley_zihenguncle.order.adapter.AllOrderAdapter;
import jx.com.shoppingtrolley_zihenguncle.order.bean.OrderBean;
import jx.com.shoppingtrolley_zihenguncle.persenter.IPersenterImpl;
import jx.com.shoppingtrolley_zihenguncle.tool.VerifyUtils;
import jx.com.shoppingtrolley_zihenguncle.url.Apis;
import jx.com.shoppingtrolley_zihenguncle.view.IView;

public class AllordersFragment extends BaseFragment implements IView {

    @BindView(R.id.allorder_xrecycle)
    XRecyclerView xRecyclerView;

    IPersenterImpl iPersenter;

    private int page;

    //要查询对应状态的订单数据
    private int STATUS = 0;
    //当前页显示条数
    private int COUNT = 5;

    private AllOrderAdapter allOrderAdapter;
    @Override
    protected void initData(View view) {
        ButterKnife.bind(this,view);
        page = 1;
        iPersenter = new IPersenterImpl(this);

        //设置布局管理器
        setLayout();

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

        allOrderAdapter = new AllOrderAdapter(getActivity());
        xRecyclerView.setAdapter(allOrderAdapter);

        //点击去结算
        allOrderAdapter.setGotoPay(new AllOrderAdapter.gotoPay() {
            @Override
            public void setPay(final String orderId, String name) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("确认付款吗?");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Map<String,String> map = new HashMap<>();
                        map.put("orderId",orderId);
                        map.put("payType",1+"");
                        iPersenter.startRequest(Apis.PAY_URL,map,AddCarBean.class);
                    }
                });
                builder.setNegativeButton("狠心取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();

            }
        });

        allOrderAdapter.setDeleteOrder(new AllOrderAdapter.DelteleOrder() {
            @Override
            public void setId(String id) {
                iPersenter.startRequestDelete(String.format(Apis.DELETE_ORDER_URL,id),AddCarBean.class);
            }
        });

    }

    private void getData(){
        iPersenter.startRequestget(String.format(Apis.SELECT_ORDER_URL,STATUS,page,COUNT),OrderBean.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        iPersenter.startRequestget(String.format(Apis.SELECT_ORDER_URL,STATUS,page,COUNT),OrderBean.class);
    }

    @Override
    public void success(Object data) {
        if(data instanceof OrderBean){
            if(page == 1){
                allOrderAdapter.setData(((OrderBean) data).getOrderList());
            }else {
                allOrderAdapter.addData(((OrderBean) data).getOrderList());
            }
            page++;
            xRecyclerView.refreshComplete();
            xRecyclerView.loadMoreComplete();
        }
        if (data instanceof AddCarBean){
            VerifyUtils.getInstance().toast(((AddCarBean) data).getMessage());
        }
    }

    @Override
    public void failed(String error) {
        VerifyUtils.getInstance().toast(error);
    }
    private void setLayout(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        xRecyclerView.setLayoutManager(layoutManager);
        int spacingInPixels = 10;
        xRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
    }
    @Override
    protected int getViewById() {
        return R.layout.fragment_allorders;
    }



}
