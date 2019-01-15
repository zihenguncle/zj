package jx.com.shoppingtrolley_zihenguncle.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import jx.com.shoppingtrolley_zihenguncle.app.MyApplication;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author 郭淄恒
 * 请求网络
 */
public class RetrofitUtils {

    private OkHttpClient mClient;
    //private static String BASE_URL = "http://172.17.8.100/small/";
    private static String BASE_URL = "http://mobile.bwstudent.com/small/";
    //单例
    private static RetrofitUtils instance;
    private final BaseApis baseApis;

    public static RetrofitUtils getInstance(){
        if(instance == null){
            synchronized (RetrofitUtils.class){
                instance = new RetrofitUtils();
            }
        }
        return instance;
    }

    public RetrofitUtils() {
        //日志拦截
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        mClient = new OkHttpClient.Builder()
                .readTimeout(5000,TimeUnit.SECONDS)
                .connectTimeout(5000,TimeUnit.SECONDS)
                .writeTimeout(5000,TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        //拿到请求
                        Request request = chain.request();
                        //取出登陆时获得的两个id
                        SharedPreferences preferences = MyApplication.getApplicationContent().getSharedPreferences("login_db", Context.MODE_PRIVATE);
                        String userId = preferences.getString("userId", "");
                        String sessionId = preferences.getString("sessionId", "");

                        //重写构造请求
                        Request.Builder builder = request.newBuilder();
                        //把原来请求的数据原样放进去
                        builder.method(request.method(),request.body());

                        if(!TextUtils.isEmpty(userId)&&!TextUtils.isEmpty(sessionId)){
                            builder.addHeader("userId",userId);
                            builder.addHeader("sessionId",sessionId);
                        }

                        //打包
                        Request request1 = builder.build();

                        return chain.proceed(request1);
                    }
                })
                //设置请求失败不会重新请求
                .retryOnConnectionFailure(true)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .client(mClient)
                .build();
        baseApis = retrofit.create(BaseApis.class);
    }


    public Map<String, RequestBody> generateRequestBody(Map<String,String> requestDataMap){
        Map<String,RequestBody> requestBodyMap = new HashMap<>();
        for (String key : requestBodyMap.keySet()){
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/from-data"),
                    requestDataMap.get(key) == null ? "" :requestDataMap.get(key));
            requestBodyMap.put(key,requestBody);
        }
        return requestBodyMap;
    }

    /**
     *     GET请求
     * @param url
     * @return
     */
    public void get(String url,HttpListener listener){
        baseApis.get(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }

    /**
     *      POSTFROM请求
     * @param url
     * @param map
     * @return
     */
    public void postFromBody(String url,Map<String,RequestBody> map,HttpListener listener){
        if(map == null){
            map = new HashMap<>();
        }
        baseApis.postFromBody(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }

    /**
     *      POST请求
     * @param url
     * @param map
     * @param listener
     */
    public void post(String url,Map<String,String> map,HttpListener listener){
        if(map == null){
            map = new HashMap<>();
        }
        baseApis.post(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }

    public void put(String url,Map<String,String> map,HttpListener listener){
        if(map == null){
            map = new HashMap<>();
        }
        baseApis.put(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }



    /**
     *      DELETE请求
     * @param url
     * @param listener
     */

    public void delete(String url,HttpListener listener){
        baseApis.delete(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }


    private Observer getObserver(final HttpListener listener){
        Observer observer = new Observer<ResponseBody>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                if(listener != null){
                    listener.failed(e.getMessage());
                }
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String string = responseBody.string();
                    if(listener != null){
                        listener.success(string);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    if(listener != null){
                        listener.failed(e.getMessage());
                    }
                }
            }
        };
        return observer;
    }


    public HttpListener listener;
    public void setHttpListener(HttpListener httpListener){
        this.listener = httpListener;
    }
    public interface HttpListener{
        void success(String data);
        void failed(String error);
    }

}
