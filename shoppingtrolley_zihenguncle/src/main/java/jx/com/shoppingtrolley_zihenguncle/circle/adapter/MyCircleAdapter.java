package jx.com.shoppingtrolley_zihenguncle.circle.adapter;

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
import jx.com.shoppingtrolley_zihenguncle.circle.bean.MyCircleBean;

public class MyCircleAdapter extends RecyclerView.Adapter<MyCircleAdapter.ViewHolder> {

    List<MyCircleBean.ResultBean> data;
    Context context;

    public MyCircleAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public void setData(List<MyCircleBean.ResultBean> data) {
        this.data.clear();
        if(data != null){
            this.data.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void addData(List<MyCircleBean.ResultBean> data) {
        if(data != null){
            this.data.addAll(data);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyCircleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.my_circle_item,null);
        return new ViewHolder(view);
    }
    public static final String Time_Style = "yyyy-MM-dd HH:mm:ss";

    @Override
    public void onBindViewHolder(@NonNull MyCircleAdapter.ViewHolder viewHolder, int i) {
        viewHolder.content.setText(data.get(i).getContent());
        final SimpleDateFormat dateFormat = new SimpleDateFormat(Time_Style,Locale.getDefault());
        viewHolder.date.setText(dateFormat.format(data.get(i).getCreateTime()));
        String[] split = data.get(i).getImage().split("\\,");
        Glide.with(context).load(split[0]).into(viewHolder.imageView);
        viewHolder.count.setText(data.get(i).getGreatNum()+"");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.circle_content)
        TextView content;
        @BindView(R.id.circle_pic)
        ImageView imageView;
        @BindView(R.id.circle_time)
        TextView date;
        @BindView(R.id.circle_hand_count)
        TextView count;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
