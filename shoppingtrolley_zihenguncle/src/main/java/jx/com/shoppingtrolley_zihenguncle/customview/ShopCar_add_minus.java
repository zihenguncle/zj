package jx.com.shoppingtrolley_zihenguncle.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jx.com.shoppingtrolley_zihenguncle.R;
import jx.com.shoppingtrolley_zihenguncle.bean.CarBean;
import jx.com.shoppingtrolley_zihenguncle.car_adapter.CarAdapter;

public class ShopCar_add_minus extends LinearLayout {
    private Context mContext;
    @BindView(R.id.text_jia)
    TextView textView_jia;
    @BindView(R.id.text_jian)
    TextView textView_jian;
    @BindView(R.id.edit_num)
    EditText editText_num;
    private int count;


    CarAdapter mAdapter;
    List<CarBean.Result> mList;
    int position;
    public ShopCar_add_minus(Context context) {
        super(context);
        mContext=context;
        mList = new ArrayList<>();
        initView();
    }

    public ShopCar_add_minus(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        initView();
    }
    public void initView(){
        View view= View.inflate(mContext, R.layout.custom_num,null);
        ButterKnife.bind(this,view);
        addView(view);
        editText_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    count = Integer.valueOf(charSequence.toString());
                    mList.get(position).setCount(count);
                }catch (Exception e){
                    mList.get(position).setCount(count);
                }
                if(callBackListener != null){
                    callBackListener.getCheckState();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    @OnClick({R.id.text_jia,R.id.text_jian})
    public void getViewClick(View view){
        switch (view.getId()){
            case R.id.text_jia:
               count++;
               editText_num.setText(count+"");
               mList.get(position).setCount(count);
               mAdapter.notifyDataSetChanged();
               callBackListener.getCheckState();
               break;
            case R.id.text_jian:
                if(count>1){
                    count--;
                }else {
                    Toast.makeText(mContext,"手下留情",Toast.LENGTH_SHORT).show();
                }
                editText_num.setText(count+"");
                mList.get(position).setCount(count);
                mAdapter.notifyDataSetChanged();
                callBackListener.getCheckState();
                break;
                default:
                    break;
        }
    }


    public void setEditNum(CarAdapter adapter, List<CarBean.Result> list,int position){
        mAdapter = adapter;
        mList = list;
        this.position = position;
        count = list.get(position).getCount();
        editText_num.setText(count +"");
    }
    public onCallBackListener callBackListener;
    public void setOnCallBackListener(onCallBackListener backListener){
        this.callBackListener=backListener;
    }

    public interface onCallBackListener{
        void getCheckState();
    }

}
