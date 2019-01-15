package jx.com.shoppingtrolley_zihenguncle.homepage_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.com.shoppingtrolley_zihenguncle.R;
import jx.com.shoppingtrolley_zihenguncle.activity.DetailsActivity;
import jx.com.shoppingtrolley_zihenguncle.bean.EventBusBean;
import jx.com.shoppingtrolley_zihenguncle.bean.GoodsBean;
import jx.com.shoppingtrolley_zihenguncle.tag.EventTag;

public class QualityAdapter extends RecyclerView.Adapter<QualityAdapter.ViewHolder> {

    private Context context;
    private List<GoodsBean.ResultBean.PzshBean.CommodityListBeanX> data;

    public QualityAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public void setData(List<GoodsBean.ResultBean.PzshBean.CommodityListBeanX> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public QualityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_quality_fragment,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QualityAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.simpleDraweeView.setImageURI(data.get(i).getMasterPic());
        viewHolder.title.setText(data.get(i).getCommodityName());
        viewHolder.price.setText("￥"+data.get(i).getPrice());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("CommodityId",data.get(i).getCommodityId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_quality_image)
        SimpleDraweeView simpleDraweeView;
        @BindView(R.id.item_quality_title)
        TextView title;
        @BindView(R.id.item_quality_price)
        TextView price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
