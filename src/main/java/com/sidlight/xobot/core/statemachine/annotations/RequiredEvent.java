package com.sidlight.xobot.core.statemachine.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface RequiredEvent {
//Евент должен обязательно реализован, иначе сервер не запустится
}
