package jx.com.shoppingtrolley_zihenguncle.foot_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.com.shoppingtrolley_zihenguncle.R;
import jx.com.shoppingtrolley_zihenguncle.bean.FootBean;

public class FootAdapter extends RecyclerView.Adapter<FootAdapter.ViewHolder> {

    private List<FootBean.ResultBean> data;
    private Context context;

    public FootAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public void setData(List<FootBean.ResultBean> data) {
        this.data.clear();
        if(data!=null){
            this.data.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void addData(List<FootBean.ResultBean> data) {
        if(data!=null){
            this.data.addAll(data);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FootAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context,R.layout.item_footprint,null);
        return new ViewHolder(view);
    }

    public static final String Time_Style = "yyyy-MM-dd HH:mm:ss";

    @Override
    public void onBindViewHolder(@NonNull FootAdapter.ViewHolder viewHolder, int i) {
        Glide.with(context).load(data.get(i).getMasterPic()).into(viewHolder.image);
        viewHolder.content.setText(data.get(i).getCommodityName());
        viewHolder.price.setText("￥"+data.get(i).getPrice());
        viewHolder.browse.setText("已浏览"+data.get(i).getBrowseNum()+"次");
        //解密   时间
        SimpleDateFormat dateFormat = new SimpleDateFormat(Time_Style,Locale.getDefault());
        viewHolder.date.setText(dateFormat.format(data.get(i).getBrowseTime()));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_foot_image)
        ImageView image;
        @BindView(R.id.item_foot_content)
        TextView content;
        @BindView(R.id.item_foot_price)
        TextView price;
        @BindView(R.id.item_foot_browse)
        TextView browse;
        @BindView(R.id.item_foot_date)
        TextView date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
