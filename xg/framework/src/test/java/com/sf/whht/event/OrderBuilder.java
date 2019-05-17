package com.sf.whht.event;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Map;

public class OrderBuilder {
    public static Order build() {
        Order order = new Order();
        order.setOrderId(IdUtil.simpleUUID());
        order.setUserId(RandomUtil.randomNumbers(11));
        order.setPrice(new BigDecimal(52.5));

        LinkedList<Map<String, Object>> mapList = new LinkedList<>();
        mapList.add(MapUtil.builder("name", (Object) "MiPhone 5").put("goodsId", 1).build());
        mapList.add(MapUtil.builder("name", (Object) "Black shoes").put("goodsId", 2).build());
        order.setGoods(mapList);
        return order;
    }
}
