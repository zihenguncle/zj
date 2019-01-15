package jx.com.shoppingtrolley_zihenguncle.callback;

public interface ICallBack<T> {
    void successData(T data);
    void failedData(String error);
}
