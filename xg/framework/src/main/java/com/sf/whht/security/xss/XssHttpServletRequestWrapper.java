package com.sf.whht.security.xss;

import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 描述 : 跨站请求防范
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    /**
     * 描述 : 构造函数
     *
     * @param request 请求对象
     */
    @SuppressWarnings("WeakerAccess")
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return value == null ? null : HtmlUtils.htmlEscape(value);
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        return value == null ? null : HtmlUtils.htmlEscape(value);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values != null) {
            int length = values.length;
            String[] escapeValues = new String[length];
            for (int i = 0; i < length; i++) {
                escapeValues[i] = HtmlUtils.htmlEscape(values[i]);
            }
            return escapeValues;
        }
        return super.getParameterValues(name);
    }
}