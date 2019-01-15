package jx.com.shoppingtrolley_zihenguncle.car_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.com.shoppingtrolley_zihenguncle.R;
import jx.com.shoppingtrolley_zihenguncle.bean.CarBean;
import jx.com.shoppingtrolley_zihenguncle.customview.ShopCar_add_minus;

/**
 * @author 郭淄恒
 */
public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {

    private List<CarBean.Result> data;
    private Context context;

    public CarAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public void setData(List<CarBean.Result> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_car,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CarAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.checkBox.setChecked(data.get(i).isCheck());
        Glide.with(context).load(data.get(i).getPic()).into(viewHolder.imageView);
        viewHolder.textView_name.setText(data.get(i).getCommodityName());
        viewHolder.textView_price.setText("￥"+data.get(i).getPrice());
        viewHolder.customview_num.setEditNum(this,data,i);
        viewHolder.customview_num.setOnCallBackListener(new ShopCar_add_minus.onCallBackListener() {
            @Override
            public void getCheckState() {
                if(editCallBack != null){
                    editCallBack.editcallback();
                }
            }
        });
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, final boolean b) {
                data.get(i).setCheck(b);
                if(editCallBack != null){
                    editCallBack.editcallback();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.checkbox_goods)
        CheckBox checkBox;
        @BindView(R.id.image_pic)
        ImageView imageView;
        @BindView(R.id.goods_name)
        TextView textView_name;
        @BindView(R.id.goods_price)
        TextView textView_price;
        @BindView(R.id.customview_num)
        ShopCar_add_minus customview_num;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public EditCallBack editCallBack;
    public void setCallBack(EditCallBack editCallBack){
        this.editCallBack = editCallBack;
    }
    public interface EditCallBack{
        void editcallback();
    }
}
