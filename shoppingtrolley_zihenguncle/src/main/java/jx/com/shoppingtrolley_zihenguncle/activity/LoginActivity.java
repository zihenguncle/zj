package jx.com.shoppingtrolley_zihenguncle.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTouch;
import jx.com.shoppingtrolley_zihenguncle.R;
import jx.com.shoppingtrolley_zihenguncle.base.BaseActivity;
import jx.com.shoppingtrolley_zihenguncle.bean.EventBusBean;
import jx.com.shoppingtrolley_zihenguncle.bean.LoginBean;
import jx.com.shoppingtrolley_zihenguncle.persenter.IPersenterImpl;
import jx.com.shoppingtrolley_zihenguncle.tag.EventTag;
import jx.com.shoppingtrolley_zihenguncle.tool.VerifyUtils;
import jx.com.shoppingtrolley_zihenguncle.url.Apis;
import jx.com.shoppingtrolley_zihenguncle.view.IView;

/**
 * @author 郭淄恒
 */
public class LoginActivity extends BaseActivity implements IView {

    @BindView(R.id.login_edit_phonenum)
    EditText edit_phonenum;
    @BindView(R.id.login_edit_password)
    EditText password;
    @BindView(R.id.login_check_remember)
    CheckBox checkbox_remember;
    @BindView(R.id.login_quick_registration)
    TextView text_register;
    @BindView(R.id.login_icon_eye)
    ImageView image_eye;
    @BindView(R.id.login_button_login)
    Button button_login;

    private IPersenterImpl iPersenter;
    private SharedPreferences.Editor edit;

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        //实例化
        iPersenter = new IPersenterImpl(this);

        //实例化
        SharedPreferences preferences = getSharedPreferences("login_db", MODE_PRIVATE);
        edit = preferences.edit();

        boolean remember = preferences.getBoolean("remember", false);
        if(remember){
            String p_phone = preferences.getString("phonenum", null);
            String p_password = preferences.getString("password", null);
            edit_phonenum.setText(p_phone);
            password.setText(p_password);
            checkbox_remember.setChecked(true);
        }else {
            edit_phonenum.setText("");
            password.setText("");
        }
    }

    @OnTouch({R.id.login_icon_eye})
    public boolean setOnTouch(View v,MotionEvent event){
        switch (v.getId()){
            case R.id.login_icon_eye:
                //判断是是否按下抬起
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    //显示
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    //隐藏
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
                default:
                    break;
        }
        return true;
    }
    @OnClick({R.id.login_quick_registration,R.id.login_button_login,R.id.login_check_remember})
    public void setOnClick(View view){
        switch (view.getId()){
            //点击跳转
            case R.id.login_quick_registration:
                //跳转到注册页面
                skipIntent();
                break;
            case R.id.login_button_login:
                butLogin();
                break;
            case R.id.login_check_remember:
                ischeck();
                default:
                    break;
        }
    }

    //判断是否是选中状态
    private void ischeck(){
        if(checkbox_remember.isChecked()){
            edit.putBoolean("remember",true);
            edit.putString("phonenum",edit_phonenum.getText().toString());
            edit.putString("password",password.getText().toString());
            edit.commit();
        }else {
            edit.clear();
            edit.commit();
        }
    }

    //请求网络
    public void butLogin(){
        if(edit_phonenum.getText().toString().equals("")){
            toast("手机号不可为空");
        } else if(password.getText().toString().equals("")){
            toast("密码不可为空");
        }else if(VerifyUtils.getInstance().verifyPhoneNum(edit_phonenum.getText().toString())&&VerifyUtils.getInstance().verifyPassword(password.getText().toString())){
            Map<String,String> map = new HashMap<>();
            map.put("phone",edit_phonenum.getText().toString());
            map.put("pwd",password.getText().toString());
            iPersenter.startRequest(Apis.LOGIN_URL,map,LoginBean.class);
        }else {
            toast("账号或密码不合法");
        }
    }
    //吐司
    private void toast(String toast){
        Toast.makeText(LoginActivity.this,toast,Toast.LENGTH_SHORT).show();
    }
    public void skipIntent(){
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
    }
    @Override
    protected int getViewById() {
        return R.layout.activity_login;
    }

    //请求成功
    @Override
    public void success(Object data) {
        if(data instanceof LoginBean){

            int userId = ((LoginBean) data).getResult().getUserId();
            String sessionId = ((LoginBean) data).getResult().getSessionId();
            edit.putString("userId",userId+"");
            edit.putString("sessionId",sessionId);
            Log.i("TAG",userId+"+++++++"+sessionId);
            edit.commit();
            toast(((LoginBean) data).getMessage());
            if(((LoginBean) data).getStatus().equals("0000")){
                startActivity(new Intent(LoginActivity.this,HomepageActivity.class));
                finish();
            }
        }

    }

    //请求失败
    @Override
    public void failed(String error) {
            toast(error);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPersenter.detach();
    }
}
