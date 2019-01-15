package jx.com.shoppingtrolley_zihenguncle.circle_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.com.shoppingtrolley_zihenguncle.R;
import jx.com.shoppingtrolley_zihenguncle.activity.MyFootPrint;
import jx.com.shoppingtrolley_zihenguncle.bean.CircleHandBean;
import jx.com.shoppingtrolley_zihenguncle.bean.CircleListBean;
import jx.com.shoppingtrolley_zihenguncle.persenter.IPersenterImpl;
import jx.com.shoppingtrolley_zihenguncle.url.Apis;
import jx.com.shoppingtrolley_zihenguncle.view.IView;

public class CircleAdapter extends RecyclerView.Adapter<CircleAdapter.ViewHolder> implements IView {

    private List<CircleListBean.ResultBean> data;
    private Context context;
    private IPersenterImpl iPersenter;

    public CircleAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
        iPersenter = new IPersenterImpl(this);
    }

    public void setData(List<CircleListBean.ResultBean> data) {
        this.data.clear();
        if(data!=null){
            this.data.addAll(data);
        }
        notifyDataSetChanged();
    }
    public void addData(List<CircleListBean.ResultBean> data) {
        if(data!=null){
            this.data.addAll(data);
        }
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public CircleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_circle_fragment,null);
        return new ViewHolder(view);
    }

    public static final String QuanZi_FaBu_Time = "yyyy-MM-dd HH:mm:ss";

    @Override
    public void onBindViewHolder(@NonNull final CircleAdapter.ViewHolder viewHolder, final int i) {
        //头像
        viewHolder.c_head_image.setImageURI(data.get(i).getHeadPic());
        //名字
        viewHolder.c_name.setText(data.get(i).getNickName());
        //解密   时间
        final SimpleDateFormat dateFormat = new SimpleDateFormat(QuanZi_FaBu_Time,Locale.getDefault());
        viewHolder.c_date.setText(dateFormat.format(data.get(i).getCreateTime()));
        //数据
        viewHolder.c_content.setText(data.get(i).getContent());
        //配图
        Glide.with(context).load(data.get(i).getImage()).into(viewHolder.c_image);
        //点赞个数
        viewHolder.c_handnum.setText(data.get(i).getGreatNum()+"");

        if(data.get(i).getWhetherGreat()==1){
            viewHolder.c_hand.setImageResource(R.drawable.common_btn_prise_s);
        }else if (data.get(i).getWhetherGreat()==2){
            viewHolder.c_hand.setImageResource(R.drawable.common_btn_prise_n);
        }

        //点赞
        viewHolder.c_hand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOnClick.setClick(data.get(i).getId(),data.get(i).getWhetherGreat(),i);
            }
        });

    }

    public void setAddNum(int position){
        data.get(position).setWhetherGreat(1);
        data.get(position).setGreatNum(data.get(position).getGreatNum()+1);
        notifyDataSetChanged();
    }

    public void setCancleNum(int position){
        data.get(position).setWhetherGreat(2);
        data.get(position).setGreatNum(data.get(position).getGreatNum()-1);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void success(Object data) {
        if(data instanceof CircleHandBean){
            if(((CircleHandBean) data).getMessage().equals("点赞成功")){
                toast(((CircleHandBean) data).getMessage());
            }else if (((CircleHandBean) data).getMessage().equals("取消成功")){
                toast(((CircleHandBean) data).getMessage());
            }
        }
    }

    @Override
    public void failed(String error) {
        toast(error);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_circle_image_name)
        SimpleDraweeView c_head_image;
        @BindView(R.id.item_circle_name)
        TextView c_name;
        @BindView(R.id.item_circle_date)
        TextView c_date;
        @BindView(R.id.item_circle_content)
        TextView c_content;
        @BindView(R.id.item_circle_image_view)
        ImageView c_image;
        @BindView(R.id.item_circle_whitehand)
        ImageView c_hand;
        @BindView(R.id.item_circle_handnum)
        TextView c_handnum;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public setOnClick setOnClick;
    public void setonclick(setOnClick setOnClick){
        this.setOnClick = setOnClick;
    }
    public interface setOnClick{
        void setClick(int id,int whetherGreat,int position);
    }
    private void toast(String toast){
        Toast.makeText(context,toast,Toast.LENGTH_SHORT).show();
    }
}
