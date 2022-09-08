package com.sidlight.xobot.core.access;

import com.sidlight.xobot.core.command.Command;
import com.sidlight.xobot.core.message.Message;
import com.sidlight.xobot.core.message.Messenger;
import com.sidlight.xobot.db.BotDataException;
import com.sidlight.xobot.db.domainobject.User;
import com.sidlight.xobot.db.managers.UserManager;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;

@Component
@EnableAutoConfiguration
public class AccessChecker {

    @Deprecated
    public static boolean checkAccess(Role[] roles, Messenger[] messengers, Role userRole) {
        boolean flagUnCheckedMessenger = true;
        for (Messenger messenger : messengers) {
            flagUnCheckedMessenger = false;
        }
        if (flagUnCheckedMessenger) {
            return false;
        }
        for (Role role : roles) {
            if (role == Role.ALL || role == userRole) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkAccess(Command command, Message message) {
        User user;
        try {
            user = UserManager.get().getUserFromUserIdentifier(message.getUserIdentifier());
        } catch (BotDataException e) {
            user = new User();
            user.setRole(Role.ALL);
            user.setMessenger(Messenger.ALL);
        }
        boolean roleFlag = false;
        boolean messengerFlag = false;
        for (Role role : command.roles()) {
            if (role == user.getRole() || role == Role.ALL) {
                roleFlag = true;
                break;
            }
        }
        for (Messenger messenger : command.messengers()) {
            if (messenger == user.getMessenger() || messenger == Messenger.ALL) {
                messengerFlag = true;
                break;
            }
        }
        return roleFlag && messengerFlag;
    }

}
