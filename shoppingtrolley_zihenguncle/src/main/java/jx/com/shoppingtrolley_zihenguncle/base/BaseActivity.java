package jx.com.shoppingtrolley_zihenguncle.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getViewById());
        initData();
    }

    protected abstract void initData();

    protected abstract int getViewById();

}
