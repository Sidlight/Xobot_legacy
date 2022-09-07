package com.sidlight.xobot.core;

import lombok.Getter;

public class UserActionException extends Exception {

    @Getter
    private final String message;

    public UserActionException(String message) {
        this.message = message;
    }

}
