package jx.com.shoppingtrolley_zihenguncle.addcar;

/**
 * @author 郭淄恒
 */
public class AddBean {
    private int commodityId;
    private int count;

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public AddBean(int commodityId, int count) {
        this.commodityId = commodityId;
        this.count = count;
    }
}
