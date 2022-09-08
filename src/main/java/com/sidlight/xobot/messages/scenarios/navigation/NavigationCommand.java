package com.sidlight.xobot.messages.scenarios.navigation;

import com.sidlight.xobot.core.command.BotCommands;
import com.sidlight.xobot.core.command.Command;
import com.sidlight.xobot.core.command.KeyboardBuilder;
import com.sidlight.xobot.core.message.Message;
import com.sidlight.xobot.messages.scenarios.KeyboardTag;

@BotCommands
public class NavigationCommand {

    @Command(command = "/nav_menu")
    public static void navigationMenu(Message message) {
        message.getUserIdentifier().getMessageExecutor()
                .sendText("Навигационное меню", KeyboardBuilder.buildKeyBoard(KeyboardTag.NAVIGATION_MENU, message));
    }

}
