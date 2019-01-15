package jx.com.shoppingtrolley_zihenguncle.order.collection_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.com.shoppingtrolley_zihenguncle.R;
import jx.com.shoppingtrolley_zihenguncle.iteminterval.SpacesItemDecoration;
import jx.com.shoppingtrolley_zihenguncle.order.bean.OrderBean;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {

    private List<OrderBean.OrderListBean> data;
    private Context context;
    private CollectionAdapter_item adapter;

    public CollectionAdapter(Context context) {
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
    public CollectionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context,R.layout.collection_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.orderid.setText(data.get(i).getOrderId());
        viewHolder.textView_receive_comoney.setText(data.get(i).getExpressCompName());
        viewHolder.textView_receive_expressSn.setText(data.get(i).getExpressSn());
        //
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        viewHolder.recyclerView.setLayoutManager(layoutManager);
        int spacingInPixels = 11;
        viewHolder.recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        adapter = new CollectionAdapter_item(data.get(i).getDetailList(),context);
        viewHolder.recyclerView.setAdapter(adapter);
        viewHolder.textView_confirm_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gotoPay != null){
                    gotoPay.setPay(data.get(i).getOrderId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.orderid_collection_item)
        TextView orderid;
        @BindView(R.id.reycle_collection_item)
        RecyclerView recyclerView;
        @BindView(R.id.receive_comoney)
        TextView textView_receive_comoney;
        @BindView(R.id.receive_expressSn)
        TextView textView_receive_expressSn;
        @BindView(R.id.confirm_order)
        TextView textView_confirm_order;

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
