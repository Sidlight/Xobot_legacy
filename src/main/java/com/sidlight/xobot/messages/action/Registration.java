package com.sidlight.xobot.messages.action;

import com.sidlight.xobot.core.UserIdentifier;
import com.sidlight.xobot.core.statemachine.annotations.EventAction;
import com.sidlight.xobot.core.statemachine.annotations.EventClass;
import com.sidlight.xobot.core.statemachine.enums.BasicEvent;

@EventClass(description = "Регистрация")
public class Registration {
    private static final String WAITING_NAME_STATE = "waiting_name_state";
    private static final String WAITING_CONFIRM_NAME_STATE = "waiting_confirm_name_state";

    private static final String CONFIRM_NAME_EVENT = "confirm_name_event";

    @EventAction(targetState = WAITING_NAME_STATE, returnToOriginalState = 0L, event = BasicEvent.START_EVENT)
    public static void registration(UserIdentifier chatIdentifier, String[] args) {
        //ожидает ввод имени
    }

    @EventAction(sourceState = WAITING_CONFIRM_NAME_STATE, returnToOriginalState = 0L, event = CONFIRM_NAME_EVENT)
    public static void confirmName(UserIdentifier chatIdentifier, String[] args) {
        //ожидает ввод имени
    }
    /*

    EventAction
    исходное состояние
    целевое состояние
    время до сброса
    событие

    StateAction
    состояние

     */
}
