package jx.com.shoppingtrolley_zihenguncle.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.com.shoppingtrolley_zihenguncle.R;
import jx.com.shoppingtrolley_zihenguncle.base.BaseFragment;
import jx.com.shoppingtrolley_zihenguncle.bean.CircleListBean;
import jx.com.shoppingtrolley_zihenguncle.bean.HeadBean;
import jx.com.shoppingtrolley_zihenguncle.circle_adapter.CircleAdapter;
import jx.com.shoppingtrolley_zihenguncle.iteminterval.SpacesItemDecoration;
import jx.com.shoppingtrolley_zihenguncle.persenter.IPersenterImpl;
import jx.com.shoppingtrolley_zihenguncle.url.Apis;
import jx.com.shoppingtrolley_zihenguncle.view.IView;

public class CircleFragment extends BaseFragment implements IView {


    @BindView(R.id.circle_xrecycle)
    XRecyclerView xRecyclerView;
    private IPersenterImpl iPersenter;

    private CircleAdapter circle_adapter;
    private int page;

    @Override
    protected void initData(View view) {
        page = 1;
        ButterKnife.bind(this,view);
        //设置布局管理器
        setRecycleLayout();
        //实例化
        iPersenter = new IPersenterImpl(this);

        circle_adapter = new CircleAdapter(getActivity());
        xRecyclerView.setAdapter(circle_adapter);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                request();
            }

            @Override
            public void onLoadMore() {
                request();
            }
        });
        request();

        circle_adapter.setonclick(new CircleAdapter.setOnClick() {
            @Override
            public void setClick(int id, int whetherGreat, int position) {
                if(whetherGreat==1){
                    cancle(id);
                    circle_adapter.setCancleNum(position);
                }else if(whetherGreat==2){
                    add(id);
                    circle_adapter.setAddNum(position);
                }
            }
        });

    }

    //取消点赞
    private void cancle(int id){
        iPersenter.startRequestDelete(String.format(Apis.CANCLE_CIRCLE_URL,id+""),HeadBean.class);
    }

    //点赞
    private void add(int id){
        Map<String,String> map = new HashMap<>();
        map.put("circleId",id+"");
        iPersenter.startRequest(Apis.ADD_CIRCLR_URL,map,HeadBean.class);
    }

    //请求圈子列表
    public void request(){
        iPersenter.startRequestget(String.format(Apis.CIRCLE_LIST_URL,page,5),CircleListBean.class);
    }



    //设置布局管理器
    private void setRecycleLayout(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        xRecyclerView.setLayoutManager(layoutManager);
        int spacingInPixels = 20;
        xRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
    }

    @Override
    protected int getViewById() {
        return R.layout.fragment_circle;
    }

    @Override
    public void success(Object data) {
        if(data instanceof CircleListBean){
            if (page == 1){
                circle_adapter.setData(((CircleListBean) data).getResult());
            }else {
                circle_adapter.addData(((CircleListBean) data).getResult());
            }
            page++;
            xRecyclerView.refreshComplete();
            xRecyclerView.loadMoreComplete();
        }
        if(data instanceof HeadBean){
            if(((HeadBean) data).getMessage().equals("点赞成功")){
                toast(((HeadBean) data).getMessage());
            }else if(((HeadBean) data).getMessage().equals("取消成功")){
                toast(((HeadBean) data).getMessage());
            }
        }

    }

    @Override
    public void failed(String error) {
        toast(error);
    }

    private void toast(String toast){
        Toast.makeText(getActivity(),toast,Toast.LENGTH_SHORT).show();
    }
}
