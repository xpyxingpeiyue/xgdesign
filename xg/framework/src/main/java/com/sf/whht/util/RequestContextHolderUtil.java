package com.sf.whht.util;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

/**
 * 应用级对象获取工具类
 */
public class RequestContextHolderUtil {
    private RequestContextHolderUtil() {}

    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }

    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    @SuppressWarnings("WeakerAccess")
    public static ServletRequestAttributes getRequestAttributes() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
    }

    @SuppressWarnings("unused")
    public static ServletContext getServletContext() {
        return ContextLoader.getCurrentWebApplicationContext().getServletContext();
    }

    public static Locale getLocale() {
        return RequestContextUtils.getLocale(getRequest());
    }
}