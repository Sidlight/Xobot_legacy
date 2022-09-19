package com.sidlight.xobot.messages.scenarios.user.registration;

import com.sidlight.xobot.core.access.Role;
import com.sidlight.xobot.core.access.StateRegister;
import com.sidlight.xobot.core.command.BotCommands;
import com.sidlight.xobot.core.command.Command;
import com.sidlight.xobot.core.command.Keyboard;
import com.sidlight.xobot.core.message.Message;
import com.sidlight.xobot.core.message.Messenger;
import com.sidlight.xobot.core.statemachine.StateMachine;
import com.sidlight.xobot.core.statemachine.StateMachineException;
import com.sidlight.xobot.core.statemachine.enums.BasicEvent;

@BotCommands(description = "Регистрация")
public class RegistrationCommands {

    @Command(command = "/start",
            description = "Старт бота",
            roles = {Role.ALL},
            messengers = Messenger.ALL,
            stateRegister = StateRegister.NON)
    public static void start(Message message) throws StateMachineException {
        message.getUserIdentifier().getMessageExecutor().sendText(Text.HELLO_MESSAGE, null);
        StateMachine.executeEvent(message, BasicEvent.START);
    }

    @Command(command = Commands.CONFIRM_NAME,
            roles = {Role.ALL},
            messengers = Messenger.ALL,
            stateRegister = StateRegister.NON)
    @Keyboard(tag = Keyboards.CONFIRM_NAME_TAG, text = "Подтвердить имя", row = 0, column = 0)
    public static void confirmName(Message message) throws StateMachineException {
        StateMachine.executeEvent(message, RegistrationAction.Events.CONFIRM_NAME);
    }

    @Command(command = Commands.CANCEL_NAME,
            roles = {Role.ALL},
            messengers = Messenger.ALL,
            stateRegister = StateRegister.NON)
    @Keyboard(tag = Keyboards.CONFIRM_NAME_TAG, text = "Ввести заново", row = 0, column = 1)
    public static void cancel(Message message) throws StateMachineException {
        StateMachine.executeEvent(message, RegistrationAction.Events.CANCEL_NAME);
    }


    public class Text {
        public static final String WAIT_FOR_CONFIRMATION = "Ожидайте подтверждения";
        public static final String NO_NAME_ENTERED = "Не введено имя пользователя";
        public static final String START_MESSAGES = "Стартовое сообщение";
        public static final String WRITE_NAME_MESSAGE = "Введите имя";
        public static final String HELLO_MESSAGE = "Приветствие";
        public static final String START_ADMIN_MESSAGES = "Стартовое сообщение для админа";
        public static final String START_DEVELOPER_MESSAGES = "Стартовое сообщение для разработчика";
        public static final String CONFIRM_NAME_MESSAGES = "Подтвердите имя";
    }

    public class Keyboards {
        public static final String CONFIRM_NAME_TAG = "confirmName";
    }

    public class Commands {
        public static final String CONFIRM_NAME = "/confirmName";
        public static final String CANCEL_NAME = "/cancelName";
    }


}
