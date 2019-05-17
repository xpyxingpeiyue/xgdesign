package com.sf.whht.event.annotation;

import com.sf.whht.event.IEvent;
import com.sf.whht.event.IEvent;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EventSubscribe {
    @AliasFor("event")
    Class<? extends IEvent> value() default IEvent.class;

    @AliasFor("value")
    Class<? extends IEvent> event() default IEvent.class;

    // Empty时会自动生成
    String group() default "";
}
