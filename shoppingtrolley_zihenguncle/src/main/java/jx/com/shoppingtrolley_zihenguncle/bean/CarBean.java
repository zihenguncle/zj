package jx.com.shoppingtrolley_zihenguncle.bean;

import android.widget.CheckBox;

import java.io.Serializable;
import java.util.List;

public class CarBean implements Serializable {

    private String status;
    private String messages;
    private List<Result> result;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public static class Result implements Serializable{
        private int commodityId;
        private String commodityName;
        private String pic;
        private Double price;
        private int count;
        private boolean check;

        public Result(int commodityId, String commodityName, String pic, Double price, int count, boolean check) {
            this.commodityId = commodityId;
            this.commodityName = commodityName;
            this.pic = pic;
            this.price = price;
            this.count = count;
            this.check = check;
        }

        public boolean isCheck() {
            return check;
        }

        public void setCheck(boolean check) {
            this.check = check;
        }


        public int getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(int commodityId) {
            this.commodityId = commodityId;
        }

        public String getCommodityName() {
            return commodityName;
        }

        public void setCommodityName(String commodityName) {
            this.commodityName = commodityName;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

}
