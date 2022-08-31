package com.sidlight.xobot.messages.command;

import com.sidlight.xobot.core.*;
import com.sidlight.xobot.core.statemachine.StateMachine;
import com.sidlight.xobot.core.statemachine.StateMachineException;
import com.sidlight.xobot.core.statemachine.enums.BasicEvent;

@BotCommands(description = "Сервисные команды")
public class ServiceCommand {

    @Command(name = "/start", description = "Старт бота", roles = {Role.ALL}, messengers = Messenger.ALL, stateRegister = StateRegister.NON)
    public static void start(Message message) throws StateMachineException {
        StateMachine.executeEvent(message, BasicEvent.START_EVENT);
    }

    @Command(name = "/confirmName", roles = {Role.ALL}, messengers = Messenger.ALL, description = "Подтвердить имя", stateRegister = StateRegister.NON)
    public static void confirmName(Message message) throws StateMachineException {
        StateMachine.executeEvent(message, BasicEvent.CONFIRM_NAME);
    }
}
