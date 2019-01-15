package jx.com.shoppingtrolley_zihenguncle.order.completed_adapter;

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

public class CompletedAdapter_item extends RecyclerView.Adapter<CompletedAdapter_item.ViewHolder> {

    private List<OrderBean.OrderListBean.DetailListBean> data;
    private Context context;

    public CompletedAdapter_item(List<OrderBean.OrderListBean.DetailListBean> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public CompletedAdapter_item.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context,R.layout.com_item_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedAdapter_item.ViewHolder viewHolder, int i) {
        viewHolder.name.setText(data.get(i).getCommodityName());
        viewHolder.price.setText("￥"+data.get(i).getCommodityPrice()+"");
        String commodityPic = data.get(i).getCommodityPic();
        final String[] split = commodityPic.split("\\,");
        Glide.with(context).load(split[0]).into(viewHolder.image);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.com_image_pic)
        ImageView image;
        @BindView(R.id.com_goods_name)
        TextView name;
        @BindView(R.id.com_goods_price)
        TextView price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
