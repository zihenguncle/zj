package jx.com.shoppingtrolley_zihenguncle.homepage_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.com.shoppingtrolley_zihenguncle.R;
import jx.com.shoppingtrolley_zihenguncle.bean.DetailsBean;
import jx.com.shoppingtrolley_zihenguncle.bean.DiscussBean;

public class DeatilsAdapter extends RecyclerView.Adapter<DeatilsAdapter.ViewHolder> {

    private List<DiscussBean.ResultBean> data;
    private Context context;

    public DeatilsAdapter(List<DiscussBean.ResultBean> data, Context context) {
        this.data = data;
        this.context = context;
    }



    @NonNull
    @Override
    public DeatilsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context,R.layout.item_details_activity,null);
        return new ViewHolder(view);
    }
    public static final String Time_Style = "yyyy-MM-dd HH:mm:ss";


    @Override
    public void onBindViewHolder(@NonNull DeatilsAdapter.ViewHolder viewHolder, int i) {
        viewHolder.textView_nickName.setText(data.get(i).getNickName());
        //解密   时间
        SimpleDateFormat dateFormat = new SimpleDateFormat(Time_Style, Locale.getDefault());
        viewHolder.textView_createTime.setText(dateFormat.format(data.get(i).getCreateTime()));
        viewHolder.comment_headPic.setImageURI(data.get(i).getHeadPic());
        viewHolder.textView_content.setText(data.get(i).getContent());
        String image = data.get(i).getImage();
        String[] split = image.split("\\,");
        Glide.with(context).load(split[0]).into(viewHolder.simpleDraweeView_image);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.comment_headPic)
        SimpleDraweeView comment_headPic;
        @BindView(R.id.comment_text_nickName)
        TextView textView_nickName;
        @BindView(R.id.comment_text_createTime)
        TextView textView_createTime;
        @BindView(R.id.comment_text_content)
        TextView textView_content;
        @BindView(R.id.comment_image)
        ImageView simpleDraweeView_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
