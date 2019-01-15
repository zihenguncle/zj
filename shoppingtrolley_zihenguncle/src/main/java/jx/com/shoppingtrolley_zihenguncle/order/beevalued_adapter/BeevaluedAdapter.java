package jx.com.shoppingtrolley_zihenguncle.order.beevalued_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.com.shoppingtrolley_zihenguncle.R;
import jx.com.shoppingtrolley_zihenguncle.iteminterval.SpacesItemDecoration;
import jx.com.shoppingtrolley_zihenguncle.order.bean.OrderBean;
import jx.com.shoppingtrolley_zihenguncle.order.take_circle.TakeActivity;

public class BeevaluedAdapter extends RecyclerView.Adapter<BeevaluedAdapter.ViewHolder> {

    private List<OrderBean.OrderListBean> data;
    private Context context;
    private BeevaluedAdapter_item adapter_item;

    public BeevaluedAdapter(Context context) {
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
    public BeevaluedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.beevalued_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BeevaluedAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.orderid.setText(data.get(i).getOrderId());
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        viewHolder.recyclerView.setLayoutManager(layoutManager);
        int spacingInPixels = 11;
        viewHolder.recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        adapter_item = new BeevaluedAdapter_item(data.get(i).getDetailList(),context);
        viewHolder.recyclerView.setAdapter(adapter_item);

        adapter_item.setGotoPay(new BeevaluedAdapter_item.gotoPay() {
            @Override
            public void setPay(int posotion) {
                OrderBean.OrderListBean.DetailListBean detailListBean = data.get(i).getDetailList().get(posotion);
                Intent intent = new Intent(context, TakeActivity.class);
                intent.putExtra("list", (Serializable) detailListBean);
                intent.putExtra("orderId",data.get(i).getOrderId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.orderid_dee_item)
        TextView orderid;
        @BindView(R.id.more_dee_item)
        ImageView more_iamge;
        @BindView(R.id.reycle_dee_item)
        RecyclerView recyclerView;
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
        void setPay(String orderId);
    }
}
