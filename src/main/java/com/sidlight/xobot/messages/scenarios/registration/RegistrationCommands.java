package com.sidlight.xobot.messages.scenarios.registration;

import com.sidlight.xobot.core.*;
import com.sidlight.xobot.core.statemachine.StateMachine;
import com.sidlight.xobot.core.statemachine.StateMachineException;
import com.sidlight.xobot.core.statemachine.enums.BasicEvent;

@BotCommands(description = "Регистрация")
public class RegistrationCommands {

    @Command(name = "/start", description = "Старт бота", roles = {Role.ALL}, messengers = Messenger.ALL, stateRegister = StateRegister.NON)
    public static void start(Message message) throws StateMachineException {
        StateMachine.executeEvent(message, BasicEvent.START);
    }

    @Command(name = "/confirmName", roles = {Role.ALL}, messengers = Messenger.ALL, description = "Подтвердить имя", stateRegister = StateRegister.NON)
    public static void confirmName(Message message) throws StateMachineException {
        StateMachine.executeEvent(message, RegistrationEvent.CONFIRM_NAME);
    }

}
