package jx.com.shoppingtrolley_zihenguncle.address_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.com.shoppingtrolley_zihenguncle.R;
import jx.com.shoppingtrolley_zihenguncle.bean.AddressBean;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    private List<AddressBean.Result> results;
    private Context context;

    public AddressAdapter(Context context) {
        this.context = context;
        results = new ArrayList<>();
    }

    public void setResults(List<AddressBean.Result> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context,R.layout.activity_address_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AddressAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.name.setText(results.get(i).getRealName());
        viewHolder.phone.setText(results.get(i).getPhone());
        viewHolder.site.setText(results.get(i).getAddress());
        if(results.get(i).getWhetherDefault()==1){
            viewHolder.radiobutton.setChecked(true);
        }else {
            viewHolder.radiobutton.setChecked(false);
        }


        viewHolder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(setData != null){
                    setData.setMyData(results.get(i));
                }
            }
        });
        viewHolder.radiobutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    for (int j = 0; j < results.size(); j++) {
                        results.get(j).setWhetherDefault(2);
                        viewHolder.radiobutton.setChecked(false);
                    }
                    results.get(i).setWhetherDefault(1);
                    viewHolder.radiobutton.setChecked(b);
                    if(setId != null){
                        int id = results.get(i).getId();
                        setId.setShowId(id);
                    }
                    notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.address_item_name)
        TextView name;
        @BindView(R.id.address_item_phone)
        TextView phone;
        @BindView(R.id.address_item_site)
        TextView site;
        @BindView(R.id.address_item_radio)
        RadioButton radiobutton;
        @BindView(R.id.address_item_del)
        Button del;
        @BindView(R.id.address_item_update)
        Button update;
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

    public setData setData;
    public void setData(setData setdata){
        this.setData = setdata;
    }
    public interface setData{
        void setMyData(AddressBean.Result result);
    }

}
