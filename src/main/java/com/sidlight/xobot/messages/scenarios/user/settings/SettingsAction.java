package com.sidlight.xobot.messages.scenarios.user.settings;

import com.sidlight.xobot.core.BotException;
import com.sidlight.xobot.core.Core;
import com.sidlight.xobot.core.command.KeyboardBuilder;
import com.sidlight.xobot.core.message.Message;
import com.sidlight.xobot.core.statemachine.StateMachine;
import com.sidlight.xobot.core.statemachine.StateMachineException;
import com.sidlight.xobot.core.statemachine.annotations.ActionClass;
import com.sidlight.xobot.core.statemachine.annotations.EventAction;
import com.sidlight.xobot.core.statemachine.annotations.StateAction;

@ActionClass
public class SettingsAction {

    @EventAction(targetState = States.WAITING_SET_NEW_NAME, event = Events.START_SET_NEW_NAME)
    public static void waitSetNewNameStateAction(Message message) throws StateMachineException {
        message.sendRequest(Text.WRITE_NAME);
    }

    @StateAction(state = States.WAITING_SET_NEW_NAME)
    public static void waitNameState(Message message) throws StateMachineException {
        StateMachine.setObjectFromStage(message.getUserIdentifier(), message.getText());
        StateMachine.executeEvent(message, Events.SET_NEW_NAME);
    }

    @EventAction(sourceState = States.WAITING_SET_NEW_NAME, targetState = States.WAITING_CONFIRM_NEW_NAME, event = Events.SET_NEW_NAME)
    public static void setNameEvent(Message message) throws StateMachineException {
        Object obj = StateMachine.getObjectWithoutDelete(message.getUserIdentifier());
        if (!(obj instanceof String)) {
            throw new BotException("Unknown exception");
        }
        message.sendRequest(Text.CONFIRM_NAME + ": " + obj.toString(), KeyboardBuilder.buildKeyboard(SettingsCommand.Keyboards.NEW_NAME,message));
    }

    @StateAction(state = States.WAITING_CONFIRM_NEW_NAME)
    public static void waitingConfirmName(Message message) throws StateMachineException {
        String command = message.getCommand();
        if (command == null || !(command.equals(SettingsCommand.Commands.CANCEL_NEW_NAME) || command.equals(SettingsCommand.Commands.CONFIRM_NEW_NAME))) {
            throw new BotException("HZ");
        }
        Core.runCommand(message);
    }

    public class Text {
        public static final String WRITE_NAME = "Введите имя";
        public static final String CONFIRM_NAME = "Подтвердите имя";
    }

    public class Events {
        public static final String START_SET_NEW_NAME = "start_set_new_name";
        public static final String SET_NEW_NAME = "set_new_name";
    }

    public class States {
        public static final String WAITING_SET_NEW_NAME = "waiting_set_new_name";
        public static final String WAITING_CONFIRM_NEW_NAME = "waiting_confirm_new_name";
    }
}
