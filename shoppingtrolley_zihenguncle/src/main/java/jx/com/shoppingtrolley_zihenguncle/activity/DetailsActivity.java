package jx.com.shoppingtrolley_zihenguncle.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.stx.xhb.xbanner.XBanner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jx.com.shoppingtrolley_zihenguncle.R;
import jx.com.shoppingtrolley_zihenguncle.addcar.AddBean;
import jx.com.shoppingtrolley_zihenguncle.base.BaseActivity;
import jx.com.shoppingtrolley_zihenguncle.bean.AddCarBean;
import jx.com.shoppingtrolley_zihenguncle.bean.CarBean;
import jx.com.shoppingtrolley_zihenguncle.bean.DetailsBean;
import jx.com.shoppingtrolley_zihenguncle.bean.DiscussBean;
import jx.com.shoppingtrolley_zihenguncle.bean.EventBusBean;
import jx.com.shoppingtrolley_zihenguncle.homepage_adapter.DeatilsAdapter;
import jx.com.shoppingtrolley_zihenguncle.persenter.IPersenterImpl;
import jx.com.shoppingtrolley_zihenguncle.tag.EventTag;
import jx.com.shoppingtrolley_zihenguncle.url.Apis;
import jx.com.shoppingtrolley_zihenguncle.view.IView;

/**
 * @author 郭淄恒
 */
public class DetailsActivity extends BaseActivity implements IView {

    @BindView(R.id.details_banner)
    XBanner xBanner;
    @BindView(R.id.details_price)
    TextView price;
    @BindView(R.id.details_commentNum)
    TextView commentnum;
    @BindView(R.id.details_name)
    TextView name;
    @BindView(R.id.details_weight)
    TextView weight;
    @BindView(R.id.details_webview)
    WebView webView;
    IPersenterImpl iPersenter;
    @BindView(R.id.details_recycle)
    RecyclerView recyclerView;

    @BindView(R.id.tab_back)
    ImageView image_back;

    private DeatilsAdapter adapter;
    private int commodityId;

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        //拿到ID
        iPersenter = new IPersenterImpl(this);
        Intent intent = getIntent();
        commodityId = intent.getIntExtra("CommodityId", 0);
        //设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        iPersenter.startRequestget(String.format(Apis.DETAILS_URL, commodityId),DetailsBean.class);
        iPersenter.startRequestget(String.format(Apis.DISCUSS_Url, commodityId,1,5),DiscussBean.class);


    }

    @OnClick({R.id.tab_back,R.id.button_add_shoppingcar})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tab_back:
                finish();
                break;
            case R.id.button_add_shoppingcar:
                /*Map<String,String> map = new HashMap<>();
                map.put("data","[{'commodityId':+"+commodityId+"+,'count':+"+1+"+}}]");
                iPersenter.startRequestPut(Apis.ADDCAR_URL,map,AddCarBean.class);*/
                iPersenter.startRequestget(Apis.SELECTCAR_URL,CarBean.class);
                break;
                default:
                    break;
        }
    }

    private void addCar(List<AddBean> list){
        String string="[";
        if(list.size()==0){
            list.add(new AddBean(commodityId,1));
        }else {
            for (int i=0;i<list.size();i++){
                if(Integer.valueOf(commodityId)==list.get(i).getCommodityId()){
                    int count = list.get(i).getCount();
                    count++;
                    list.get(i).setCount(count);
                    break;
                }else if(i==list.size()-1){
                    list.add(new AddBean(Integer.valueOf(commodityId),1));
                    break;
                }
            }
        }
        for (AddBean resultBean:list){
            string+="{\"commodityId\":"+resultBean.getCommodityId()+",\"count\":"+resultBean.getCount()+"},";
        }
        String substring = string.substring(0, string.length() - 1);
        substring+="]";
        Log.i("TAG",substring);
        Map<String,String> map=new HashMap<>();
        map.put("data",substring);
        iPersenter.startRequestPut(Apis.ADDCAR_URL,map,AddCarBean.class);
    }
    @Override
    protected int getViewById() {
        return R.layout.activity_details;
    }

    @Override
    public void success(Object data) {
        if(data instanceof DetailsBean){
            DetailsBean bean = (DetailsBean) data;
            price.setText("￥"+bean.getResult().getPrice());
            commentnum.setText("已售"+bean.getResult().getCommentNum()+"件");
            name.setText(bean.getResult().getCategoryName());
            weight.setText("重量    "+bean.getResult().getWeight()+"kg");
            webView.loadDataWithBaseURL(null, bean.getResult().getDetails(), "text/html", "utf-8", null);
            String picture = bean.getResult().getPicture();
            String[] split = picture.split("\\,");
            final List<String> list = new ArrayList<>();
            for (int i = 0; i < split.length; i++) {
                list.add(split[i]);
            }
            xBanner.setData(list,null);
            xBanner.loadImage(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    Glide.with(DetailsActivity.this).load(list.get(position)).into((ImageView) view);
                    xBanner.setPageChangeDuration(1000);
                }
            });
        }
        if(data instanceof DiscussBean){
            adapter = new DeatilsAdapter(((DiscussBean) data).getResult(),this);
            recyclerView.setAdapter(adapter);
        }
        if(data instanceof AddCarBean){
            if(((AddCarBean) data).getMessage().equals("同步成功")){
                toast("成功加入购物车");
            }
        }
        if (data instanceof CarBean){
            CarBean bean = (CarBean) data;
            List<AddBean> list=new ArrayList<>();
            List<CarBean.Result> result = bean.getResult();
            for(CarBean.Result results:result){
                list.add(new AddBean(results.getCommodityId(),results.getCount()));
            }
            //把查到的数据 传给添加购物车方法 并判断
            addCar(list);
        }
    }

    @Override
    public void failed(String error) {
        toast(error);
    }

    private void toast(String s){
        Toast.makeText(DetailsActivity.this,s+"",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.setVisibility(View.GONE);
            webView.removeAllViews();
            webView.destroy();
        }
    }

}
