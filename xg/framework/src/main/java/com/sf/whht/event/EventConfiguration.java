package com.sf.whht.event;

import com.sf.whht.event.annotation.EventAnnotationBeanPostProcessor;
import com.sf.whht.event.annotation.EventAnnotationBeanPostProcessor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

//@Component
public class EventConfiguration {
//    private static final EventHandler DEFAULT_EVENT_HANDLER = new KafkaEventHandler();

    private EventHandler eventHandler;
    private BeanFactory beanFactory;

    public EventConfiguration() {
//        this.eventHandler = DEFAULT_EVENT_HANDLER;
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public EventHandler getEventHandler() {
        if (eventHandler != null) {
            eventHandler.setBeanFactory(beanFactory);
        }
        return eventHandler;
    }

    @Bean("eventAnnotationBeanPostProcessor")
    public EventAnnotationBeanPostProcessor eventAnnotationBeanPostProcessor() {
        EventAnnotationBeanPostProcessor postProcessor = new EventAnnotationBeanPostProcessor();
        postProcessor.setEventConfiguration(this);
        return postProcessor;
    }
}