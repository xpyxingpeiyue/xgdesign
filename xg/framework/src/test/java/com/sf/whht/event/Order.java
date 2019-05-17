package com.sf.whht.event;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Map;

public class Order {
    private String orderId;
    private String userId;
    private BigDecimal price;
    private LinkedList<Map<String, Object>> goods;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LinkedList<Map<String, Object>> getGoods() {
        return goods;
    }

    public void setGoods(LinkedList<Map<String, Object>> goods) {
        this.goods = goods;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
