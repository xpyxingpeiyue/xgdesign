package com.sf.whht.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Haulon on 2018/05/25
 */
@SuppressWarnings("unused")
public class RequestUtil {
    @SuppressWarnings("WeakerAccess")
    public static boolean isMethod(HttpServletRequest request, String method) {
        return method != null && method.equalsIgnoreCase(request.getMethod());
    }

    public static boolean isGet(HttpServletRequest request) {
        return isMethod(request, "GET");
    }

    public static boolean isPost(HttpServletRequest request) {
        return isMethod(request, "POST");
    }

    public static boolean isAjax(HttpServletRequest request) {
        return null != request.getHeader("x-requested-with");
    }

    public static boolean isPjax(HttpServletRequest request) {
        return null != request.getHeader("X-PJAX");
    }

    /**
     * 获取ip地址
     */
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    @SuppressWarnings("WeakerAccess")
    public static String getBasePath(HttpServletRequest request, String scheme) {
        String path = request.getContextPath();
        int port = request.getServerPort();
        return (scheme == null ? "" : scheme + ":") + "//" + request.getServerName()
                + (port == 80 || port == 443 ? "" : ":" + port) + path + "/";
    }

    public static String getBasePath(HttpServletRequest request) {
        return getBasePath(request, null);
    }
}
