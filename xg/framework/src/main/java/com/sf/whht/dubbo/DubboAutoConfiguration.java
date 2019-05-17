package com.sf.whht.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@EnableConfigurationProperties({DubboProperties.class})
public class DubboAutoConfiguration {
    @Resource
    private DubboProperties dubboProperties;

    @Bean
    public ApplicationConfig requestApplicationConfig() {
        return this.dubboProperties.getApplication();
    }

    @Bean
    public RegistryConfig requestRegistryConfig() {
        return this.dubboProperties.getRegistry();
    }

    @Bean
    public ProtocolConfig requestProtocolConfig() {
        return this.dubboProperties.getProtocol();
    }

    @Bean
    public ConsumerConfig requestConsumerConfig() {
        return this.dubboProperties.getConsumer();
    }
}