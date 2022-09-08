package com.sidlight.xobot;

import com.sidlight.xobot.core.message.Messenger;
import com.sidlight.xobot.core.statemachine.StateMachine;
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
