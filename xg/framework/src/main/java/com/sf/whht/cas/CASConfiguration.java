package com.sf.whht.cas;

import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

//@Configuration
public class CASConfiguration {
    private static final String CAS_SERVER_URL_PREFIX = "https://cas.sf-express.com/cas";
    private static final String CAS_SERVER_LOGIN_URL = CAS_SERVER_URL_PREFIX + "/login";
    private static final String BASE_PATH = "http://127.0.0.1:8900";

    @Bean
    public FilterRegistrationBean casFilterRegistration() {
        AuthenticationFilter filter = new AuthenticationFilter();
        String name = "CAS Filter";
        String url = "/*";
        Map<String, String> parameter = new HashMap<>();
        parameter.put("casServerLoginUrl", CAS_SERVER_LOGIN_URL);
        parameter.put("serverName", BASE_PATH);
        FilterRegistrationBean<AuthenticationFilter> registration
                = createFilterRegistrationBean(filter, name, url, parameter);
        registration.setOrder(1); // 设置优先级
        return registration;
    }

    @Bean
    public FilterRegistrationBean casValidationFilterRegistration() {
        Cas20ProxyReceivingTicketValidationFilter filter = new Cas20ProxyReceivingTicketValidationFilter();
        String name = "CAS Validation Filter";
        String url = "/*";
        Map<String, String> parameter = new HashMap<>();
        parameter.put("casServerUrlPrefix", CAS_SERVER_URL_PREFIX);
        parameter.put("serverName", BASE_PATH);
        FilterRegistrationBean<Cas20ProxyReceivingTicketValidationFilter> registration
                = createFilterRegistrationBean(filter, name, url, parameter);
        registration.setOrder(1); // 设置优先级
        return registration;
    }

    private <T extends Filter> FilterRegistrationBean<T> createFilterRegistrationBean(T filter, String name, String url, Map<String, String> initParameter) {
        FilterRegistrationBean<T> registration = new FilterRegistrationBean<>();
        registration.setFilter(filter); // 添加过滤器
        registration.addUrlPatterns(url); // 设置过滤路径，/*所有路径
        // 添加默认参数
        initParameter.forEach(registration::addInitParameter);
        registration.setName(name); // 过滤器名称
        return registration;
    }
}
