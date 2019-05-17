package com.sf.whht.beetl;

import com.sf.whht.util.I18NUtil;
import org.beetl.core.Context;
import org.beetl.core.Function;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;

public class I18n implements Function {
    @Override
    public Object call(Object[] objects, Context context) {
        HttpServletRequest request = ((HttpServletRequest) context.getGlobal("request"));
        return I18NUtil.get((String) objects[0], RequestContextUtils.getLocale(request));
    }
}
