package jx.com.shoppingtrolley_zihenguncle.bean;

public class EventBusBean {

    private int tag;
    private Object obj;

    public EventBusBean(int tag, Object obj) {
        this.tag = tag;
        this.obj = obj;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
