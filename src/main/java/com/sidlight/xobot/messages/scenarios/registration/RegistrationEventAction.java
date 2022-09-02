package com.sidlight.xobot.messages.scenarios.registration;

import com.sidlight.xobot.core.UserIdentifier;
import com.sidlight.xobot.core.statemachine.annotations.EventAction;
import com.sidlight.xobot.core.statemachine.enums.BasicEvent;

public class RegistrationEventAction {

    @EventAction(targetState = RegistrationState.WAITING_NAME_STATE, returnToOriginalState = 0L, event = BasicEvent.START)
    public static void registration(UserIdentifier chatIdentifier, String[] args) {
        //ожидает ввод имени
    }

    @EventAction(sourceState = RegistrationState.WAITING_CONFIRM_NAME_STATE, returnToOriginalState = 0L, event = RegistrationEvent.CONFIRM_NAME)
    public static void confirmName(UserIdentifier chatIdentifier, String[] args) {
        //ожидает ввод имени
    }
}
