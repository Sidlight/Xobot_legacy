package com.sidlight.xobot.core;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public interface MessageExecutor {

    Messenger getMessenger();

    void sendText(String text, ArrayList<Map<String, String>> keyboard);

    void sendFile(File file, TypeFile typeFile, String description);

    void sendFiles(Map<File, TypeFile> files, String description);

    void sendGPSCoordinate(double latitude, double longitude);
}
