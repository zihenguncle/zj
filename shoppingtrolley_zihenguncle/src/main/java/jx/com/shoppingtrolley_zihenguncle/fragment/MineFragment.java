package jx.com.shoppingtrolley_zihenguncle.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jx.com.shoppingtrolley_zihenguncle.R;
import jx.com.shoppingtrolley_zihenguncle.activity.MyAddressActivity;
import jx.com.shoppingtrolley_zihenguncle.activity.MyFootPrint;
import jx.com.shoppingtrolley_zihenguncle.activity.MywalletActivity;
import jx.com.shoppingtrolley_zihenguncle.activity.PersonalActivity;
import jx.com.shoppingtrolley_zihenguncle.app.MyApplication;
import jx.com.shoppingtrolley_zihenguncle.base.BaseFragment;
import jx.com.shoppingtrolley_zihenguncle.bean.EventBusBean;
import jx.com.shoppingtrolley_zihenguncle.bean.UserBean;
import jx.com.shoppingtrolley_zihenguncle.circle.MyCircleActivity;
import jx.com.shoppingtrolley_zihenguncle.persenter.IPersenterImpl;
import jx.com.shoppingtrolley_zihenguncle.tag.EventTag;
import jx.com.shoppingtrolley_zihenguncle.tool.VerifyUtils;
import jx.com.shoppingtrolley_zihenguncle.url.Apis;
import jx.com.shoppingtrolley_zihenguncle.view.IView;

import static android.content.Context.MODE_PRIVATE;

/**
 * @author 郭淄恒
 * 我的页面
 *
 */
public class MineFragment extends BaseFragment implements IView {

    @BindView(R.id.mine_personage_text)
    TextView personage;
    @BindView(R.id.mine_mycircle_text)
    TextView mycircle;
    @BindView(R.id.mine_myfootprint_text)
    TextView myfootprint;
    @BindView(R.id.mine_mywallet_text)
    TextView mywallet;
    @BindView(R.id.mine_myaddress_text)
    TextView myaddress;
    @BindView(R.id.mine_head_image)
    SimpleDraweeView simpleDraweeView;
    @BindView(R.id.mine_name)
    TextView mine_name;
    private String nickName;
    IPersenterImpl iPersenter;

    @Override
    protected void initData(View view) {
        //绑定
        ButterKnife.bind(this,view);
        iPersenter = new IPersenterImpl(this);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login_db", MODE_PRIVATE);
        nickName = sharedPreferences.getString("nickName", null);
        mine_name.setText(nickName);
        iPersenter.startRequestget(Apis.SELECT_MY,UserBean.class);

    }

    @Override
    public void onResume() {
        super.onResume();
        iPersenter.startRequestget(Apis.SELECT_MY,UserBean.class);
    }

    @OnClick({R.id.mine_myfootprint_text,R.id.mine_myaddress_text,R.id.mine_mywallet_text,R.id.mine_mycircle_text,R.id.mine_personage_text})
    public void onClick(View view){
        switch (view.getId()){
            //我的足迹
            case R.id.mine_myfootprint_text:
                startActivity(new Intent(getActivity(),MyFootPrint.class));
                break;
                //我的地址
            case R.id.mine_myaddress_text:
                startActivity(new Intent(getActivity(),MyAddressActivity.class));
                break;
                //我的钱包
            case R.id.mine_mywallet_text:
                startActivity(new Intent(getActivity(),MywalletActivity.class));
                break;
                //我的圈子
            case R.id.mine_mycircle_text:
                startActivity(new Intent(getActivity(),MyCircleActivity.class));
                break;
                //个人资料
            case R.id.mine_personage_text:
                Intent intent = new Intent(getActivity(), PersonalActivity.class);
                intent.putExtra("nickName",nickName);
                startActivity(intent);
                break;
                default:
                    break;
        }
    }

    @Override
    protected int getViewById() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void success(Object data) {
        if(data instanceof UserBean){
            String nickName = ((UserBean) data).getResult().getNickName();
            mine_name.setText(nickName);
            simpleDraweeView.setImageURI(((UserBean) data).getResult().getHeadPic());
        }
    }

    @Override
    public void failed(String error) {
        VerifyUtils.getInstance().toast(error);
    }
}
