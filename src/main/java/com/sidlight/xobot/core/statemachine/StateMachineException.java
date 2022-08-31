package com.sidlight.xobot.core.statemachine;

import lombok.Getter;

public class StateMachineException extends Exception {

    @Getter
    private final String stateMachineException;

    public StateMachineException(String stateMachineException, Exception e) {
        super(e != null ? e : new Exception(stateMachineException));
        this.stateMachineException = stateMachineException;
    }
}
