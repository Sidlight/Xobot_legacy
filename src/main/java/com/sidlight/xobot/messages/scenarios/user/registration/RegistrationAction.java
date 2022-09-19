package com.sidlight.xobot.messages.scenarios.user.registration;

import com.sidlight.xobot.core.Core;
import com.sidlight.xobot.core.access.Role;
import com.sidlight.xobot.core.command.KeyboardBuilder;
import com.sidlight.xobot.core.command.UserActionException;
import com.sidlight.xobot.core.message.Message;
import com.sidlight.xobot.core.statemachine.StateMachine;
import com.sidlight.xobot.core.statemachine.StateMachineException;
import com.sidlight.xobot.core.statemachine.annotations.ActionClass;
import com.sidlight.xobot.core.statemachine.annotations.EventAction;
import com.sidlight.xobot.core.statemachine.annotations.StateAction;
import com.sidlight.xobot.core.statemachine.enums.BasicEvent;
import com.sidlight.xobot.db.managers.UserManager;
import org.springframework.beans.factory.annotation.Value;

@ActionClass(description = "")
public class RegistrationAction {

    @Value("bot.open")
    private static boolean botOpen;

    @EventAction(targetState = States.WAITING_NAME_STATE, returnToOriginalState = 0L, event = BasicEvent.START)
    public static void registration(Message message) {
        message.getUserIdentifier().getMessageExecutor().sendText(RegistrationCommands.Text.WRITE_NAME_MESSAGE, null);
    }

    @EventAction(targetState = States.WAITING_NAME_STATE, sourceState = States.WAITING_CONFIRM_NAME_STATE, returnToOriginalState = 0L, event = Events.CANCEL_NAME)
    public static void cancelName(Message message) {
        message.getUserIdentifier().getMessageExecutor().sendText(RegistrationCommands.Text.START_MESSAGES, null);
    }

    @EventAction(targetState = States.WAITING_CONFIRM_NAME_STATE, returnToOriginalState = 0L, event = Events.SET_NAME)
    public static void setStateWaitingName(Message message) {
        message.getUserIdentifier().getMessageExecutor().sendText(RegistrationCommands.Text.CONFIRM_NAME_MESSAGES, KeyboardBuilder.buildKeyboard(RegistrationCommands.Keyboards.CONFIRM_NAME_TAG, message));
    }

    @EventAction(sourceState = States.WAITING_CONFIRM_NAME_STATE, returnToOriginalState = 0L, event = Events.CONFIRM_NAME)
    public static void confirmName(Message message) throws StateMachineException, UserActionException {
        String name = StateMachine.getObjectFromStage(message.getUserIdentifier()).toString();
        if (name == null) throw new UserActionException(RegistrationCommands.Text.NO_NAME_ENTERED);
        String developer = message.getUserIdentifier().getMessageExecutor().getController().getDevelopUserName();
        String admin = message.getUserIdentifier().getMessageExecutor().getController().getDevelopUserName();
        Role role;
        if (developer != null && developer.equals(message.getUserName())) {
            role = Role.DEVELOPER;
        } else if (admin != null && admin.equals(message.getUserName())) {
            role = Role.ADMIN;
        } else {
            role = Role.USER;
        }
        UserManager.get().register(
                message.getUserIdentifier(),
                message.getUserName(),
                role,
                name);
        switch (role) {
            case ADMIN -> message.getUserIdentifier().getMessageExecutor().sendText(RegistrationCommands.Text.START_ADMIN_MESSAGES, null);
            case DEVELOPER -> message.getUserIdentifier().getMessageExecutor().sendText(RegistrationCommands.Text.START_DEVELOPER_MESSAGES, null);
            case USER -> message.getUserIdentifier().getMessageExecutor().sendText(RegistrationCommands.Text.WAIT_FOR_CONFIRMATION, null);
        }
    }

    @StateAction(state = States.WAITING_NAME_STATE)
    public static void setName(Message message) throws StateMachineException {
        StateMachine.setObjectFromStage(message.getUserIdentifier(), message.getText());
        StateMachine.executeEvent(message, Events.SET_NAME);
    }

    @StateAction(state = States.WAITING_CONFIRM_NAME_STATE)
    public static void confirmNameStateAction(Message message) throws StateMachineException {
        Core.runCommand(message);
    }

    public class States {
        public static final String WAITING_NAME_STATE = "waiting_name";
        public static final String WAITING_CONFIRM_NAME_STATE = "waiting_confirm";
    }

    public class Events {
        public static final String CONFIRM_NAME = "confirm_name";
        public static final String SETTING_NAME = "setting_name";
        public static final String SET_NAME = "set_name";
        public static final String CANCEL_NAME = "cancel_name";
    }

}
