package jx.com.shoppingtrolley_zihenguncle.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jx.com.shoppingtrolley_zihenguncle.R;
import jx.com.shoppingtrolley_zihenguncle.address_adapter.AddressAdapter;
import jx.com.shoppingtrolley_zihenguncle.bean.AddCarBean;
import jx.com.shoppingtrolley_zihenguncle.bean.AddressBean;
import jx.com.shoppingtrolley_zihenguncle.iteminterval.SpacesItemDecoration;
import jx.com.shoppingtrolley_zihenguncle.persenter.IPersenterImpl;
import jx.com.shoppingtrolley_zihenguncle.tool.VerifyUtils;
import jx.com.shoppingtrolley_zihenguncle.url.Apis;
import jx.com.shoppingtrolley_zihenguncle.view.IView;
import me.leefeng.citypicker.CityPicker;
import me.leefeng.citypicker.CityPickerListener;

/**
 * @author 郭淄恒
 * 查询收获地址
 */
public class MyAddressActivity extends AppCompatActivity implements IView,CityPickerListener {

    @BindView(R.id.address_new)
    Button address;
    @BindView(R.id.address_recycle)
    RecyclerView recyclerView;
    private IPersenterImpl iPersenter;
    private AddressAdapter addressAdapter;

    private PopupWindow popupWindow;

    private CityPicker cityPicker;

    private EditText P_address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);
        ButterKnife.bind(this);
        iPersenter = new IPersenterImpl(this);
        iPersenter.startRequestget(Apis.SITE_URL,AddressBean.class);
        cityPicker = new CityPicker(MyAddressActivity.this, this);
        //设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        int spacingInPixels = 10;
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        addressAdapter = new AddressAdapter(this);
        recyclerView.setAdapter(addressAdapter);


        //修改地址
        addressAdapter.setData(new AddressAdapter.setData() {

            private EditText P_zipcode;
            private EditText P_detail_address;

            private EditText P_phone;
            private EditText P_name;
            private int id;

            @Override
            public void setMyData(final AddressBean.Result result) {
                View view = View.inflate(MyAddressActivity.this,R.layout.popup_update_address,null);
                P_name = view.findViewById(R.id.popo_edit_name);
                P_phone = view.findViewById(R.id.popop_edit_phone);
                P_address = view.findViewById(R.id.popop_edit_address);
                P_detail_address = view.findViewById(R.id.popup_detail_address);
                P_zipcode = view.findViewById(R.id.popup_edit_zipCode);
                Button ture_update = view.findViewById(R.id.true_update);
                Button ture_back = view.findViewById(R.id.true_back);
                ImageView P_image = view.findViewById(R.id.image_next);
                popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, true);
                //popupWindow.setFocusable(true);
                //popupWindow.setTouchable(true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.GRAY));
                popupWindow.setFocusable(false);
                popupWindow.setOutsideTouchable(false);
                popupWindow.showAtLocation(view, Gravity.CENTER_VERTICAL, 0, -200);



                /*AlertDialog.Builder builder = new AlertDialog.Builder(MyAddressActivity.this);
                builder.setView(view);*/

                P_name.setText(result.getRealName());
                P_phone.setText(result.getPhone());
                String[] split = result.getAddress().split("\\ ");
                P_address.setText(split[0]+" "+split[1]+" "+split[2]);
                P_detail_address.setText(split[3]);
                P_zipcode.setText(result.getZipCode());
                P_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cityPicker.show();
                    }
                });
                id = result.getId();
                ture_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,String> map = new HashMap<>();
                        map.put("id",id+"");
                        map.put("realName", P_name.getText().toString());
                        map.put("phone", P_phone.getText().toString());
                        map.put("address", P_address.getText().toString()+" "+ P_detail_address.getText().toString());
                        map.put("zipCode", P_zipcode.getText().toString());
                        iPersenter.startRequestPut(Apis.UPDATE_ADDRESS_URL,map,AddCarBean.class);
                        popupWindow.dismiss();
                    }
                });
                ture_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
                /*//.setTitle("修改地址").setMessage("")
                AlertDialog builder = new AlertDialog.Builder(MyAddressActivity.this).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Map<String,String> map = new HashMap<>();
                        map.put("id",id+"");
                        map.put("realName", P_name.getText().toString());
                        map.put("phone", P_phone.getText().toString());
                        map.put("address", P_address.getText().toString()+" "+ P_detail_address.getText().toString());
                        map.put("zipCode", P_zipcode.getText().toString());
                        iPersenter.startRequestPut(Apis.UPDATE_ADDRESS_URL,map,AddCarBean.class);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create();
                builder.setCanceledOnTouchOutside(false);
                builder.setView(view);
                builder.show();
                *//*builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();*/

            }
        });


        addressAdapter.setShowid(new AddressAdapter.setId() {
            @Override
            public void setShowId(int id) {
                Map<String,String> map = new HashMap<>();
                map.put("id",id+"");
                iPersenter.startRequest(Apis.DEFAULT_ADDRESS_URL,map,AddCarBean.class);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        iPersenter.startRequestget(Apis.SITE_URL,AddressBean.class);
    }

    @OnClick({R.id.address_new})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.address_new:
                startActivity(new Intent(MyAddressActivity.this,AddressActivity.class));
                finish();
                break;
                default:
                    break;
        }
    }

    @Override
    public void success(Object data) {
        if (data instanceof AddressBean){
            if(((AddressBean) data).getStatus().equals("0000")){
                if(((AddressBean) data).getMessage().equals("你还没有收货地址，快去添加吧")){
                    VerifyUtils.getInstance().toast("暂无地址，请添加地址");
                }else {
                    addressAdapter.setResults(((AddressBean) data).getResult());
                }
            }
        }
        if(data instanceof AddCarBean){
            if(((AddCarBean) data).getStatus().equals("0000")){
                VerifyUtils.getInstance().toast(((AddCarBean) data).getMessage());
            }
        }
    }

    @Override
    public void failed(String error) {
        VerifyUtils.getInstance().toast(error);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPersenter.detach();
    }


    @Override
    public void getCity(String s) {
        P_address.setText(s);
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
