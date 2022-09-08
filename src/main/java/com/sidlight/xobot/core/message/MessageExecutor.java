package com.sidlight.xobot.core.message;

import com.sidlight.xobot.messages.controllers.BotController;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface MessageExecutor {

    BotController getController();

    Messenger getMessenger();

    void sendText(String text, List<Map<String, String>> keyboard);

    void sendFile(File file, TypeFile typeFile, String description);

    void sendFiles(Map<File, TypeFile> files, String description);

    void sendGPSCoordinate(double latitude, double longitude);
}
