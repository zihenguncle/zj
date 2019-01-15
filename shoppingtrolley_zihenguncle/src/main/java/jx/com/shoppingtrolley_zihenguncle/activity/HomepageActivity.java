package jx.com.shoppingtrolley_zihenguncle.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jx.com.shoppingtrolley_zihenguncle.R;
import jx.com.shoppingtrolley_zihenguncle.customview.CustomViewPager;
import jx.com.shoppingtrolley_zihenguncle.fragment.BillFragment;
import jx.com.shoppingtrolley_zihenguncle.fragment.CircleFragment;
import jx.com.shoppingtrolley_zihenguncle.fragment.HomepageFragment;
import jx.com.shoppingtrolley_zihenguncle.fragment.MineFragment;
import jx.com.shoppingtrolley_zihenguncle.fragment.ShoppingcartFragment;

/**
 * @author 郭淄恒
 */
public class HomepageActivity extends AppCompatActivity {

    @BindView(R.id.homepage_viewpager)
    CustomViewPager viewPager;
    @BindView(R.id.homepage_radiogroup)
    RadioGroup radioGroup;
    @BindView(R.id.homepage_but_homepage)
    Button homepage;
    @BindView(R.id.homepage_but_circle)
    Button circle;
    @BindView(R.id.home_bottom_shoppingcart)
    Button shoppingcart;
    @BindView(R.id.home_bottom_bill)
    Button bill;
    @BindView(R.id.home_bottom_mine)
    Button mine;

    private List<Fragment> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        ButterKnife.bind(this);

        //滑动切换
        setCurrent();

        //实例化Framgnet,   添加到List中
        initFrag();

        //设置适配器
        setHomeListAdapter();
    }

    //设置适配器
    private void setHomeListAdapter() {
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
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

    //实例化Framgnet,   添加到List中
    private void initFrag() {
        HomepageFragment homepageFragment = new HomepageFragment();
        CircleFragment circleFragment = new CircleFragment();
        ShoppingcartFragment shoppingcartFragment = new ShoppingcartFragment();
        BillFragment billFragment = new BillFragment();
        MineFragment mineFragment = new MineFragment();

        //添加到list中
        list = new ArrayList<>();
        list.add(homepageFragment);
        list.add(circleFragment);
        list.add(shoppingcartFragment);
        list.add(billFragment);
        list.add(mineFragment);
    }

    //滑动切换
    private void setCurrent() {

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        radioGroup.check(R.id.homepage_but_homepage);
                        break;
                    case 1:
                        radioGroup.check(R.id.homepage_but_circle);
                        break;
                    case 2:
                        radioGroup.check(R.id.home_bottom_shoppingcart);
                        break;
                    case 3:
                        radioGroup.check(R.id.home_bottom_bill);
                        break;
                    case 4:
                        radioGroup.check(R.id.home_bottom_mine);
                        default:
                            break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        //点击按钮切换Fragment
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.homepage_but_homepage:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.homepage_but_circle:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.home_bottom_shoppingcart:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.home_bottom_bill:
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.home_bottom_mine:
                        viewPager.setCurrentItem(4);
                        break;
                        default:
                            break;
                }
            }
        });
    }

}
