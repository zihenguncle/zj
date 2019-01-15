package jx.com.shoppingtrolley_zihenguncle.order.completed_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.com.shoppingtrolley_zihenguncle.R;
import jx.com.shoppingtrolley_zihenguncle.iteminterval.SpacesItemDecoration;
import jx.com.shoppingtrolley_zihenguncle.order.bean.OrderBean;

public class CompletedAdapter extends RecyclerView.Adapter<CompletedAdapter.ViewHolder> {

    private List<OrderBean.OrderListBean> data;
    private Context context;
    private CompletedAdapter_item adapter_item;


    public CompletedAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }
    public void setData(List<OrderBean.OrderListBean> data) {
        this.data.clear();
        if(data != null){
            this.data.addAll(data);
        }
        notifyDataSetChanged();
    }
    public void addData(List<OrderBean.OrderListBean> data) {
        if(data != null){
            this.data.addAll(data);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CompletedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.com_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedAdapter.ViewHolder viewHolder, int i) {
        viewHolder.orderid.setText(data.get(i).getOrderId());
        //
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        viewHolder.recyclerView.setLayoutManager(layoutManager);
        int spacingInPixels = 11;
        viewHolder.recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        adapter_item = new CompletedAdapter_item(data.get(i).getDetailList(),context);
        viewHolder.recyclerView.setAdapter(adapter_item);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.orderid_com_item)
        TextView orderid;
        @BindView(R.id.more_com_item)
        ImageView image_more;
        @BindView(R.id.reycle_com_item)
        RecyclerView recyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
