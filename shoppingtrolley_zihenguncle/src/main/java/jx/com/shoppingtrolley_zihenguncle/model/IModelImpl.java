package jx.com.shoppingtrolley_zihenguncle.model;

import com.google.gson.Gson;

import java.util.Map;

import jx.com.shoppingtrolley_zihenguncle.callback.ICallBack;
import jx.com.shoppingtrolley_zihenguncle.network.RetrofitUtils;

/**
 * @author 郭淄恒
 */
public class IModelImpl implements IModel {
    @Override
    public void requestData(String url, Map<String, String> map, final Class clazz, final ICallBack iCallBack) {

        RetrofitUtils.getInstance().post(url, map, new RetrofitUtils.HttpListener() {
            @Override
            public void success(String data) {
                try {
                    Object o = new Gson().fromJson(data, clazz);
                    if(iCallBack != null){
                        iCallBack.successData(o);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    if(iCallBack != null){
                        iCallBack.failedData(e.getMessage());
                    }
                }
            }

            @Override
            public void failed(String error) {
                if(iCallBack != null){
                    iCallBack.failedData(error);
                }
            }
        });

    }

    @Override
    public void requestDataGet(String url, final Class clazz, final ICallBack iCallBack) {
        RetrofitUtils.getInstance().get(url, new RetrofitUtils.HttpListener() {
            @Override
            public void success(String data) {
                Object o = new Gson().fromJson(data, clazz);
                if(iCallBack != null){
                    iCallBack.successData(o);
                }
            }

            @Override
            public void failed(String error) {
                if(iCallBack != null){
                    iCallBack.failedData(error);
                }
            }
        });
    }

    @Override
    public void requestDataDelete(String url, final Class clazz, final ICallBack iCallBack) {
        RetrofitUtils.getInstance().delete(url, new RetrofitUtils.HttpListener() {
            @Override
            public void success(String data) {
                Object o = new Gson().fromJson(data, clazz);
                if(iCallBack != null){
                    iCallBack.successData(o);
                }
            }

            @Override
            public void failed(String error) {
                if(iCallBack != null){
                    iCallBack.failedData(error);
                }
            }
        });
    }

    @Override
    public void requestDataPut(String url, Map<String, String> map, final Class clazz, final ICallBack iCallBack) {
        RetrofitUtils.getInstance().put(url, map, new RetrofitUtils.HttpListener() {
            @Override
            public void success(String data) {
                try {
                    Object o = new Gson().fromJson(data, clazz);
                    if(iCallBack != null){
                        iCallBack.successData(o);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    if(iCallBack != null){
                        iCallBack.failedData(e.getMessage());
                    }
                }
            }

            @Override
            public void failed(String error) {
                if(iCallBack != null){
                    iCallBack.failedData(error);
                }
            }
        });
    }


}
