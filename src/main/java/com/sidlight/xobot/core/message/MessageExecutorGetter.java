package com.sidlight.xobot.core.message;

import com.sidlight.xobot.core.message.MessageExecutor;

@FunctionalInterface
public interface MessageExecutorGetter {
    MessageExecutor getMessageExecutor(String chatId);
}
