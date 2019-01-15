package jx.com.shoppingtrolley_zihenguncle.homepage_adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.com.shoppingtrolley_zihenguncle.R;
import jx.com.shoppingtrolley_zihenguncle.bean.EventBusBean;
import jx.com.shoppingtrolley_zihenguncle.bean.HeadBean;
import jx.com.shoppingtrolley_zihenguncle.bean.HeadTwoBean;
import jx.com.shoppingtrolley_zihenguncle.persenter.IPersenterImpl;
import jx.com.shoppingtrolley_zihenguncle.tag.EventTag;
import jx.com.shoppingtrolley_zihenguncle.url.Apis;
import jx.com.shoppingtrolley_zihenguncle.view.IView;

public class HeadOneAdapter extends RecyclerView.Adapter<HeadOneAdapter.ViewHolder>{

    private List<HeadBean.ResultBean> data;
    private Context context;
    private List<Boolean> str;
    public HeadOneAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
        str = new ArrayList<>();
    }

    public void setData(List<HeadBean.ResultBean> data) {
        this.data = data;
        for (int i = 0; i < data.size(); i++) {
                str.add(false);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HeadOneAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.headitem,null);
        return new ViewHolder(view);
    }

    private Boolean text_color = true;
    @Override
    public void onBindViewHolder(@NonNull final HeadOneAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.title.setText(data.get(i).getName());
        if(text_color){
            str.set(0,true);
            text_color = false;
        }
        if(str.get(i)){
            viewHolder.title.setTextColor(Color.RED);
        }else {
            viewHolder.title.setTextColor(Color.WHITE);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setonClick.setId(data.get(i).getId());
                for (int j = 0; j < str.size(); j++) {
                    str.set(j,false);
                }
                str.set(i,true);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.headitem_text)
        TextView title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


    private setonClick setonClick;
    public void onclick(setonClick click){
        setonClick = click;
    }
    public interface setonClick{
        void setId(String id);
    }

}
