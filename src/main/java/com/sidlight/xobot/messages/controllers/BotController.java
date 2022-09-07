package com.sidlight.xobot.messages.controllers;

public interface BotController {

    void init() throws Exception;

    String getDevelopUserName();

    String getAdminUserName();
}
