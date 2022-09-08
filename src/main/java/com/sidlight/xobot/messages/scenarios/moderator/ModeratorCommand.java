package com.sidlight.xobot.messages.scenarios.moderator;

import com.sidlight.xobot.core.access.Role;
import com.sidlight.xobot.core.access.StateRegister;
import com.sidlight.xobot.core.command.BotCommands;
import com.sidlight.xobot.core.command.Command;
import com.sidlight.xobot.core.command.Keyboard;
import com.sidlight.xobot.core.command.KeyboardBuilder;
import com.sidlight.xobot.core.message.Message;
import com.sidlight.xobot.core.message.Messenger;
import com.sidlight.xobot.db.domainobject.User;
import com.sidlight.xobot.db.managers.UserManager;
import com.sidlight.xobot.messages.scenarios.KeyboardTag;

@BotCommands(description = "Команды модератора")
public class ModeratorCommand {

    @Command(command = "/get_navigation_menu_moderator", roles = {Role.MODERATOR, Role.ADMIN, Role.DEVELOPER}, messengers = {Messenger.ALL})
    @Keyboard(tag = KeyboardTag.NAVIGATION_MENU, text = "Навигационное меню модератора", row = 10, column = 0)
    public static void getNavigationMenuModerator(Message message) {
        message.getUserIdentifier().getMessageExecutor()
                .sendText("Навигационное меню модератора", KeyboardBuilder.buildKeyBoard(KeyboardTag.MODERATOR_KEYBOARD, message));
    }

    @Command(command = "/get_confirmed_user", roles = {Role.ADMIN, Role.MODERATOR, Role.DEVELOPER}, messengers = {Messenger.ALL})
    @Keyboard(tag = KeyboardTag.MODERATOR_KEYBOARD, text = "Подтвержденные пользователи", row = 0, column = 0)
    public static void getConfirmedUser(Message message) {
        StringBuilder stringBuilder = new StringBuilder();
        for (User user : UserManager.get().getAllUserFrom(StateRegister.CONFIRMED)) {
            stringBuilder.append(user.toString() + "\n");
        }
        message.getUserIdentifier().getMessageExecutor().sendText(stringBuilder.toString(), null);
    }

    @Command(command = "/get_ask_registr", roles = {Role.ADMIN, Role.MODERATOR, Role.DEVELOPER}, messengers = {Messenger.ALL})
    @Keyboard(tag = KeyboardTag.MODERATOR_KEYBOARD, text = "Просящие", row = 0, column = 1)
    public static void getAskingForRegistration(Message message) {
        StringBuilder stringBuilder = new StringBuilder();
        for (User user : UserManager.get().getAllUserFrom(StateRegister.PENDING_CONFIRMATION)) {
            stringBuilder.append(user.toString() + "\n");
        }
        message.getUserIdentifier().getMessageExecutor().sendText(stringBuilder.toString(), null);
    }
}
