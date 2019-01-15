package jx.com.shoppingtrolley_zihenguncle.homepage_adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
import jx.com.shoppingtrolley_zihenguncle.tag.EventTag;

public class HeadTwoAdapter extends RecyclerView.Adapter<HeadTwoAdapter.ViewHolder> {

    private List<HeadTwoBean.ResultBean> data;
    private Context context;
    private List<Boolean> str;
    public HeadTwoAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
        str = new ArrayList<>();}

    public void setData(List<HeadTwoBean.ResultBean> data) {
        this.data = data;
        for (int i = 0; i < data.size(); i++) {
            str.add(false);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HeadTwoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.headtwoitem,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeadTwoAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.title.setText(data.get(i).getName());
        if(str.get(i)){
            viewHolder.title.setTextColor(Color.RED);
        }else {
            viewHolder.title.setTextColor(Color.WHITE);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = data.get(i).getId();
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
        @BindView(R.id.headtwo_title)
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
