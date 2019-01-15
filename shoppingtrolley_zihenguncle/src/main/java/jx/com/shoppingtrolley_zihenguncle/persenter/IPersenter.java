package jx.com.shoppingtrolley_zihenguncle.persenter;

import java.util.Map;

public interface IPersenter {
    void startRequest(String url, Map<String,String> params,Class clazz);
    void startRequestget(String url,Class clazz);
    void startRequestDelete(String url,Class clazz);
    void startRequestPut(String url, Map<String,String> params,Class clazz);
}
