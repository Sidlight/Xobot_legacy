package com.sidlight.xobot.messages.scenarios.developer;

import com.sidlight.xobot.core.*;
import com.sidlight.xobot.db.managers.UserManager;

@BotCommands(description = "")
public class DeveloperCommand {

    @Command(name = "/getStateStatus", roles = {Role.DEVELOPER},messengers = Messenger.ALL)
    public void getStateStatus (Message message){

    }
}
