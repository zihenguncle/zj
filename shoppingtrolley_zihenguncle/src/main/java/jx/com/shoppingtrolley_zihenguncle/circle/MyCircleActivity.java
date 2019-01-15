package jx.com.shoppingtrolley_zihenguncle.circle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.com.shoppingtrolley_zihenguncle.R;
import jx.com.shoppingtrolley_zihenguncle.base.BaseActivity;
import jx.com.shoppingtrolley_zihenguncle.circle.adapter.MyCircleAdapter;
import jx.com.shoppingtrolley_zihenguncle.circle.bean.MyCircleBean;
import jx.com.shoppingtrolley_zihenguncle.iteminterval.SpacesItemDecoration;
import jx.com.shoppingtrolley_zihenguncle.persenter.IPersenterImpl;
import jx.com.shoppingtrolley_zihenguncle.tool.VerifyUtils;
import jx.com.shoppingtrolley_zihenguncle.url.Apis;
import jx.com.shoppingtrolley_zihenguncle.view.IView;

public class MyCircleActivity extends BaseActivity implements IView {

    @BindView(R.id.my_circle_xrecycle)
    XRecyclerView xRecyclerView;

    private IPersenterImpl iPersenter;
    private int page;
    private MyCircleAdapter adapter;
    @Override
    protected void initData() {
        ButterKnife.bind(this);
        iPersenter = new IPersenterImpl(this);
        page = 1;
        //设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        xRecyclerView.setLayoutManager(layoutManager);
        int spacingInPixels = 10;
        xRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        adapter = new MyCircleAdapter(this);
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
        iPersenter.startRequestget(String.format(Apis.MYCIRCLE_URL,page,5),MyCircleBean.class);
    }
    @Override
    public void success(Object data) {
        if(data instanceof MyCircleBean){
            if(page == 1){
                adapter.setData(((MyCircleBean) data).getResult());
            }else {
                adapter.addData(((MyCircleBean) data).getResult());
            }
            page++;
            xRecyclerView.refreshComplete();
            xRecyclerView.loadMoreComplete();
        }
    }

    @Override
    public void failed(String error) {
        VerifyUtils.getInstance().toast(error);
    }
    @Override
    protected int getViewById() {
        return R.layout.activity_my_circle;
    }


}
