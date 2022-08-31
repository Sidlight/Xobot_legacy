package com.sidlight.xobot.core;

public record UserIdentifier(String chatId, Messenger messenger){

    public MessageExecutor getMessageExecutor(){
        return messenger.getMessageExecutor(chatId);
    }
}
