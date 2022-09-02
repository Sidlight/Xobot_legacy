package com.sidlight.xobot.core;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;

@Component
@EnableAutoConfiguration
public class AccessChecker {

    public static boolean checkAccess(Role[] roles, Messenger[] messengers, Role userRole) {
        boolean flagUnCheckedMessenger = true;
        for (Messenger messenger : messengers) {
            flagUnCheckedMessenger = false;
        }
        if(flagUnCheckedMessenger){
            return false;
        }
        for (Role role : roles) {
            if (role == Role.ALL || role == userRole) {
                return true;
            }
        }
        return false;
    }
}
