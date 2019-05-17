package com.sf.whht.event;

import cn.hutool.json.JSONUtil;
import com.sf.whht.event.router.RouterPushEvent;

public class EventTest {
    public static void main(String[] args) {
        Order order = OrderBuilder.build();
        RouterPushEvent event = new RouterPushEvent(order, order.getUserId(), order.getOrderId());
        EventBridge eventBridge = EventBridge.create(event).addListener(e -> System.out.println(JSONUtil.toJsonStr(e)));
        eventBridge.notifyListeners();
    }
}
