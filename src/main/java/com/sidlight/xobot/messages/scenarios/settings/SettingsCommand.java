package com.sidlight.xobot.messages.scenarios.settings;

import com.sidlight.xobot.core.command.BotCommands;
import com.sidlight.xobot.core.command.Command;
import com.sidlight.xobot.core.command.Keyboard;
import com.sidlight.xobot.core.command.KeyboardBuilder;
import com.sidlight.xobot.core.message.Message;
import com.sidlight.xobot.core.message.Messenger;
import com.sidlight.xobot.core.statemachine.StateMachine;
import com.sidlight.xobot.core.statemachine.StateMachineException;
import com.sidlight.xobot.core.statemachine.enums.BasicEvent;
import com.sidlight.xobot.db.managers.UserManager;
import com.sidlight.xobot.messages.scenarios.navigation.NavigationCommand;

@BotCommands
public class SettingsCommand {

    @Command(command = Commands.SETTINGS_MENU, messengers = {Messenger.ALL})
    @Keyboard(tag = NavigationCommand.Keyboards.NAVIGATION, text = Text.SETTINGS)
    public static void sendSettingsMenu(Message message) {
        message.sendRequest(Text.SETTINGS, KeyboardBuilder.buildKeyboard(Keyboards.SETTINGS, message));
    }

    @Command(command = Commands.SETTING_NEW_NAME, messengers = {Messenger.ALL})
    @Keyboard(tag = Keyboards.SETTINGS, text = Text.CHANGE_NAME)
    public static void settingNewName(Message message) throws StateMachineException {
        StateMachine.executeEvent(message, SettingsAction.Events.START_SET_NEW_NAME);
    }

    @Command(command = Commands.CONFIRM_NEW_NAME, messengers = {Messenger.ALL})
    @Keyboard(tag = Keyboards.NEW_NAME, text = Text.CONFIRM)
    public static void confirmNewName(Message message) throws StateMachineException {
        Object obj = StateMachine.getObjectFromStage(message.getUserIdentifier());
        UserManager.get().setNameFromUser(message.getUserIdentifier(), obj.toString());
        message.sendRequest(Text.SAVED_NAME);
        StateMachine.executeEvent(message, BasicEvent.END);
    }

    @Command(command = Commands.CANCEL_NEW_NAME, messengers = {Messenger.ALL})
    @Keyboard(tag = Keyboards.NEW_NAME, text = Text.CANCEL)
    public static void cancelNewName(Message message) throws StateMachineException {
        message.sendRequest(Text.CANCELED);
        StateMachine.executeEvent(message, BasicEvent.END);
    }

    public class Text {
        public static final String SETTINGS = "Настройки";
        public static final String CANCEL = "Отмена";
        public static final String CANCELED = "Отменено";
        public static final String CONFIRM = "Подтвердить";
        public static final String WRITE_NEW_NAME = "Введите новое имя";
        public static final String CHANGE_NAME = "Изменить имя";
        public static final String SAVED_NAME = "Имя изменено";
    }

    public class Keyboards {
        public static final String SETTINGS = "settings_keyboard";
        public static final String NEW_NAME = "NEW_NAME_SETTING";
    }

    public class Commands {
        public static final String SETTINGS_MENU = "/settings_menu";
        public static final String SETTING_NEW_NAME = "/setting_new_name";
        public static final String CONFIRM_NEW_NAME = "/confirm_new_name";
        public static final String CANCEL_NEW_NAME = "/cancel_new_name";
    }

}
