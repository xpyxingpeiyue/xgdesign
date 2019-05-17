package com.sf.whht.event;

import org.springframework.beans.factory.BeanFactory;

import java.lang.reflect.Method;

public abstract class EventHandler {
    private BeanFactory beanFactory;

    public abstract void process(IEvent event);

    public abstract void register(Class<? extends IEvent> eventClass, String group, Object bean, Method method);

    @SuppressWarnings("WeakerAccess")
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @SuppressWarnings("WeakerAccess")
    public BeanFactory getBeanFactory() {
        return beanFactory;
    }
}