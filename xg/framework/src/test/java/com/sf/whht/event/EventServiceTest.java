package com.sf.whht.event;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.sf.whht.event.annotation.EventSubscribe;
import com.sf.whht.event.user.UserCreatingEvent;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.Map;

public class EventServiceTest {
    private static final Log log = LogFactory.get();

    public static void main(String[] args) {
        EventBean bean = new EventBean();
        EventConfiguration configuration = new EventConfiguration();
        configuration.setBeanFactory(new DefaultListableBeanFactory());
        configuration.setEventHandler(new DefaultEventHandler());

        EventHandler eventHandler = configuration.getEventHandler();

        Map<Method, EventSubscribe> annotatedMethods = MethodIntrospector.selectMethods(bean.getClass(),
                (MethodIntrospector.MetadataLookup<EventSubscribe>) method ->
                        AnnotationUtils.findAnnotation(method, EventSubscribe.class));
        annotatedMethods.forEach((method, eventSubscribe) -> {
            Class<? extends IEvent> eventClass
                    = eventSubscribe.event() != IEvent.class ? eventSubscribe.event() : eventSubscribe.value();
            String group = eventSubscribe.group();
            eventHandler.register(eventClass, group, bean, method);
        });

        UserCreatingEvent event = new UserCreatingEvent();
        event.setUserName("fsdfsdfs");
        eventHandler.process(event);
    }

    public static class EventBean {
        @EventSubscribe(value = UserCreatingEvent.class, group = "1")
        public void listener(UserCreatingEvent event) {
            log.info("1 UserCreatingEvent: {}", event);
        }

        @EventSubscribe(value = UserCreatingEvent.class, group = "2")
        public void listener2(UserCreatingEvent event) {
            log.info("2 UserCreatingEvent: {}", event.getUserName());
        }

        @EventSubscribe(value = UserCreatingEvent.class, group = "3")
        public void listener3(UserCreatingEvent event) {
            log.info("3 UserCreatingEvent: {}", event.getId());
        }
    }
}
