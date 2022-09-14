package com.sidlight.xobot.core.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Keyboard {

    String tag();

    String text();

    String description() default "";

    int row() default -1;

    int column() default -1;

}
