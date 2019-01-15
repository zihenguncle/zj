package jx.com.shoppingtrolley_zihenguncle.order.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
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

/**
 * @author 郭淄恒
 * 未付款的列表展示
 */
public class ObligationFragment extends BaseFragment implements IView {

    @BindView(R.id.obligation_recycle)
    XRecyclerView xRecyclerView;
    IPersenterImpl iPersenter;
    private int STATUS = 1;
    //当前页显示条数
    private int COUNT = 5;
    private int page;
    private AllOrderAdapter allOrderAdapter;

    @Override
    protected void initData(View view) {
        ButterKnife.bind(this,view);
        iPersenter = new IPersenterImpl(this);
        page=1;
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
                builder.setTitle("再次确认");
                builder.setMessage("确认购买此商品吗?");
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
    }
    private void getData(){
        iPersenter.startRequestget(String.format(Apis.SELECT_ORDER_URL,STATUS,page,COUNT),OrderBean.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        iPersenter.startRequestget(String.format(Apis.SELECT_ORDER_URL,STATUS,page,COUNT),OrderBean.class);
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
        return R.layout.fragment_obligation;
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
            xRecyclerView.loadMoreComplete();
            xRecyclerView.refreshComplete();
        }
    }

    @Override
    public void failed(String error) {
        VerifyUtils.getInstance().toast(error);
    }
}
