package com.sf.whht.dubbo;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.dubbo.config.spring.AnnotationBean;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;

public class DubboConfigurationApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final Log log = LogFactory.get();

    public void initialize(@NonNull ConfigurableApplicationContext applicationContext) {
        log.info("Dubbo running...");

        Environment env = applicationContext.getEnvironment();
        String scan = env.getProperty("spring.dubbo.scan");
        if (scan != null) {
            AnnotationBean scanner = BeanUtils.instantiateClass(AnnotationBean.class);

            scanner.setPackage(scan);
            scanner.setApplicationContext(applicationContext);
            applicationContext.addBeanFactoryPostProcessor(scanner);
            applicationContext.getBeanFactory().addBeanPostProcessor(scanner);
            applicationContext.getBeanFactory().registerSingleton("annotationBean", scanner);
        }
    }
}