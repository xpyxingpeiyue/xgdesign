package com.sf.whht.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

@SuppressWarnings("unused")
@ConfigurationProperties(
        prefix = "spring.dubbo"
)
public class DubboProperties {
    private String scan;
    private ApplicationConfig application;
    private RegistryConfig registry;
    private ProtocolConfig protocol;
    private ConsumerConfig consumer;

    public String getScan() {
        return this.scan;
    }

    public void setScan(String scan) {
        this.scan = scan;
    }

    public ApplicationConfig getApplication() {
        return this.application;
    }

    public void setApplication(ApplicationConfig application) {
        this.application = application;
    }

    public RegistryConfig getRegistry() {
        return this.registry;
    }

    public void setRegistry(RegistryConfig registry) {
        this.registry = registry;
    }

    public ProtocolConfig getProtocol() {
        return this.protocol;
    }

    public void setProtocol(ProtocolConfig protocol) {
        this.protocol = protocol;
    }

    public ConsumerConfig getConsumer() {
        return consumer;
    }

    public void setConsumer(ConsumerConfig consumer) {
        this.consumer = consumer;
    }
}