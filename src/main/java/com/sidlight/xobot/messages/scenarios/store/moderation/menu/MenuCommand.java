package com.sidlight.xobot.messages.scenarios.store.moderation.menu;

import com.sidlight.xobot.core.access.Role;
import com.sidlight.xobot.core.command.BotCommands;
import com.sidlight.xobot.core.command.Command;
import com.sidlight.xobot.core.command.Keyboard;
import com.sidlight.xobot.core.command.KeyboardBuilder;
import com.sidlight.xobot.core.message.Message;
import com.sidlight.xobot.messages.scenarios.administration.AdministrationCommands;

@BotCommands
public class MenuCommand {

    @Command(command = Commands.MENU_MENU, roles = {Role.ADMIN, Role.DEVELOPER, Role.MODERATOR})
    @Keyboard(tag = AdministrationCommands.Keyboards.ADMIN_MENU, text = Text.MENU)
    public static void getMenuMenu(Message message) {
        message.sendRequest(Text.MENU, KeyboardBuilder.buildKeyboard(Keyboards.EDIT_MENU_MENU, message));
    }

    @Command(command = Commands.ADDING_MENU_ITEM, roles = {Role.ADMIN, Role.DEVELOPER, Role.MODERATOR})
    @Keyboard(tag = Keyboards.EDIT_MENU_MENU, text = Text.ADDING_MENU)
    public static void addingMenuItem(Message message) {
        message.sendRequest(Text.ADDING_MENU, KeyboardBuilder.buildKeyboard(Keyboards.ADDING_MENU_ITEM_MENU, message));
    }


    public class Commands {
        public static final String MENU_MENU = "/menu_menu";
        public static final String ADDING_MENU_ITEM = "/adding_menu_item";
    }

    public class Text {
        public static final String MENU = "Меню";
        public static final String ADDING_MENU = "Добавление позиций в меню";
    }

    public class Keyboards {
        public static final String EDIT_MENU_MENU = "edit_menu_menu";
        public static final String ADDING_MENU_ITEM_MENU = "adding_menu_item_menu";
    }

}
