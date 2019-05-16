package cn.com.xg.log.adapter;

import com.sun.security.jgss.InquireSecContextPermission;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

//转换器  根据引入不同的日志转换
public class Adapter {
    private static final String LOG4J_SPI = "org.apache.logging.log4j.spi.ExtendedLogger";
    private static final String LOG4J_SLF4J_PROVIDER = "org.apache.logging.slf4j.SLF4JProvider";
    private static final String SLF4J_SPI = "org.slf4j.spi.LocationAwareLogger";
    private static final String SLF4J_API = "org.slf4j.Logger";
    private static final Adapter.LogApi logApi;

    public static Log createLog(String name) {
        switch (logApi) {
            case LOG4J:
                return Adapter.Log4jAdapter.createLog(name);
            case LOG4J_SLF4J:
                return null;
            case SLF4J:
                return null;
            default:
                //JavaUtil 自带logger
                return Adapter.Log4jAdapter.createLog(name);
        }
    }

    //初始化日志对象判断
    private static boolean isPresent(String className) {
        try {
            //TODO:第二个参数为true或者false区别
            Class.forName(className, false, Adapter.class.getClassLoader());
            return true;
        } catch (ClassNotFoundException var1) {
            return false;
        }
    }

    //转换器里面添加不同转换器类
    //LOG4J_SPI

    private static class JavaUtilLog implements Log, Serializable {
        private transient Logger logger;
        private String name;

        public JavaUtilLog(String name) {
            this.name = name;
            this.logger = Logger.getLogger(name);
        }

        @Override
        public boolean isFatalEnabled() {
            return this.isErrorEnabled();
        }
        @Override
        public boolean isErrorEnabled() {
            return this.logger.isLoggable(Level.SEVERE);
        }

        @Override
        public boolean isWarnEnabled() {
            return this.logger.isLoggable(Level.WARNING);
        }

        @Override
        public boolean isInfoEnabled() {
            return this.logger.isLoggable(Level.INFO);
        }

        @Override
        public boolean isDebugEnabled() {
            return this.logger.isLoggable(Level.FINE);
        }

        @Override
        public boolean isTraceEnabled() {
            return this.logger.isLoggable(Level.FINEST);
        }

        @Override
        public void fatal(Object var1) {
        }

        @Override
        public void fatal(Object var1, Throwable var2) {

        }

        @Override
        public void error(Object message) {
        }

        @Override
        public void error(Object var1, Throwable var2) {

        }

        @Override
        public void warn(Object var1) {

        }

        @Override
        public void warn(Object var1, Throwable var2) {

        }

        @Override
        public void info(Object message) {
            this.log(Level.INFO,message,null);
        }

        @Override
        public void info(Object var1, Throwable var2) {

        }

        @Override
        public void debug(Object var1) {

        }

        @Override
        public void debug(Object var1, Throwable var2) {

        }

        @Override
        public void trace(Object var1) {

        }

        @Override
        public void trace(Object var1, Throwable var2) {

        }
        private void log(Level level, Object message, Throwable exception) {
            if (this.logger.isLoggable(level)) {
                Object rec;
                if (message instanceof LogRecord) {
                    rec = (LogRecord)message;
                } else {
                    rec = new Adapter.LocationResolvingLogRecord(level, String.valueOf(message));
                    ((LogRecord)rec).setLoggerName(this.name);
                    ((LogRecord)rec).setResourceBundleName(this.logger.getResourceBundleName());
                    ((LogRecord)rec).setResourceBundle(this.logger.getResourceBundle());
                    ((LogRecord)rec).setThrown(exception);
                }

                this.logger.log((LogRecord)rec);
            }

        }

        protected Object readResolve() {
            return new Adapter.JavaUtilLog(this.name);
        }

    }
    //TODO: 此处为copy代码
    private static class LocationResolvingLogRecord extends LogRecord {
        private static final String FQCN = Adapter.JavaUtilLog.class.getName();
        private volatile boolean resolved;

        public LocationResolvingLogRecord(Level level, String msg) {
            super(level, msg);
        }

        public String getSourceClassName() {
            if (!this.resolved) {
                this.resolve();
            }

            return super.getSourceClassName();
        }

        public void setSourceClassName(String sourceClassName) {
            super.setSourceClassName(sourceClassName);
            this.resolved = true;
        }

        public String getSourceMethodName() {
            if (!this.resolved) {
                this.resolve();
            }

            return super.getSourceMethodName();
        }

        public void setSourceMethodName(String sourceMethodName) {
            super.setSourceMethodName(sourceMethodName);
            this.resolved = true;
        }

        private void resolve() {
            StackTraceElement[] stack = (new Throwable()).getStackTrace();
            String sourceClassName = null;
            String sourceMethodName = null;
            boolean found = false;
            StackTraceElement[] var5 = stack;
            int var6 = stack.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                StackTraceElement element = var5[var7];
                String className = element.getClassName();
                if (FQCN.equals(className)) {
                    found = true;
                } else if (found) {
                    sourceClassName = className;
                    sourceMethodName = element.getMethodName();
                    break;
                }
            }

            this.setSourceClassName(sourceClassName);
            this.setSourceMethodName(sourceMethodName);
        }

        protected Object writeReplace() {
            LogRecord serialized = new LogRecord(this.getLevel(), this.getMessage());
            serialized.setLoggerName(this.getLoggerName());
            serialized.setResourceBundle(this.getResourceBundle());
            serialized.setResourceBundleName(this.getResourceBundleName());
            serialized.setSourceClassName(this.getSourceClassName());
            serialized.setSourceMethodName(this.getSourceMethodName());
            serialized.setSequenceNumber(this.getSequenceNumber());
            serialized.setParameters(this.getParameters());
            serialized.setThreadID(this.getThreadID());
            serialized.setMillis(this.getMillis());
            serialized.setThrown(this.getThrown());
            return serialized;
        }
    }

    //Slf4jLog  adapter
    private static class Slf4jLog<T extends org.slf4j.Logger> implements Log, Serializable{

        //构造函数

        @Override
        public boolean isFatalEnabled() {
            return false;
        }

        @Override
        public boolean isErrorEnabled() {
            return false;
        }

        @Override
        public boolean isWarnEnabled() {
            return false;
        }

        @Override
        public boolean isInfoEnabled() {
            return false;
        }

        @Override
        public boolean isDebugEnabled() {
            return false;
        }

        @Override
        public boolean isTraceEnabled() {
            return false;
        }

        @Override
        public void fatal(Object var1) {

        }

        @Override
        public void fatal(Object var1, Throwable var2) {

        }

        @Override
        public void error(Object var1) {

        }

        @Override
        public void error(Object var1, Throwable var2) {

        }

        @Override
        public void warn(Object var1) {

        }

        @Override
        public void warn(Object var1, Throwable var2) {

        }

        @Override
        public void info(Object var1) {

        }

        @Override
        public void info(Object var1, Throwable var2) {

        }

        @Override
        public void debug(Object var1) {

        }

        @Override
        public void debug(Object var1, Throwable var2) {

        }

        @Override
        public void trace(Object var1) {

        }

        @Override
        public void trace(Object var1, Throwable var2) {

        }
    }


    //初始化使用什么类型的log
    static {
        if (isPresent("org.apache.logging.log4j.spi.ExtendedLogger")) {
            //TODO: 此处判断的作用？
            if (isPresent("org.apache.logging.slf4j.SLF4JProvider") && isPresent("org.slf4j.spi.LocationAwareLogger")) {
                logApi = LogApi.LOG4J_SLF4J;
            } else {
                logApi = LogApi.LOG4J;
            }
        } else if (isPresent("org.slf4j.spi.LocationAwareLogger")) {
            logApi = LogApi.LOG4J_SLF4J;
        } else if (isPresent("org.slf4j.Logger")) {
            logApi = LogApi.SLF4J;
        } else {
            logApi = LogApi.JUL;
        }

    }
    private static class Log4jAdapter{
        private Log4jAdapter(){

        }
        public static Log createLog(String name){
            return new Adapter.JavaUtilLog(name);
        }
    }

    private static enum LogApi {
        LOG4J,
        LOG4J_SLF4J,
        SLF4J,
        JUL;

        private LogApi() {

        }
    }
}
