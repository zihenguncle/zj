package jx.com.shoppingtrolley_zihenguncle.order.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.com.shoppingtrolley_zihenguncle.R;
import jx.com.shoppingtrolley_zihenguncle.iteminterval.SpacesItemDecoration;
import jx.com.shoppingtrolley_zihenguncle.order.bean.OrderBean;

public class AllOrderAdapter extends RecyclerView.Adapter<AllOrderAdapter.ViewHolder> {

    private List<OrderBean.OrderListBean> data;
    private Context context;
    private AllOrderItemAdapter allOrderItemAdapter;

    public AllOrderAdapter(Context context) {
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
    public AllOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context,R.layout.allorder_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllOrderAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.orderId.setText(data.get(i).getOrderId());
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        viewHolder.recyclerView.setLayoutManager(layoutManager);
        int spacingInPixels = 11;
        viewHolder.recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        int count = 0;
        double price = 0.0;
        for (int j = 0; j < data.get(i).getDetailList().size(); j++) {
            count += data.get(i).getDetailList().get(j).getCommodityCount();
            price += data.get(i).getDetailList().get(j).getCommodityPrice();
        }
        SpannableString spannableString = new SpannableString("共"+count+"件商品");
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0000")), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.totalCount.setText(spannableString);
        SpannableString spannableString1 = new SpannableString("需付款"+ price +"元");
        spannableString1.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0000")), 3, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.totalPrice.setText(spannableString1);
        //适配器
        allOrderItemAdapter = new AllOrderItemAdapter(data.get(i).getDetailList(),context);
        viewHolder.recyclerView.setAdapter(allOrderItemAdapter);

        viewHolder.goto_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gotoPay != null){
                    gotoPay.setPay(data.get(i).getOrderId(),data.get(i).getExpressCompName());
                }
            }
        });
        viewHolder.delete_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(order != null){
                    String orderId = data.get(i).getOrderId();
                    order.setId(orderId);
                }
            }
        });

        int orderStatus = data.get(i).getOrderStatus();
        switch (orderStatus){
            case 1:
                viewHolder.goto_pay.setText("去支付");
                break;
            case 2:
                viewHolder.goto_pay.setText("确定收货");
                break;
            case 3:
                viewHolder.goto_pay.setText("去评价");
                break;
            case 9:
                viewHolder.goto_pay.setText("完成");
                break;
            default:
                break;
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.orderId)
        TextView orderId;
        @BindView(R.id.order_Recycle)
        RecyclerView recyclerView;
        @BindView(R.id.order_total_count)
        TextView totalCount;
        @BindView(R.id.order_total_price)
        TextView totalPrice;
        @BindView(R.id.go_to_pay_order)
        TextView goto_pay;
        @BindView(R.id.cancel_order)
        TextView delete_order;
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
        void setPay(String orderId,String name);
    }

    public DelteleOrder order;
    public void setDeleteOrder(DelteleOrder deleteOrder){
        order = deleteOrder;
    }
    public interface DelteleOrder{
        void setId(String id);
    }
}
