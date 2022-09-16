package com.sidlight.xobot.core.statemachine.enums;

import com.sidlight.xobot.core.statemachine.annotations.RequiredEvent;

public class BasicEvent {
    @RequiredEvent
    public static final String START = "start";
    public static final String END = "end";

    @RequiredEvent
    public static final String CANCEL = "cancel";
}
