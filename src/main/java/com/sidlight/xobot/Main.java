package com.sidlight.xobot;

import com.sidlight.xobot.core.statemachine.StateMachine;
import com.sidlight.xobot.core.statemachine.StateMachineException;

public class Main {

    public static void main(String[] args) throws StateMachineException {
        StateMachine.initStateMachine();
    }
}
