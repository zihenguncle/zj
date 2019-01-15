package jx.com.shoppingtrolley_zihenguncle.homepage_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class HotsellAdapter extends RecyclerView.Adapter<HotsellAdapter.ViewHolder> {

    private List<GoodsBean.ResultBean.RxxpBean.CommodityListBean> mdata;
    private Context context;

    public HotsellAdapter(Context context) {
        mdata = new ArrayList<>();
        this.context = context;
    }

    public void setMdata(List<GoodsBean.ResultBean.RxxpBean.CommodityListBean> mdata) {
        this.mdata = mdata;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HotsellAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_hotsell_fragment,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HotsellAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.title.setText(mdata.get(i).getCommodityName());
        viewHolder.pricce.setText("￥"+mdata.get(i).getPrice());
        viewHolder.imageView.setImageURI(mdata.get(i).getMasterPic());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("CommodityId",mdata.get(i).getCommodityId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.hot_item_image)
        SimpleDraweeView imageView;
        @BindView(R.id.hot_item_title)
        TextView title;
        @BindView(R.id.hot_item_price)
        TextView pricce;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
