package jx.com.shoppingtrolley_zihenguncle.order.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.com.shoppingtrolley_zihenguncle.R;
import jx.com.shoppingtrolley_zihenguncle.base.BaseFragment;
import jx.com.shoppingtrolley_zihenguncle.iteminterval.SpacesItemDecoration;
import jx.com.shoppingtrolley_zihenguncle.order.bean.OrderBean;
import jx.com.shoppingtrolley_zihenguncle.order.completed_adapter.CompletedAdapter;
import jx.com.shoppingtrolley_zihenguncle.persenter.IPersenterImpl;
import jx.com.shoppingtrolley_zihenguncle.tool.VerifyUtils;
import jx.com.shoppingtrolley_zihenguncle.url.Apis;
import jx.com.shoppingtrolley_zihenguncle.view.IView;

public class CompletedFragment extends BaseFragment implements IView {


    @BindView(R.id.com_xrecycle)
    XRecyclerView xRecyclerView;
    private int page;
    //要查询对应状态的订单数据
    private int STATUS = 9;
    //当前页显示条数
    private int COUNT = 5;
    IPersenterImpl iPersenter;

    private CompletedAdapter adapter;

    @Override
    protected void initData(View view) {
        ButterKnife.bind(this,view);
        page = 1;
        iPersenter = new IPersenterImpl(this);

        //设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        xRecyclerView.setLayoutManager(layoutManager);
        int spacingInPixels = 11;
        xRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        adapter = new CompletedAdapter(getActivity());
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
        iPersenter.startRequestget(String.format(Apis.SELECT_ORDER_URL,STATUS,page,COUNT),OrderBean.class);
    }

    @Override
    public void onResume() {
        iPersenter.startRequestget(String.format(Apis.SELECT_ORDER_URL,STATUS,page,COUNT),OrderBean.class);
        super.onResume();
    }

    @Override
    protected int getViewById() {
        return R.layout.fragment_completed;
    }

    @Override
    public void success(Object data) {
        if(data instanceof OrderBean){
            if(page == 1){
                adapter.setData(((OrderBean) data).getOrderList());
            }else {
                adapter.addData(((OrderBean) data).getOrderList());
            }
        }
        page++;
        xRecyclerView.loadMoreComplete();
        xRecyclerView.refreshComplete();
    }

    @Override
    public void failed(String error) {
        VerifyUtils.getInstance().toast(error);
    }
}
