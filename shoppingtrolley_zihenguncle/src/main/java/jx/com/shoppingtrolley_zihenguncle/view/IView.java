package jx.com.shoppingtrolley_zihenguncle.view;

public interface IView<T> {
    void success(T data);
    void failed(String error);
}
