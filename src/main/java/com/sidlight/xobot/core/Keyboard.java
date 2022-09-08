package com.sidlight.xobot.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target( ElementType.METHOD)
public @interface Keyboard {
    String tag();
    String description() default "";
    String command();
    int row() default -1;
    int column() default -1;


}
