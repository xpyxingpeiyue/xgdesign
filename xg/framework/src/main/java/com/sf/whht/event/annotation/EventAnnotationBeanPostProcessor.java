package com.sf.whht.event.annotation;

import com.sf.whht.event.IEvent;
import com.sf.whht.event.EventConfiguration;
import com.sf.whht.event.IEvent;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.Map;

public class EventAnnotationBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware {
    private EventConfiguration eventConfiguration;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetClass = AopUtils.getTargetClass(bean);
        Map<Method, EventSubscribe> annotatedMethods = MethodIntrospector.selectMethods(targetClass,
                (MethodIntrospector.MetadataLookup<EventSubscribe>) method ->
                        AnnotationUtils.findAnnotation(method, EventSubscribe.class));

        if (!annotatedMethods.isEmpty() && eventConfiguration != null) {
            annotatedMethods.forEach((method, eventSubscribe) -> {
                Class<? extends IEvent> eventClass
                        = eventSubscribe.event() != IEvent.class ? eventSubscribe.event() : eventSubscribe.value();
                String group = eventSubscribe.group();
                eventConfiguration.getEventHandler().register(eventClass, group, bean, method);
            });
        }
        return bean;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.eventConfiguration.setBeanFactory(beanFactory);
    }

    public void setEventConfiguration(EventConfiguration eventConfiguration) {
        this.eventConfiguration = eventConfiguration;
    }
}
