package jx.com.shoppingtrolley_zihenguncle.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.view.WindowManager;

import com.facebook.drawee.backends.pipeline.Fresco;

import butterknife.ButterKnife;

/**
 * @author 郭淄恒
 */
public class MyApplication extends Application {

    //绘制页面时参照的设计图宽度
    public final static float DESIGN_WIDTH = 750;
    private static Context mcontext;
    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(this);
        resetDensity();

        mcontext = getApplicationContext();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        resetDensity();
    }

    public void resetDensity(){
        Point size = new Point();
        ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getSize(size);
        getResources().getDisplayMetrics().xdpi = size.x/DESIGN_WIDTH*72f;
    }

    public static Context getApplicationContent(){
        return mcontext;
    }


}
