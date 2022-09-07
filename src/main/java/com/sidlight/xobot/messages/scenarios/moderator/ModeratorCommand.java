package com.sidlight.xobot.messages.scenarios.moderator;

import com.sidlight.xobot.core.BotCommands;
import com.sidlight.xobot.core.Command;
import com.sidlight.xobot.core.Messenger;
import com.sidlight.xobot.core.Role;

@BotCommands(description = "Команды модератора")
public class ModeratorCommand {

    @Command(name = "/get_confirmed_user", roles = {Role.ADMIN, Role.MODERATOR, Role.DEVELOPER}, messengers = {Messenger.ALL})
    public static void getConfirmedUser() {

    }
}
