package jx.com.shoppingtrolley_zihenguncle.order.take_circle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.com.shoppingtrolley_zihenguncle.R;
import jx.com.shoppingtrolley_zihenguncle.base.BaseActivity;
import jx.com.shoppingtrolley_zihenguncle.bean.AddCarBean;
import jx.com.shoppingtrolley_zihenguncle.order.bean.OrderBean;
import jx.com.shoppingtrolley_zihenguncle.persenter.IPersenterImpl;
import jx.com.shoppingtrolley_zihenguncle.tool.VerifyUtils;
import jx.com.shoppingtrolley_zihenguncle.url.Apis;
import jx.com.shoppingtrolley_zihenguncle.view.IView;
import okhttp3.RequestBody;

public class TakeActivity extends BaseActivity implements IView {

    @BindView(R.id.take_image)
    ImageView image;
    @BindView(R.id.take_name)
    TextView name;
    @BindView(R.id.take_price)
    TextView price;
    @BindView(R.id.take_content)
    EditText edit_take;
    @BindView(R.id.take_image_camera)
    ImageView take_image;
    @BindView(R.id.check_circle)
    CheckBox syn_circle;
    @BindView(R.id.publish_take)
    Button publish;
    IPersenterImpl iPersenter;

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        iPersenter = new IPersenterImpl(this);
        Intent intent = getIntent();

        final String orderId = intent.getStringExtra("orderId");
        final OrderBean.OrderListBean.DetailListBean list = (OrderBean.OrderListBean.DetailListBean) intent.getSerializableExtra("list");
        final String[] split = list.getCommodityPic().split("\\,");
        Glide.with(this).load(split[0]).into(image);
        Glide.with(this).load(split[2]).into(take_image);
        name.setText(list.getCommodityName());
        price.setText("￥"+list.getCommodityPrice());
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,String> map = new HashMap<>();
                map.put("commodityId",list.getCommodityId()+"");
                map.put("orderId",orderId);
                map.put("content",edit_take.getText().toString());
                map.put("image",null);
                iPersenter.startRequest(Apis.TAKE_URL,map,AddCarBean.class);
                if(syn_circle.isChecked()){
                    Map<String,String> map1 = new HashMap<>();
                    map1.put("commodityId",list.getCommodityId()+"");
                    map1.put("content",edit_take.getText().toString());
                    map1.put("image",null);
                    iPersenter.startRequest(Apis.ADD_CIRLE,map1,AddCarBean.class);
                }
            }
        });
    }

    @Override
    protected int getViewById() {
        return R.layout.activity_take;
    }

    @Override
    public void success(Object data) {
        if (data instanceof AddCarBean){
            VerifyUtils.getInstance().toast(((AddCarBean) data).getMessage());
        }
    }

    @Override
    public void failed(String error) {
        VerifyUtils.getInstance().toast(error);
    }
}
