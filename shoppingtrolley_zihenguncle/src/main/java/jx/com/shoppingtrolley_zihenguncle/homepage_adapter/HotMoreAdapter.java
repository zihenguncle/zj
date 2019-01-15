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
import jx.com.shoppingtrolley_zihenguncle.bean.HotMoreBean;
import jx.com.shoppingtrolley_zihenguncle.tag.EventTag;

public class HotMoreAdapter extends RecyclerView.Adapter<HotMoreAdapter.ViewHolder> {

    private List<HotMoreBean.ResultBean> data;
    private Context context;

    public HotMoreAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public void setData(List<HotMoreBean.ResultBean> data) {
        this.data.clear();
        if(data != null){
            this.data.addAll(data);
        }
        notifyDataSetChanged();
    }
    public void addData(List<HotMoreBean.ResultBean> data) {
        if(data != null){
            this.data.addAll(data);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HotMoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.moreitem,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotMoreAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.title.setText(data.get(i).getCommodityName());
        viewHolder.price.setText("￥"+data.get(i).getPrice());
        viewHolder.shownum.setText("已售"+data.get(i).getSaleNum()+"件");
        viewHolder.simpleDraweeView.setImageURI(data.get(i).getMasterPic());
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
        @BindView(R.id.more_simple)
        SimpleDraweeView simpleDraweeView;
        @BindView(R.id.more_title)
        TextView title;
        @BindView(R.id.more_price)
        TextView price;
        @BindView(R.id.more_shownum)
        TextView shownum;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
