package com.sidlight.xobot.messages.scenarios.registration;

import com.sidlight.xobot.core.Core;
import com.sidlight.xobot.core.message.Message;
import com.sidlight.xobot.core.statemachine.StateMachine;
import com.sidlight.xobot.core.statemachine.StateMachineException;
import com.sidlight.xobot.core.statemachine.annotations.ActionClass;
import com.sidlight.xobot.core.statemachine.annotations.StateAction;
import com.sidlight.xobot.messages.scenarios.registration.resource.RegistrationEvent;
import com.sidlight.xobot.messages.scenarios.registration.resource.RegistrationState;

@ActionClass(description = "")
public class RegistrationStateAction {

    @StateAction(state = RegistrationState.WAITING_NAME_STATE)
    public static void setName(Message message) throws StateMachineException {
        StateMachine.setObjectFromStage(message.getUserIdentifier(), message.getText());
        StateMachine.executeEvent(message, RegistrationEvent.SET_NAME);
    }

    @StateAction(state = RegistrationState.WAITING_CONFIRM_NAME_STATE)
    public static void confirmName(Message message) throws StateMachineException {
        Core.runCommand(message);
    }
}
