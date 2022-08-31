package com.sidlight.xobot.core.statemachine.enums;

import com.sidlight.xobot.core.statemachine.annotations.RequiredEvent;

public class BasicEvent {
    @RequiredEvent
    public static final String START_EVENT = "start_event";
    @RequiredEvent
    public static final String CONFIRM_NAME = "confirm_name";
    @RequiredEvent
    public static final String HARD_STOP_EVENT = "hard_stop_event";

}
