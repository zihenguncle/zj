package jx.com.shoppingtrolley_zihenguncle.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import jx.com.shoppingtrolley_zihenguncle.R;
import jx.com.shoppingtrolley_zihenguncle.base.BaseActivity;
import jx.com.shoppingtrolley_zihenguncle.bean.LoginBean;
import jx.com.shoppingtrolley_zihenguncle.bean.RegisterBean;
import jx.com.shoppingtrolley_zihenguncle.persenter.IPersenterImpl;
import jx.com.shoppingtrolley_zihenguncle.tool.VerifyUtils;
import jx.com.shoppingtrolley_zihenguncle.url.Apis;
import jx.com.shoppingtrolley_zihenguncle.view.IView;

/**
 * @author 郭淄恒
 */
public class RegisterActivity extends BaseActivity implements IView {

    @BindView(R.id.edit_register_phone)
    EditText edit_phone;
    @BindView(R.id.edit_register_code)
    EditText edit_code;
    @BindView(R.id.get_code)
    TextView get_code;
    @BindView(R.id.edit_register_pass)
    EditText edit_pass;
    @BindView(R.id.register__eye)
    ImageView image_eye;
    @BindView(R.id.get_login)
    TextView text_intent;
    @BindView(R.id.button_register)
    Button but_register;

    private IPersenterImpl iPersenter;
    private String ramdom = "";

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        iPersenter = new IPersenterImpl(this);
    }

    @OnTouch({R.id.register__eye})
    public boolean setOnTouch(View v,MotionEvent event){
        switch (v.getId()){
            case R.id.register__eye:
                //判断是是否按下抬起
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    //显示
                    edit_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    //隐藏
                    edit_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            default:
                break;
        }
        return true;
    }

    //获得验证码，已有账户了立即登陆，注册
    @OnClick({R.id.get_code,R.id.get_login,R.id.button_register})
    public void setonClick(View view){
        switch (view.getId()){
            //获得验证码
            case R.id.get_code:
                ramdom = VerifyUtils.getInstance().getRamdom();
                toast(ramdom);
                break;
            //已有账户了立即登陆
            case R.id.get_login:
                finish();
                break;
            //注册
            case R.id.button_register:
                getRegister();
                break;
                default:
                    break;
        }
    }

    //验证输的框的值
    private void getRegister(){
        if(edit_phone.getText().toString().equals("")){
            toast("手机号不可为空");
        } else if(edit_pass.getText().toString().equals("")){
            toast("密码不可为空");
        } else if(ramdom.equals("")){
            toast("请输入验证码");
        }else if(!ramdom.equals(edit_code.getText().toString())){
            toast("验证码错误");
        }else if(VerifyUtils.getInstance().verifyPhoneNum(edit_phone.getText().toString())&&
                VerifyUtils.getInstance().verifyPassword(edit_pass.getText().toString())){
            Map<String,String> map = new HashMap<>();
            map.put("phone",edit_phone.getText().toString());
            map.put("pwd",edit_pass.getText().toString());
            iPersenter.startRequest(Apis.REGISTER_URL,map,RegisterBean.class);
        }else {
            toast("账号或密码不合法");
        }
    }

    @Override
    protected int getViewById() {
        return R.layout.activity_register;
    }

    //吐司
    private void toast(String toast){
        Toast.makeText(RegisterActivity.this,toast,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void success(Object data) {
        if(data instanceof RegisterBean){
            toast(((RegisterBean) data).getMessage()+"");
            if(((RegisterBean) data).getMessage().equals("注册成功")){
                finish();
            }
        }
    }

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
