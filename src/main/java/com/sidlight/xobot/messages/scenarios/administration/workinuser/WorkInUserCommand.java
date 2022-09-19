package com.sidlight.xobot.messages.scenarios.administration.workinuser;

import com.sidlight.xobot.core.access.Role;
import com.sidlight.xobot.core.access.StateRegister;
import com.sidlight.xobot.core.command.BotCommands;
import com.sidlight.xobot.core.command.Command;
import com.sidlight.xobot.core.command.Keyboard;
import com.sidlight.xobot.core.command.KeyboardBuilder;
import com.sidlight.xobot.core.message.Message;
import com.sidlight.xobot.core.message.Messenger;
import com.sidlight.xobot.core.statemachine.StateMachine;
import com.sidlight.xobot.core.statemachine.StateMachineException;
import com.sidlight.xobot.db.domainobject.User;
import com.sidlight.xobot.db.managers.UserManager;
import com.sidlight.xobot.messages.scenarios.administration.AdministrationCommands;

import java.util.List;

@BotCommands
public class WorkInUserCommand {

    @Command(command = Commands.USER_WORK_MENU, roles = {Role.DEVELOPER, Role.ADMIN, Role.MODERATOR})
    @Keyboard(tag = AdministrationCommands.Keyboards.ADMIN_MENU, text = Text.USER_WORK)
    public static void sendUserWorkMenu(Message message) {
        message.sendRequest(Text.USER_WORK, KeyboardBuilder.buildKeyboard(Keyboards.WORK_IN_USER, message));
    }


    @Command(command = Commands.GET_ADMIN_LIST, roles = {Role.ADMIN, Role.DEVELOPER}, messengers = {Messenger.ALL})
    @Keyboard(tag = Keyboards.WORK_IN_USER, text = Text.ADMIN_LIST)
    public static void sendAdminList(Message message) {
        List<User> users = UserManager.get().getAllUserFrom(Role.ADMIN);
        if (users == null || users.isEmpty()) {
            message.sendRequest(Text.EMPTY_LIST);
            return;
        }
        StringBuilder admins = new StringBuilder().append(Text.ADMIN_LIST).append(":\n");
        for (User user : users) admins.append(user.toString()).append("\n");
        message.sendRequest(admins.toString());
    }

    @Command(command = Commands.GET_MODERATOR_LIST, roles = {Role.ADMIN, Role.DEVELOPER}, messengers = {Messenger.ALL})
    @Keyboard(tag = Keyboards.WORK_IN_USER, text = Text.MODERATOR_LIST)
    public static void sendModeratorList(Message message) {
        List<User> users = UserManager.get().getAllUserFrom(Role.MODERATOR);
        if (users == null || users.isEmpty()) {
            message.sendRequest(Text.EMPTY_LIST);
            return;
        }
        StringBuilder moderators = new StringBuilder().append(Text.MODERATOR_LIST).append(":\n");
        for (User user : users) moderators.append(user.toString()).append("\n");
        message.sendRequest(moderators.toString());
    }

    @Command(command = Commands.GET_CANCELED_USER_LIST, roles = {Role.ADMIN, Role.DEVELOPER, Role.MODERATOR}, messengers = {Messenger.ALL})
    @Keyboard(tag = Keyboards.WORK_IN_USER, text = Text.CANCELED_USER_LIST)
    public static void sendCanceledUserList(Message message) {
        List<User> users = UserManager.get().getAllUserFrom(StateRegister.CANCELLED);
        if (users == null || users.isEmpty()) {
            message.sendRequest(Text.EMPTY_LIST);
            return;
        }
        StringBuilder canceledUsers = new StringBuilder().append(Text.CANCELED_USER_LIST).append(":\n");
        for (User user : users) canceledUsers.append(user.toString()).append("\n");
        message.sendRequest(canceledUsers.toString());
    }

    @Command(command = Commands.GET_REGISTER_USER_LIST, roles = {Role.ADMIN, Role.DEVELOPER, Role.MODERATOR}, messengers = {Messenger.ALL})
    @Keyboard(tag = Keyboards.WORK_IN_USER, text = Text.REGISTER_USER_LIST)
    public static void sendRegisterUserList(Message message) {
        List<User> users = UserManager.get().getAllUserFrom(StateRegister.CONFIRMED);
        if (users == null || users.isEmpty()) {
            message.sendRequest(Text.EMPTY_LIST);
            return;
        }
        StringBuilder regsUser = new StringBuilder().append(Text.REGISTER_USER_LIST).append(":\n");
        for (User user : users) regsUser.append(user.toString()).append("\n");
        message.sendRequest(regsUser.toString());
    }


    @Command(command = Commands.GET_WAITING_CONFIRM_USER_LIST, roles = {Role.ADMIN, Role.DEVELOPER, Role.MODERATOR}, messengers = {Messenger.ALL})
    @Keyboard(tag = Keyboards.WORK_IN_USER, text = Text.WAITING_CONFIRM_USER_LIST)
    public static void sendWaitingUser(Message message) {
        List<User> users = UserManager.get().getAllUserFrom(StateRegister.PENDING_CONFIRMATION);
        if (users == null || users.isEmpty()) {
            message.sendRequest(Text.EMPTY_LIST);
            return;
        }
        StringBuilder waitUsers = new StringBuilder().append(Text.WAITING_CONFIRM_USER_LIST).append(":\n");
        for (User user : users) waitUsers.append(user.toString()).append("\n");
        message.sendRequest(waitUsers.toString());
    }

    @Command(command = Commands.START_CONFIRMING_FROM_ID, roles = {Role.ADMIN, Role.USER, Role.DEVELOPER})
    @Keyboard(tag = Keyboards.WORK_IN_USER, text = Text.START_CONFIRM_USER_FROM_ID)
    public static void startConfirmUser(Message message) throws StateMachineException {
        StateMachine.executeEvent(message, WorkInUserAction.Events.START_CONFIRM_USER_FROM_ID);
    }

    public class Text {
        public static final String REGISTER_USER_LIST = "Зарегистрированные пользователи";
        public static final String CANCELED_USER_LIST = "Отклоненные пользователи";
        public static final String MODERATOR_LIST = "Модераторы";
        public static final String ADMIN_LIST = "Админы";
        public static final String EMPTY_LIST = "Пользователи не найдены";
        public static final String WAITING_CONFIRM_USER_LIST = "В ожидании подтверждения";
        public static final String USER_WORK = "Пользователи";
        public static final String START_CONFIRM_USER_FROM_ID = "Подтвердить пользователя по id";
    }

    public class Commands {
        //Получить список зарегистрированных пользователей
        public static final String GET_REGISTER_USER_LIST = "/get_register_user_list";
        // Получить список отклоненных пользователей
        public static final String GET_CANCELED_USER_LIST = "/get_canceled_user_list";
        // Получить список модераторов
        public static final String GET_MODERATOR_LIST = "/get_moderator_list";
        // Получить список администраторов
        public static final String GET_ADMIN_LIST = "/get_admin_list";
        // Получить список ожидающих подтверждения
        public static final String GET_WAITING_CONFIRM_USER_LIST = "/get_waiting_confirm_user_list";
        //Меню работы с пользователями
        public static final String USER_WORK_MENU = "/user_work_menu";
        //Повреждение регистрации по id
        public static final String CONFIRM_FROM_ID = "/confirm_from_id";
        public static final String START_CONFIRMING_FROM_ID = "/start_confirming_from_id";

        //Установить модератора по id
        public static final String START_SET_MODERATOR_FROM_ID = "/start_moderator_set_from_id";
        public static final String SET_MODERATOR_FROM_ID = "/set_moderator_from_id";

        //Установить Админа по id
        public static final String SETTING_ADMIN_FROM_ID = "/start_admin_set_from_id";
        public static final String SET_ADMIN_FROM_ID = "/set_admin_from_id";
    }

    public class Keyboards {
        public static final String WORK_IN_USER = "work_in_user";
        public static final String CONFIRM_FROM_ID = "confirm_from_id";
    }
}
