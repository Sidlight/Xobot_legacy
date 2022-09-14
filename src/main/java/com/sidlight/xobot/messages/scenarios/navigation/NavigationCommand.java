package com.sidlight.xobot.messages.scenarios.navigation;

import com.sidlight.xobot.core.command.BotCommands;
import com.sidlight.xobot.core.command.Command;
import com.sidlight.xobot.core.command.KeyboardBuilder;
import com.sidlight.xobot.core.message.Message;

@BotCommands
public class NavigationCommand {

    @Command(command = "/nav_menu")
    public static void navigationMenu(Message message) {
        message.getUserIdentifier().getMessageExecutor()
                .sendText("Навигационное меню", KeyboardBuilder.buildKeyBoard(Keyboards.NAVIGATION, message));
    }


    public class Keyboards {
        public static final String NAVIGATION = "navigation";
    }


}
