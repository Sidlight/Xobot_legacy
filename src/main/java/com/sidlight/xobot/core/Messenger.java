package com.sidlight.xobot.core;

public enum Messenger {
        ALL
    ;

    private MessageExecutorGetter messageExecutorGetter;

    public MessageExecutor getMessageExecutor(String chatId) {
        return messageExecutorGetter.getMessageExecutor(chatId);
    }

}
