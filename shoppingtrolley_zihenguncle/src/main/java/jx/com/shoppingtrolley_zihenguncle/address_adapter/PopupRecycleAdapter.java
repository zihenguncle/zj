package jx.com.shoppingtrolley_zihenguncle.address_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.com.shoppingtrolley_zihenguncle.R;
import jx.com.shoppingtrolley_zihenguncle.bean.AddressBean;

public class PopupRecycleAdapter extends RecyclerView.Adapter<PopupRecycleAdapter.ViewHolder> {

    private List<AddressBean.Result> results;
    private Context context;

    public PopupRecycleAdapter(Context context) {
        this.context = context;
        results = new ArrayList<>();
    }

    public void setResults(List<AddressBean.Result> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PopupRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context,R.layout.popup_recycle_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopupRecycleAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.name.setText(results.get(i).getRealName());
        viewHolder.address.setText(results.get(i).getAddress());
        viewHolder.phone.setText(results.get(i).getPhone());
        viewHolder.text_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(setId != null){
                    int id = results.get(i).getId();
                    setId.setShowId(id);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.popop_name)
        TextView name;
        @BindView(R.id.popop_address)
        TextView address;
        @BindView(R.id.popop_phone)
        TextView phone;
        @BindView(R.id.text_checked)
        TextView text_check;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public setId setId;
    public void setShowid(setId showid){
        setId = showid;
    }
    public interface setId{
        void setShowId(int id);
    }
}
