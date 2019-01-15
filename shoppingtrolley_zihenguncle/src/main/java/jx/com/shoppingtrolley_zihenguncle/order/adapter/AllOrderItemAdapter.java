package jx.com.shoppingtrolley_zihenguncle.order.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.com.shoppingtrolley_zihenguncle.R;
import jx.com.shoppingtrolley_zihenguncle.order.bean.OrderBean;

public class AllOrderItemAdapter extends RecyclerView.Adapter<AllOrderItemAdapter.ViewHolder> {

    private List<OrderBean.OrderListBean.DetailListBean> data;
    private Context context;

    public AllOrderItemAdapter(List<OrderBean.OrderListBean.DetailListBean> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public AllOrderItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context,R.layout.order_item_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllOrderItemAdapter.ViewHolder viewHolder, int i) {
        viewHolder.name.setText(data.get(i).getCommodityName());
        viewHolder.price.setText("￥"+data.get(i).getCommodityPrice());
        viewHolder.editText.setText(""+data.get(i).getCommodityCount());
        String[] split = data.get(i).getCommodityPic().split("\\,");
        Glide.with(context).load(split[0]).into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.order_image_item)
        ImageView imageView;
        @BindView(R.id.order_name_item)
        TextView name;
        @BindView(R.id.order_price_item)
        TextView price;
        @BindView(R.id.order_num_item)
        EditText editText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
