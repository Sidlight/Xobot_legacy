package com.sidlight.xobot.core.command;

import com.sidlight.xobot.core.message.Messenger;
import com.sidlight.xobot.core.access.Role;
import com.sidlight.xobot.core.access.StateRegister;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target( ElementType.METHOD)
public @interface Command {

    String command();

    Role[] roles() default Role.ALL;

    Messenger[] messengers() default Messenger.ALL;

    String description() default "";

    StateRegister stateRegister() default StateRegister.CONFIRMED;
}