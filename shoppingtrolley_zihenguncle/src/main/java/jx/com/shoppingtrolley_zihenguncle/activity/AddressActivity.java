package jx.com.shoppingtrolley_zihenguncle.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jx.com.shoppingtrolley_zihenguncle.R;
import jx.com.shoppingtrolley_zihenguncle.bean.AddCarBean;
import jx.com.shoppingtrolley_zihenguncle.persenter.IPersenterImpl;
import jx.com.shoppingtrolley_zihenguncle.tool.VerifyUtils;
import jx.com.shoppingtrolley_zihenguncle.url.Apis;
import jx.com.shoppingtrolley_zihenguncle.view.IView;
import me.leefeng.citypicker.CityPicker;
import me.leefeng.citypicker.CityPickerListener;

/**
 * @author 郭淄恒
 *添加数据
 */
public class AddressActivity extends AppCompatActivity implements CityPickerListener,IView {

    @BindView(R.id.edit_name)
    EditText editText_name;
    @BindView(R.id.edit_phone)
    EditText editText_phone;
    @BindView(R.id.edit_address)
    EditText editText_address;
    @BindView(R.id.edit_zipCode)
    EditText editText_zipCode;
    @BindView(R.id.detail_address)
    EditText editText_detail_address;
    @BindView(R.id.save_address)
    Button save_address;
    @BindView(R.id.image_next)
    ImageView imageView_next;
    private CityPicker cityPicker;
    private IPersenterImpl iPersenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);
        cityPicker = new CityPicker(AddressActivity.this, this);
        iPersenter = new IPersenterImpl(this);
    }

    @OnClick({R.id.image_next,R.id.save_address})
    public void setOnClick(View v){
        switch (v.getId()){
            case R.id.image_next:
                cityPicker.show();
                break;
            case R.id.save_address:
                String name = editText_name.getText().toString();
                String phone = editText_phone.getText().toString();
                String detail_address = editText_detail_address.getText().toString();
                String zipCode = editText_zipCode.getText().toString();
                String address = editText_address.getText().toString();
                Map<String,String> params=new HashMap<>();
                params.put("realName",name);
                params.put("phone",phone);
                params.put("address",address+" "+detail_address);
                params.put("zipCode",zipCode);
                iPersenter.startRequest(Apis.ADD_SITE,params,AddCarBean.class);
                break;
                default:
                    break;
        }
    }



    @Override
    public void success(Object data) {
        if(data instanceof AddCarBean){
            if(((AddCarBean) data).getStatus().equals("0000")){
                startActivity(new Intent(AddressActivity.this,MyAddressActivity.class));
                VerifyUtils.getInstance().toast(((AddCarBean) data).getMessage());
                finish();
            }
        }
    }

    @Override
    public void failed(String error) {
        VerifyUtils.getInstance().toast(error);
    }

    @Override
    public void getCity(String s) {
        editText_address.setText(s);
    }
    @Override
    public void onBackPressed() {
        if (cityPicker.isShow()){
            cityPicker.close();
            return;
        }
        super.onBackPressed();
    }



}
