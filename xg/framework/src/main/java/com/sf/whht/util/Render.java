package com.sf.whht.util;

import cn.hutool.core.map.MapProxy;

import java.util.HashMap;
import java.util.function.Supplier;

public class Render extends MapProxy {
    private static Supplier<? extends Render> supplier = Render::new;

    public Render() {
        super(new HashMap<>());
    }

    public static Caller use(Supplier<? extends Render> caller) {
        return new Caller(caller);
    }

    public static Render success() {
        return success(null);
    }

    public static Render success(Object data) {
        return use(supplier).success(data);
    }

    public static Render error(String msg) {
        return use(supplier).error(msg);
    }

    public boolean isSuccess() {
        return getBool("success", false);
    }

    public void setSuccess(boolean success) {
        put("success", success);
    }

    public String getMsg() {
        return getStr("msg");
    }

    public void setMsg(String msg) {
        put("msg", msg);
    }

    public Object getData() {
        return get("data");
    }

    public void setData(Object data) {
        put("data", data);
    }

    public static class Caller {
        private Supplier<? extends Render> supplier;

        private Caller(Supplier<? extends Render> supplier) {
            this.supplier = supplier;
        }

        private Render getRender(boolean success, String msg, Object data) {
            Render render = supplier.get();
            render.setSuccess(success);
            render.setMsg(msg);
            if (data != null) {
                render.setData(data);
            }
            return render;
        }

        public Render success() {
            return success(null);
        }

        public Render success(Object data) {
            return getRender(true, null, data);
        }

        public Render error(String msg) {
            return getRender(false, msg, null);
        }
        public Render error(String msg,Object data) {
            return getRender(false, msg, data);
        }
    }
}
