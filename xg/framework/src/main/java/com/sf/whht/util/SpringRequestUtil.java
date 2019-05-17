package com.sf.whht.util;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("unused")
public class SpringRequestUtil extends RequestUtil {
    public static boolean isMethod(String method) {
        return isMethod(getRequest(), method);
    }

    public static boolean isGet() {
        return isGet(getRequest());
    }

    public static boolean isPost() {
        return isPost(getRequest());
    }

    public static boolean isAjax() {
        return isAjax(getRequest());
    }

    public static boolean isPjax() {
        return isPjax(getRequest());
    }

    public static String getClientIp() {
        return getClientIp(getRequest());
    }

    public static String getBasePath(String scheme) {
        return getBasePath(getRequest(), scheme);
    }

    public static String getBasePath() {
        return getBasePath(getRequest());
    }

    private static HttpServletRequest getRequest() {
        return RequestContextHolderUtil.getRequest();
    }
}
