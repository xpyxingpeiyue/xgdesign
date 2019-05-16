package cn.com.xg.log.adapter;

public abstract class LogFactory {
    public LogFactory() {
    }

    public static Log getLog(Class<?> clazz) {
        return getLog(clazz.getName());
    }

    public static Log getLog(String name) {
        return Adapter.createLog(name);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static LogFactory getFactory() {
        return new LogFactory() {
        };
    }

    /**
     * @deprecated
     */
    @Deprecated
    public Log getInstance(Class<?> clazz) {
        return getLog(clazz);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public Log getInstance(String name) {
        return getLog(name);
    }
}
