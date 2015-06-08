package me.b3nw.dev.Events;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventHandle {

    Type type();

    enum Type {
        KEYPRESS,
        LOSTFOCUS,
        NICKANNOUNCE;
    }

}
