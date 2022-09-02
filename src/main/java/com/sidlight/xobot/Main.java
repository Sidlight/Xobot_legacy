package com.sidlight.xobot;

import com.sidlight.xobot.core.Messenger;
import com.sidlight.xobot.core.statemachine.StateMachine;
import com.sidlight.xobot.core.statemachine.StateMachineException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {


    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class, args);

        StateMachine.initStateMachine();
        Messenger.initBots();
    }
}
