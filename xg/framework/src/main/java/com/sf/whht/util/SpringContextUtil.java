package com.sf.whht.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtil implements ApplicationContextAware {

    // Spring应用上下文环境
    private static final SpringContextHolder holder = new SpringContextHolder();

    /**
     * 实现ApplicationContextAware接口的回调方法。设置上下文环境
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        holder.setApplicationContext(applicationContext);
    }

    /**
     * @return ApplicationContext
     */
    @SuppressWarnings("unused")
    public static ApplicationContext getApplicationContext() {
        return holder.getApplicationContext();
    }

    /**
     * 获取对象
     * @return Object
     */
    @SuppressWarnings("unused")
    public static Object getBean(String name) throws BeansException {
        return holder.getApplicationContext().getBean(name);
    }

    @SuppressWarnings("unused")
    public static <B> B getBean(Class<B> clazz) {
        return holder.getApplicationContext().getBean(clazz);
    }

    private static class SpringContextHolder {
        private ApplicationContext applicationContext;

        ApplicationContext getApplicationContext() {
            return applicationContext;
        }

        void setApplicationContext(ApplicationContext applicationContext) {
            this.applicationContext = applicationContext;
        }
    }
}
