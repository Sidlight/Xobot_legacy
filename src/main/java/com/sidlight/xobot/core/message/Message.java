package com.sidlight.xobot.core.message;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.List;
import java.util.Map;

public class Message {

    @Getter
    @Setter
    private String text;

    @Setter
    @Getter
    private UserIdentifier userIdentifier;

    @Getter
    @Setter
    private List<File> files;

    @Getter
    @Setter
    private String userName;

    public String getCommand() {
        if (text == null) return null;
        String command = text.split(" ")[0];
        if (command == null || command.length() == 0 || command.charAt(0) != '/') {
            return null;
        }
        return command;
    }

    public void sendRequest(String text) {
        userIdentifier.getMessageExecutor().sendText(text, null);
    }

    public void sendRequest(String text, List<Map<String, String>> keyboard) {
        userIdentifier.getMessageExecutor().sendText(text, keyboard);
    }

}
