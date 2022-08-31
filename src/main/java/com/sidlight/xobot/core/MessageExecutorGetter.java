package com.sidlight.xobot.core;

@FunctionalInterface
public interface MessageExecutorGetter {
    MessageExecutor getMessageExecutor(String chatId);
}
