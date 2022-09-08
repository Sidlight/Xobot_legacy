package com.sidlight.xobot.messages.scenarios.developer;

import com.sidlight.xobot.core.access.Role;
import com.sidlight.xobot.core.command.BotCommands;
import com.sidlight.xobot.core.command.Command;
import com.sidlight.xobot.core.message.Message;
import com.sidlight.xobot.core.message.Messenger;

@BotCommands(description = "")
public class DeveloperCommand {

    @Command(command = "/getStateStatus", roles = {Role.DEVELOPER},messengers = Messenger.ALL)
    public void getStateStatus (Message message){

    }
}
