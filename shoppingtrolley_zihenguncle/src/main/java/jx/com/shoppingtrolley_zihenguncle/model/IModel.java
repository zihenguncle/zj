package jx.com.shoppingtrolley_zihenguncle.model;

import java.util.Map;

import jx.com.shoppingtrolley_zihenguncle.callback.ICallBack;

public interface IModel {
    void requestData(String url, Map<String,String> map, Class clazz, ICallBack iCallBack);
    void requestDataGet(String url,Class clazz,ICallBack iCallBack);
    void requestDataDelete(String url,Class clazz,ICallBack iCallBack);
    void requestDataPut(String url, Map<String,String> map, Class clazz, ICallBack iCallBack);
}
