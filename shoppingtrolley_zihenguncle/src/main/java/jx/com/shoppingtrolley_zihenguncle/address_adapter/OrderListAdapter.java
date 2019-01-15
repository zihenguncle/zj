package jx.com.shoppingtrolley_zihenguncle.address_adapter;

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
import jx.com.shoppingtrolley_zihenguncle.bean.CarBean;
import jx.com.shoppingtrolley_zihenguncle.customview.ShopCar_add_minus;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {

    private List<CarBean.Result> results;
    private Context context;

    public OrderListAdapter(List<CarBean.Result> results, Context context) {
        this.results = results;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context,R.layout.orderlistadapter_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListAdapter.ViewHolder viewHolder, int i) {
        viewHolder.name.setText(results.get(i).getCommodityName());
        viewHolder.price.setText(results.get(i).getPrice()+"");
        Glide.with(context).load(results.get(i).getPic()).into(viewHolder.imageView);
        viewHolder.edit.setText(results.get(i).getCount()+"");
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.order_goods_name_item)
        TextView name;
        @BindView(R.id.order_image_pic_item)
        ImageView imageView;
        @BindView(R.id.order_goods_price_item)
        TextView price;
        @BindView(R.id.order_edit_num_item)
        EditText edit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
