package com.sidlight.xobot.core.message;

import com.sidlight.xobot.core.message.MessageExecutor;
import com.sidlight.xobot.core.message.Messenger;

public record UserIdentifier(String chatId, Messenger messenger){

    public MessageExecutor getMessageExecutor(){
        return messenger.getMessageExecutor(chatId);
    }
}
