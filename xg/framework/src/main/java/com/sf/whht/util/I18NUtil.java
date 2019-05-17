package com.sf.whht.util;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.springframework.context.MessageSource;

import java.util.Locale;

public class I18NUtil {
    private static final Log log = LogFactory.get();
    private static final MessageSource messageSource = SpringContextUtil.getBean(MessageSource.class);

    private I18NUtil() {
    }

    public static String get(String key, Locale locale, Object... objects) {
        try {
            return messageSource.getMessage(key, objects != null ? objects : new Object[0], locale);
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return "";
    }

    public static String get(String key, Object... objects) {
        Locale locale = RequestContextHolderUtil.getLocale();
        return get(key, locale, objects);
    }
}
