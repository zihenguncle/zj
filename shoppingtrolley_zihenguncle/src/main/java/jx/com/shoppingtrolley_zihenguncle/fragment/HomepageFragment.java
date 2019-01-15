package jx.com.shoppingtrolley_zihenguncle.fragment;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.stx.xhb.xbanner.XBanner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jx.com.shoppingtrolley_zihenguncle.R;
import jx.com.shoppingtrolley_zihenguncle.base.BaseFragment;
import jx.com.shoppingtrolley_zihenguncle.bean.BannerBean;
import jx.com.shoppingtrolley_zihenguncle.bean.EventBusBean;
import jx.com.shoppingtrolley_zihenguncle.bean.GoodsBean;
import jx.com.shoppingtrolley_zihenguncle.bean.HeadBean;
import jx.com.shoppingtrolley_zihenguncle.bean.HeadTwoBean;
import jx.com.shoppingtrolley_zihenguncle.bean.HotMoreBean;
import jx.com.shoppingtrolley_zihenguncle.bean.MoreBean;
import jx.com.shoppingtrolley_zihenguncle.homepage_adapter.HeadOneAdapter;
import jx.com.shoppingtrolley_zihenguncle.homepage_adapter.HeadTwoAdapter;
import jx.com.shoppingtrolley_zihenguncle.homepage_adapter.HotMoreAdapter;
import jx.com.shoppingtrolley_zihenguncle.homepage_adapter.HotsellAdapter;
import jx.com.shoppingtrolley_zihenguncle.homepage_adapter.MagisAdapter;
import jx.com.shoppingtrolley_zihenguncle.homepage_adapter.MoreAdapter;
import jx.com.shoppingtrolley_zihenguncle.homepage_adapter.QualityAdapter;
import jx.com.shoppingtrolley_zihenguncle.iteminterval.SpacesItemDecoration;
import jx.com.shoppingtrolley_zihenguncle.persenter.IPersenterImpl;
import jx.com.shoppingtrolley_zihenguncle.tag.EventTag;
import jx.com.shoppingtrolley_zihenguncle.url.Apis;
import jx.com.shoppingtrolley_zihenguncle.view.IView;

/**
 * @author 郭淄恒
 * 主页
 */
public class HomepageFragment extends BaseFragment implements IView {

    @BindView(R.id.homepage_edit_search)
    EditText edit_search;
    @BindView(R.id.homepage_text_search)
    TextView text_search;
    @BindView(R.id.homepage_image_search)
    ImageView image_search;
    @BindView(R.id.homepage_frag_viewpager)
    XBanner viewPager;
    //热门的recycle
    @BindView(R.id.homepage_hotsell_recycle)
    RecyclerView hot_recycle;
    //三个小圆点(更多商品）
    @BindView(R.id.hot_image_but_moer)
    ImageView hot_image_but_moer;
    @BindView(R.id.homepage_magic_recycle)
    RecyclerView magis_recycle;
    @BindView(R.id.homepage_quality_recycle)
    RecyclerView quality_recycle;
    private IPersenterImpl iPersenter;
    @BindView(R.id.menu)
    ImageView image_menu;

    private HotsellAdapter hot_adapter;
    private MagisAdapter magis_adapter;
    private QualityAdapter quality_adapter;
    private HeadOneAdapter head_adapter;
    private HeadTwoAdapter headtwo_adapter;

    private HotMoreAdapter moreAdapter;
    @BindView(R.id.homepage_scroll)
    ScrollView scrollView;

    @BindView(R.id.xq_relative)
    RelativeLayout relativeLayout;

    @BindView(R.id.hot_recycle)
    XRecyclerView hot_recycle_more;
    private int hot_id;
    private  int gric_id;
    private int quali_id;
    @BindView(R.id.magic_recycle)
    XRecyclerView magic_recycle_more;
    @BindView(R.id.magic_image_but_moer)
    ImageView magic_more;
    @BindView(R.id.xq1_relative)
    RelativeLayout xq1_relative;
    @BindView(R.id.xq2_relative)
    RelativeLayout xq2_relative;
    @BindView(R.id.quality_recycle)
    XRecyclerView qualityrecycle_more;
    @BindView(R.id.quality_image_but_moer)
    ImageView qua_image_more;
    @BindView(R.id.xq1_back)
    ImageView xq1_back;
    @BindView(R.id.xq_back)
    ImageView xq_back;
    @BindView(R.id.xq3_back)
    ImageView xq3_back;
    @BindView(R.id.home_more_xrecycle)
    XRecyclerView more_recyclerView;
    private MoreAdapter moreadapter;
    @BindView(R.id.no_relative)
    RelativeLayout no_relative;
    private int page;
    @Override
    protected void initData(View view) {
        //绑定
        ButterKnife.bind(this,view);
        //发送消息
        iPersenter = new IPersenterImpl(this);

        //Banner轮播
        setBannerViewpager();

        //设置布局管理器
        setrecycleLayout();

        //RecycleView item边距
        setRecycleItemView();

        //订阅
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }

        iPersenter.startRequestget(Apis.GOODS_URL,GoodsBean.class);
        hot_adapter = new HotsellAdapter(getActivity());
        hot_recycle.setAdapter(hot_adapter);
        magis_adapter = new MagisAdapter(getActivity());
        magis_recycle.setAdapter(magis_adapter);
        quality_adapter = new QualityAdapter(getActivity());
        quality_recycle.setAdapter(quality_adapter);


        moreAdapter = new HotMoreAdapter(getActivity());

        hot_recycle_more.setAdapter(moreAdapter);
        magic_recycle_more.setAdapter(moreAdapter);
        qualityrecycle_more.setAdapter(moreAdapter);

        moreadapter = new MoreAdapter(getActivity());
        more_recyclerView.setAdapter(moreadapter);

    }

    //RecycleView item边距
    private void setRecycleItemView() {
        int spacingInPixels = 10;
        hot_recycle.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        magis_recycle.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        quality_recycle.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        hot_recycle_more.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        magic_recycle_more.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        qualityrecycle_more.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        more_recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
    }


    //设置布局管理器
    private void setrecycleLayout() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        hot_recycle.setLayoutManager(layoutManager);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        magis_recycle.setLayoutManager(layoutManager1);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        quality_recycle.setLayoutManager(gridLayoutManager);

        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager1.setOrientation(OrientationHelper.VERTICAL);
        hot_recycle_more.setLayoutManager(gridLayoutManager1);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager2.setOrientation(OrientationHelper.VERTICAL);
        magic_recycle_more.setLayoutManager(gridLayoutManager2);
        GridLayoutManager gridLayoutManager3 = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager3.setOrientation(OrientationHelper.VERTICAL);
        qualityrecycle_more.setLayoutManager(gridLayoutManager3);
        GridLayoutManager gridLayoutManager4 = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager4.setOrientation(OrientationHelper.VERTICAL);
        more_recyclerView.setLayoutManager(gridLayoutManager4);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBus(EventBusBean eventBusBean){
        if(eventBusBean.getTag() == EventTag.ID_HEAD_TAG){
            Object obj = eventBusBean.getObj();
            String s = obj.toString();

        }
    }

    //Banner轮播
    private void setBannerViewpager() {
        iPersenter.startRequestget(Apis.BANNER_URL,BannerBean.class);
    }

    //点击事件
   @OnClick({R.id.homepage_image_search,R.id.homepage_text_search,R.id.hot_image_but_moer,R.id.menu,
           R.id.magic_image_but_moer,R.id.quality_image_but_moer,R.id.xq1_back,R.id.xq_back,R.id.xq3_back})
   public void setClick(View view){
        switch (view.getId()){
            //点击展示隐藏搜索栏
            case R.id.homepage_image_search:
                //显示输入框和搜索按钮
                setSearchVisibility();
                break;
            case R.id.homepage_text_search:
                //点击搜索进行展示信息或隐藏
                setTextSearchVisibility();
                break;
            case R.id.hot_image_but_moer:
                //请求 热门 数据，展示更多
                getHotData();
                break;
            case R.id.menu:
                getPopup();
                break;
            case R.id.magic_image_but_moer:
                getmagicData();
                break;
            case R.id.quality_image_but_moer:
                getqualityData();
                break;
            case R.id.xq_back:
                back();
                break;
            case R.id.xq1_back:
                back();
                break;
            case R.id.xq3_back:
                back();
                break;
            default:
                    break;
        }
   }

   private PopupWindow popupWindow;
    private void getPopup() {
        View view = View.inflate(getActivity(), R.layout.popup_head, null);
        //第一个标签
        RecyclerView head_one = view.findViewById(R.id.home_head_recycle);
        head_one.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        head_adapter = new HeadOneAdapter(getActivity());
        head_one.setAdapter(head_adapter);
        iPersenter.startRequestget(Apis.ERJI_URL,HeadBean.class);

        //第二个标签
        RecyclerView head_two = view.findViewById(R.id.home_head_two_recycle);
        head_two.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        headtwo_adapter = new HeadTwoAdapter(getActivity());
        head_two.setAdapter(headtwo_adapter);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(image_menu);
        iPersenter.startRequestget(String.format(Apis.ERJI_ITEM_URL,"1001002"),HeadTwoBean.class);
        head_adapter.onclick(new HeadOneAdapter.setonClick() {
            @Override
            public void setId(String id) {
                iPersenter.startRequestget(String.format(Apis.ERJI_ITEM_URL,id),HeadTwoBean.class);
                headtwo_adapter.onclick(new HeadTwoAdapter.setonClick() {
                    @Override
                    public void setId(String id) {
                        getXrecycle(Apis.ERJI_SHOW_URL,id);
                        xq2_relative.setVisibility(View.INVISIBLE);
                        xq1_relative.setVisibility(View.INVISIBLE);
                        relativeLayout.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
    }

    private int hot_page;
    private void getqualityData() {
        hot_page = 1;
        xq2_relative.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.INVISIBLE);
        qualityrecycle_more.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                hot_page=1;
                iPersenter.startRequestget(String.format(Apis.HOT_LABLE_URL,quali_id+"",hot_page,6),HotMoreBean.class);
            }

            @Override
            public void onLoadMore() {
                iPersenter.startRequestget(String.format(Apis.HOT_LABLE_URL,quali_id+"",hot_page,6),HotMoreBean.class);
            }
        });
        iPersenter.startRequestget(String.format(Apis.HOT_LABLE_URL,quali_id+"",hot_page,6),HotMoreBean.class);

    }

    private void getmagicData() {
        hot_page = 1;
        xq1_relative.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.INVISIBLE);
        //请求更多的数据
        magic_recycle_more.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                hot_page=1;
                iPersenter.startRequestget(String.format(Apis.HOT_LABLE_URL,gric_id+"",hot_page,6),HotMoreBean.class);
            }

            @Override
            public void onLoadMore() {
                iPersenter.startRequestget(String.format(Apis.HOT_LABLE_URL,gric_id+"",hot_page,6),HotMoreBean.class);
            }
        });
        iPersenter.startRequestget(String.format(Apis.HOT_LABLE_URL,gric_id+"",hot_page,6),HotMoreBean.class);

    }


    //请求热门数据
    private void getHotData() {
        hot_page = 1;
            relativeLayout.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.INVISIBLE);
            hot_recycle_more.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
                    hot_page=1;
                    iPersenter.startRequestget(String.format(Apis.HOT_LABLE_URL,hot_id+"",hot_page,6),HotMoreBean.class);
                }

                @Override
                public void onLoadMore() {
                    iPersenter.startRequestget(String.format(Apis.HOT_LABLE_URL,hot_id+"",hot_page,6),HotMoreBean.class);
                }
            });
            //请求更多的数据
            iPersenter.startRequestget(String.format(Apis.HOT_LABLE_URL,hot_id+"",hot_page,6),HotMoreBean.class);
    }
    //返回
    private void back(){
        xq2_relative.setVisibility(View.INVISIBLE);
        xq1_relative.setVisibility(View.INVISIBLE);
        relativeLayout.setVisibility(View.INVISIBLE);
        more_recyclerView.setVisibility(View.INVISIBLE);
        no_relative.setVisibility(View.INVISIBLE);
        edit_search.setText("");
        scrollView.setVisibility(View.VISIBLE);
    }
    //点击搜索进行展示信息或隐藏------------搜索更多数据
    private void setTextSearchVisibility() {
        if(edit_search.getText().toString().equals("")){
            edit_search.setVisibility(View.INVISIBLE);
            text_search.setVisibility(View.INVISIBLE);
            image_search.setVisibility(View.VISIBLE);
        }else {
            String s = edit_search.getText().toString();
            getXrecycle(Apis.MORE_URL,s);
            xq2_relative.setVisibility(View.INVISIBLE);
            xq1_relative.setVisibility(View.INVISIBLE);
            relativeLayout.setVisibility(View.INVISIBLE);
        }
    }

    //搜索的xRecycle
    private void getXrecycle(final String url, final String s) {
        page = 1;

        more_recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;

                iPersenter.startRequestget(String.format(url,s,page,7),MoreBean.class);
            }

            @Override
            public void onLoadMore() {
                iPersenter.startRequestget(String.format(url,s,page,7),MoreBean.class);
            }
        });
        iPersenter.startRequestget(String.format(url,s,page,7),MoreBean.class);
    }

    //显示输入框和搜索按钮
    private void setSearchVisibility() {
        edit_search.setVisibility(View.VISIBLE);
        text_search.setVisibility(View.VISIBLE);
        image_search.setVisibility(View.INVISIBLE);
    }


    @Override
    protected int getViewById() {
        return R.layout.fragment_homepage;
    }

    @Override
    public void success(Object data) {
        goBack();
        if(data instanceof BannerBean){
            BannerBean bannerBean = (BannerBean) data;
            viewPager.setData(bannerBean.getResult(),null);
            viewPager.loadImage(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    BannerBean.ResultBean bean = (BannerBean.ResultBean) model;
                    Glide.with(getActivity()).load(bean.getImageUrl()).into((ImageView) view);
                    banner.setPageChangeDuration(2000);
                }
            });
        }
        if(data instanceof GoodsBean){
            GoodsBean bean = (GoodsBean) data;
            hot_id = bean.getResult().getRxxp().get(0).getId();
            gric_id = bean.getResult().getMlss().get(0).getId();
            quali_id = bean.getResult().getPzsh().get(0).getId();
            hot_adapter.setMdata(bean.getResult().getRxxp().get(0).getCommodityList());
            magis_adapter.setData(bean.getResult().getMlss().get(0).getCommodityList());
            quality_adapter.setData(bean.getResult().getPzsh().get(0).getCommodityList());
        }
        if(data instanceof HeadBean){
            HeadBean bean = (HeadBean) data;
            head_adapter.setData(bean.getResult());
        }
        if(data instanceof HeadTwoBean){
            HeadTwoBean bean = (HeadTwoBean) data;
            headtwo_adapter.setData(bean.getResult());
        }

        if(data instanceof HotMoreBean){
            if(hot_page == 1){
                moreAdapter.setData(((HotMoreBean) data).getResult());
            }else {
                moreAdapter.addData(((HotMoreBean) data).getResult());
            }
            hot_page++;
            hot_recycle_more.loadMoreComplete();
            hot_recycle_more.refreshComplete();
            magic_recycle_more.loadMoreComplete();
            magic_recycle_more.refreshComplete();
            qualityrecycle_more.loadMoreComplete();
            qualityrecycle_more.refreshComplete();

        }
        if(data instanceof MoreBean){
            if(((MoreBean) data).getResult().size()==0&&page==1){
                no_relative.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.INVISIBLE);
            }else {
                scrollView.setVisibility(View.INVISIBLE);
                more_recyclerView.setVisibility(View.VISIBLE);
                no_relative.setVisibility(View.INVISIBLE);}
            if(page==1){
                moreadapter.setData(((MoreBean) data).getResult());
            }else{
                moreadapter.addData(((MoreBean) data).getResult());
            }
            page++;
            more_recyclerView.refreshComplete();
            more_recyclerView.loadMoreComplete();
        }

    }

    @Override
    public void failed(String error) {
        toast(error);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        iPersenter.detach();
        EventBus.getDefault().unregister(this);
    }



    private long exitTime = 0;
    public void goBack(){

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    back();
                    return true;//当fragment消费了点击事件后，返回true，activity中的点击事件就不会执行了
                }
                if(scrollView.getVisibility()==View.VISIBLE) {
                    if (System.currentTimeMillis() - exitTime > 2000) {
                        exitTime = System.currentTimeMillis();
                        toast("再点一下退出商城");
                    } else {
                        //启动一个意图,回到桌面
                        Intent backHome = new Intent(Intent.ACTION_MAIN);
                        backHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        backHome.addCategory(Intent.CATEGORY_HOME);
                        startActivity(backHome);
                    }
                }

                return false;//当fragmenet没有消费点击事件，返回false，activity中继续执行对应的逻辑

            }
        });
    }

    private void toast(String toast){
        Toast.makeText(getActivity(),toast,Toast.LENGTH_SHORT).show();
    }


}
