package com.sf.whht.util;

import com.sf.kafka.api.produce.IKafkaProducer;
import com.sf.kafka.check.util.JsonUtil;
import com.sf.whht.spi.router.router.TtRouter;

import java.util.ArrayList;
import java.util.List;

public class ProducerSendUtil {
    private static final IKafkaProducer kafkaRouterProducer = (IKafkaProducer) SpringContextUtil.getBean("routerProducer");
    private static final Object topic = SpringContextUtil.getBean("topic");

    //指定topic
    public static void sendRouter(String topic, List<TtRouter> routers) {
        List<String> routerString = new ArrayList<>();
        routers.forEach(r -> routerString.add(JsonUtil.writeValueAsString(r)));
        //router对象太通过json转为String
        kafkaRouterProducer.batchSendString(topic, routerString);
    }

    public static void sendRouter(List<TtRouter> routers) {
        List<String> routerString = new ArrayList<>();
        routers.forEach(r -> routerString.add(JsonUtil.writeValueAsString(r)));
        //router对象太通过json转为String
        kafkaRouterProducer.batchSendString(topic.toString(), routerString);
    }
}
