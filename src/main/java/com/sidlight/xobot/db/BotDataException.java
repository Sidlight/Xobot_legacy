package com.sidlight.xobot.db;

import com.sidlight.xobot.core.BotException;

public class BotDataException extends BotException {

    public BotDataException(String messages) {
        super(messages);
    }

    public BotDataException(String messages, String sendMessages) {
        super(messages, sendMessages);
    }

}
