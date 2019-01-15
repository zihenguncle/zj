package jx.com.shoppingtrolley_zihenguncle.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.com.shoppingtrolley_zihenguncle.R;
import jx.com.shoppingtrolley_zihenguncle.bean.FootBean;
import jx.com.shoppingtrolley_zihenguncle.foot_adapter.FootAdapter;
import jx.com.shoppingtrolley_zihenguncle.iteminterval.SpacesItemDecoration;
import jx.com.shoppingtrolley_zihenguncle.persenter.IPersenterImpl;
import jx.com.shoppingtrolley_zihenguncle.url.Apis;
import jx.com.shoppingtrolley_zihenguncle.view.IView;

/**
 * @author 郭淄恒
 * 我的足迹
 */
public class MyFootPrint extends AppCompatActivity implements IView {

    @BindView(R.id.foot_xrecycle)
    XRecyclerView xRecyclerView;
    private int page;
    private int spancount = 2;
    IPersenterImpl iPersenter;
    private FootAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_foot_print);
        ButterKnife.bind(this);
        iPersenter = new IPersenterImpl(this);
        //设置布局管理器
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(spancount, StaggeredGridLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(gridLayoutManager);
        int spacingInPixels = 20;
        xRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        page=1;

        adapter = new FootAdapter(this);
        xRecyclerView.setAdapter(adapter);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
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
        iPersenter.startRequestget(String.format(Apis.FOOT_JILU_URL,page,6),FootBean.class);
    }
    @Override
    public void success(Object data) {
        if(data instanceof FootBean){
            if(page == 1){
                adapter.setData(((FootBean) data).getResult());
            }else {
                adapter.addData(((FootBean) data).getResult());
            }
            page++;
            xRecyclerView.loadMoreComplete();
            xRecyclerView.refreshComplete();
        }
    }

    @Override
    public void failed(String error) {
        toast(error);
    }

    private void toast(String toast){
        Toast.makeText(MyFootPrint.this,toast,Toast.LENGTH_SHORT).show();
    }
}
