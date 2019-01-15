package jx.com.shoppingtrolley_zihenguncle.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.com.shoppingtrolley_zihenguncle.R;
import jx.com.shoppingtrolley_zihenguncle.base.BaseFragment;
import jx.com.shoppingtrolley_zihenguncle.order.fragment.AllordersFragment;
import jx.com.shoppingtrolley_zihenguncle.order.fragment.BeevaluatedFragment;
import jx.com.shoppingtrolley_zihenguncle.order.fragment.CompletedFragment;
import jx.com.shoppingtrolley_zihenguncle.order.fragment.ForCollectionFragment;
import jx.com.shoppingtrolley_zihenguncle.order.fragment.ObligationFragment;

public class BillFragment extends BaseFragment {

    //radiogroup
    @BindView(R.id.group_divider)
    RadioGroup billRadiogroup;

    //全部订单
    @BindView(R.id.image_allorders)
    RadioButton allorders;
    //代付款
    @BindView(R.id.image_obligation)
    RadioButton obligation;
    //待收货
    @BindView(R.id.image_forCollection)
    RadioButton forCollection;
    //待评价
    @BindView(R.id.image_beevaluated)
    RadioButton beevaluated;
    //已完成
    @BindView(R.id.image_completed)
    RadioButton completed;

    @BindView(R.id.order_viewpager)
    ViewPager viewPager;
    private List<Fragment> list;

    @BindView(R.id.image_allorders_text)
    TextView allorder_text;

    @Override
    protected void initData(View view) {
        //绑定
        ButterKnife.bind(this,view);
        list = new ArrayList<>();
        //添加fragment
        setList();
        //设置适配器
        setViewAdapter();
        //设置滑动监听
        setScrool();
    }

    private void setScrool() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        billRadiogroup.check(R.id.image_allorders);
                        break;
                    case 1:
                        billRadiogroup.check(R.id.image_obligation);
                        break;
                    case 2:
                        billRadiogroup.check(R.id.image_forCollection);
                        break;
                    case 3:
                        billRadiogroup.check(R.id.image_beevaluated);
                        break;
                    case 4:
                        billRadiogroup.check(R.id.image_completed);
                        default:
                            break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        billRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.image_allorders:
                        viewPager.setCurrentItem(0);

                        break;
                    case R.id.image_obligation:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.image_forCollection:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.image_beevaluated:
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.image_completed:
                        viewPager.setCurrentItem(4);
                        break;
                        default:
                            break;
                }
            }
        });
    }

    //设置适配器
    private void setViewAdapter() {
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return list.get(i);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
    }

    private void setList(){
        AllordersFragment allordersFragment = new AllordersFragment();
        ObligationFragment obligationFragment = new ObligationFragment();
        ForCollectionFragment forCollectionFragment = new ForCollectionFragment();
        BeevaluatedFragment beevaluatedFragment = new BeevaluatedFragment();
        CompletedFragment completedFragment = new CompletedFragment();
        list.add(allordersFragment);
        list.add(obligationFragment);
        list.add(forCollectionFragment);
        list.add(beevaluatedFragment);
        list.add(completedFragment);
    }


    @Override
    protected int getViewById() {
        return R.layout.fragment_bill;
    }
}
