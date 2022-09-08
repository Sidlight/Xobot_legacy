package com.sidlight.xobot.messages.scenarios.registration;

import com.sidlight.xobot.core.access.Role;
import com.sidlight.xobot.core.command.KeyboardBuilder;
import com.sidlight.xobot.core.command.UserActionException;
import com.sidlight.xobot.core.message.Message;
import com.sidlight.xobot.core.statemachine.StateMachine;
import com.sidlight.xobot.core.statemachine.StateMachineException;
import com.sidlight.xobot.core.statemachine.annotations.ActionClass;
import com.sidlight.xobot.core.statemachine.annotations.EventAction;
import com.sidlight.xobot.core.statemachine.enums.BasicEvent;
import com.sidlight.xobot.db.managers.UserManager;
import com.sidlight.xobot.messages.scenarios.KeyboardTag;
import com.sidlight.xobot.messages.scenarios.registration.resource.RegistrationEvent;
import com.sidlight.xobot.messages.scenarios.registration.resource.RegistrationMessages;
import com.sidlight.xobot.messages.scenarios.registration.resource.RegistrationState;
import org.springframework.beans.factory.annotation.Value;

@ActionClass(description = "")
public class RegistrationEventAction {

    @Value("bot.open")
    private static boolean botOpen;

    @EventAction(targetState = RegistrationState.WAITING_NAME_STATE, returnToOriginalState = 0L, event = BasicEvent.START)
    public static void registration(Message message) {
        message.getUserIdentifier().getMessageExecutor().sendText(RegistrationMessages.WRITE_NAME_MESSAGE, null);
    }

    @EventAction(targetState = RegistrationState.WAITING_NAME_STATE, sourceState = RegistrationState.WAITING_CONFIRM_NAME_STATE, returnToOriginalState = 0L, event = RegistrationEvent.CANCEL_NAME)
    public static void cancelName(Message message) {
        message.getUserIdentifier().getMessageExecutor().sendText(RegistrationMessages.START_MESSAGES, null);
    }

    @EventAction(targetState = RegistrationState.WAITING_CONFIRM_NAME_STATE, returnToOriginalState = 0L, event = RegistrationEvent.SET_NAME)
    public static void setStateWaitingName(Message message) {
        message.getUserIdentifier().getMessageExecutor().sendText(RegistrationMessages.CONFIRM_NAME_MESSAGES, KeyboardBuilder.buildKeyBoard(KeyboardTag.CONFIRM_NAME_TAG, message));
    }

    @EventAction(sourceState = RegistrationState.WAITING_CONFIRM_NAME_STATE, returnToOriginalState = 0L, event = RegistrationEvent.CONFIRM_NAME)
    public static void confirmName(Message message) throws StateMachineException, UserActionException {
        String name = StateMachine.getObjectFromStage(message.getUserIdentifier()).toString();
        if (name == null) throw new UserActionException(RegistrationMessages.NO_NAME_ENTERED);
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
            case ADMIN -> message.getUserIdentifier().getMessageExecutor().sendText(RegistrationMessages.START_ADMIN_MESSAGES, null);
            case DEVELOPER -> message.getUserIdentifier().getMessageExecutor().sendText(RegistrationMessages.START_DEVELOPER_MESSAGES, null);
            case USER -> message.getUserIdentifier().getMessageExecutor().sendText(RegistrationMessages.WAIT_FOR_CONFIRMATION, null);
        }
    }
}
