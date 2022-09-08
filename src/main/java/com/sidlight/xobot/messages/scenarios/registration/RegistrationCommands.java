package com.sidlight.xobot.messages.scenarios.registration;

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
import com.sidlight.xobot.messages.scenarios.KeyboardTag;
import com.sidlight.xobot.messages.scenarios.registration.resource.RegCom;
import com.sidlight.xobot.messages.scenarios.registration.resource.RegistrationEvent;
import com.sidlight.xobot.messages.scenarios.registration.resource.RegistrationMessages;

@BotCommands(description = "Регистрация")
public class RegistrationCommands {

    @Command(command = "/start",
            description = "Старт бота",
            roles = {Role.ALL},
            messengers = Messenger.ALL,
            stateRegister = StateRegister.NON)
    public static void start(Message message) throws StateMachineException {
        message.getUserIdentifier().getMessageExecutor().sendText(RegistrationMessages.HELLO_MESSAGE, null);
        StateMachine.executeEvent(message, BasicEvent.START);
    }

    @Command(command = RegCom.CONFIRM_NAME,
            roles = {Role.ALL},
            messengers = Messenger.ALL,
            stateRegister = StateRegister.NON)
    @Keyboard(tag = KeyboardTag.CONFIRM_NAME_TAG, text = "Подтвердить имя", row = 0, column = 0)
    public static void confirmName(Message message) throws StateMachineException {
        StateMachine.executeEvent(message, RegistrationEvent.CONFIRM_NAME);
    }

    @Command(command = RegCom.CANCEL_NAME,
            roles = {Role.ALL},
            messengers = Messenger.ALL,
            stateRegister = StateRegister.NON)
    @Keyboard(tag = KeyboardTag.CONFIRM_NAME_TAG, text = "Ввести заново", row = 0, column = 1)
    public static void cancel(Message message) throws StateMachineException {
        StateMachine.executeEvent(message, RegistrationEvent.CANCEL_NAME);
    }

}
