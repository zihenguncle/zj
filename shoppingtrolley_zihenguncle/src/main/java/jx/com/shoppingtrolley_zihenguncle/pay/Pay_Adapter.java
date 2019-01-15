package jx.com.shoppingtrolley_zihenguncle.pay;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.com.shoppingtrolley_zihenguncle.R;

public class Pay_Adapter extends RecyclerView.Adapter<Pay_Adapter.ViewHolder> {

    private List<PayBean.ResultBean.DetailListBean> data;
    private Context context;

    public Pay_Adapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public void setData(List<PayBean.ResultBean.DetailListBean> data) {
       this.data.clear();
       if(data != null){
           this.data.addAll(data);
       }
       notifyDataSetChanged();
    }
    public void addData(List<PayBean.ResultBean.DetailListBean> data) {
        this.data.clear();
        if(data != null){
            this.data.addAll(data);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Pay_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context,R.layout.pay_item,null);
        return new ViewHolder(view);
    }

    //将发布时间 long类型转换
    public static final String QuanZi_FaBu_Time = "yyyy-MM-dd HH:mm:ss";
    @Override
    public void onBindViewHolder(@NonNull Pay_Adapter.ViewHolder viewHolder, int i) {
        viewHolder.price.setText(data.get(i).getAmount()+"");
        SimpleDateFormat dateFormat = new SimpleDateFormat(QuanZi_FaBu_Time, Locale.getDefault());
        viewHolder.date.setText(dateFormat.format(data.get(i).getCreateTime()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.pay_price)
        TextView price;
        @BindView(R.id.pay_date)
        TextView date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
