package jx.com.shoppingtrolley_zihenguncle.persenter;

import java.util.Map;

import jx.com.shoppingtrolley_zihenguncle.callback.ICallBack;
import jx.com.shoppingtrolley_zihenguncle.model.IModelImpl;
import jx.com.shoppingtrolley_zihenguncle.view.IView;

public class IPersenterImpl implements IPersenter {
    private IView iView;
    private IModelImpl iModel;

    public IPersenterImpl(IView iView) {
        this.iView = iView;
        iModel = new IModelImpl();
    }

    @Override
    public void startRequest(String url, Map<String, String> params, Class clazz) {
        iModel.requestData(url, params, clazz, new ICallBack() {
            @Override
            public void successData(Object data) {
                iView.success(data);
            }

            @Override
            public void failedData(String error) {
                iView.failed(error);
            }
        });
    }


    @Override
    public void startRequestget(String url, Class clazz) {
        iModel.requestDataGet(url, clazz, new ICallBack() {
            @Override
            public void successData(Object data) {
                iView.success(data);
            }

            @Override
            public void failedData(String error) {
                iView.failed(error);
            }
        });
    }

    @Override
    public void startRequestDelete(String url, Class clazz) {
        iModel.requestDataDelete(url, clazz, new ICallBack() {
            @Override
            public void successData(Object data) {
                iView.success(data);
            }

            @Override
            public void failedData(String error) {
                iView.failed(error);
            }
        });
    }

    @Override
    public void startRequestPut(String url, Map<String, String> params, Class clazz) {
        iModel.requestDataPut(url, params, clazz, new ICallBack() {
            @Override
            public void successData(Object data) {
                iView.success(data);
            }

            @Override
            public void failedData(String error) {
                iView.failed(error);
            }
        });
    }

    public void detach(){
        if(iView != null){
            iView = null;
        }
        if(iModel != null){
            iModel = null;
        }
    }
}
