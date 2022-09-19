package com.sidlight.xobot.messages.scenarios.administration;

import com.sidlight.xobot.core.access.Role;
import com.sidlight.xobot.core.command.BotCommands;
import com.sidlight.xobot.core.command.Command;
import com.sidlight.xobot.core.command.Keyboard;
import com.sidlight.xobot.core.command.KeyboardBuilder;
import com.sidlight.xobot.core.message.Message;
import com.sidlight.xobot.core.message.Messenger;
import com.sidlight.xobot.messages.scenarios.usability.navigation.NavigationCommand;

@BotCommands
public class AdministrationCommands {

    @Keyboard(tag = NavigationCommand.Keyboards.NAVIGATION, text = Text.ADMIN_MENU)
    @Command(command = Commands.ADMIN_MENU, roles = {Role.ADMIN, Role.USER, Role.DEVELOPER}, messengers = {Messenger.ALL})
    public static void sendAdministrationsMenu(Message message) {
        message.sendRequest(Text.ADMIN_MENU, KeyboardBuilder.buildKeyboard(Keyboards.ADMIN_MENU, message));
    }

    public class Text {
        public static final String ADMIN_MENU = "Администрирование";
    }

    public class Commands {
        public static final String ADMIN_MENU = "/admin_menu";
    }

    public class Keyboards {
        public static final String ADMIN_MENU = "admin_menu_keyboard";
    }
}
