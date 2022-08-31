package com.sidlight.xobot.core;

import lombok.Getter;

public class BotException extends RuntimeException {
    @Getter
    private final String sendUserMessages;
    public BotException(String messages){
        super(messages);
        sendUserMessages = null;
    }

    public BotException(String messages, String sendUserMessages){
        super(messages);
        this.sendUserMessages = sendUserMessages;
    }
}
