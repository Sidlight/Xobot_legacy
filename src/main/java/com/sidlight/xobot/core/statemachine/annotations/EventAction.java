package com.sidlight.xobot.core.statemachine.annotations;

import com.sidlight.xobot.core.statemachine.enums.BasicState;
import org.atteo.classindex.IndexAnnotated;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@IndexAnnotated
public @interface EventAction {

    String targetState() default BasicState.WAITING_STATE;

    String sourceState() default BasicState.WAITING_STATE;
    String event();

    long returnToOriginalState();
}
