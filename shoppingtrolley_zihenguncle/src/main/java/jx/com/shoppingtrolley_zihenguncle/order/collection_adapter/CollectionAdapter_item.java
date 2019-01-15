package jx.com.shoppingtrolley_zihenguncle.order.collection_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.com.shoppingtrolley_zihenguncle.R;
import jx.com.shoppingtrolley_zihenguncle.order.bean.OrderBean;

public class CollectionAdapter_item extends RecyclerView.Adapter<CollectionAdapter_item.ViewHolder> {
    private List<OrderBean.OrderListBean.DetailListBean> data;
    private Context context;

    public CollectionAdapter_item(List<OrderBean.OrderListBean.DetailListBean> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public CollectionAdapter_item.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context,R.layout.col_item_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionAdapter_item.ViewHolder viewHolder, int i) {
        viewHolder.textView_name.setText(data.get(i).getCommodityName());
        viewHolder.textView_price.setText("￥"+data.get(i).getCommodityPrice()+"");
        String commodityPic = data.get(i).getCommodityPic();
        String[] split = commodityPic.split("\\,");
        Glide.with(context).load(split[0]).into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_pic)
        ImageView imageView;
        @BindView(R.id.goods_name)
        TextView textView_name;
        @BindView(R.id.goods_price)
        TextView textView_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
