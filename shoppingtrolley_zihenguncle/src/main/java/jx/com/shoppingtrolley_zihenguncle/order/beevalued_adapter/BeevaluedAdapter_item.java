package jx.com.shoppingtrolley_zihenguncle.order.beevalued_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.com.shoppingtrolley_zihenguncle.R;
import jx.com.shoppingtrolley_zihenguncle.order.bean.OrderBean;
import jx.com.shoppingtrolley_zihenguncle.order.take_circle.TakeActivity;

public class BeevaluedAdapter_item extends RecyclerView.Adapter<BeevaluedAdapter_item.ViewHolder> {

    private List<OrderBean.OrderListBean.DetailListBean> data;
    private Context context;

    public BeevaluedAdapter_item(List<OrderBean.OrderListBean.DetailListBean> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public BeevaluedAdapter_item.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context,R.layout.beevalued_item_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BeevaluedAdapter_item.ViewHolder viewHolder, final int i) {
        viewHolder.name.setText(data.get(i).getCommodityName());
        viewHolder.price.setText("￥"+data.get(i).getCommodityPrice()+"");
        String commodityPic = data.get(i).getCommodityPic();
        final String[] split = commodityPic.split("\\,");

        Glide.with(context).load(split[0]).into(viewHolder.image);
        viewHolder.deevaled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gotoPay != null){
                    gotoPay.setPay(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.dee_image_pic)
        ImageView image;
        @BindView(R.id.dee_goods_name)
        TextView name;
        @BindView(R.id.dee_goods_price)
        TextView price;
        @BindView(R.id.dee_text_dee)
        TextView deevaled;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public gotoPay gotoPay;
    public void setGotoPay(gotoPay gotoPay){
        this.gotoPay = gotoPay;
    }
    public interface gotoPay{
        void setPay(int posotion);
    }

}
