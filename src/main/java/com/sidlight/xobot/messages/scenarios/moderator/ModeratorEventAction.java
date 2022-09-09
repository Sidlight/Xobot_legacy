package com.sidlight.xobot.messages.scenarios.moderator;

import com.sidlight.xobot.core.access.StateRegister;
import com.sidlight.xobot.core.command.KeyboardBuilder;
import com.sidlight.xobot.core.message.Message;
import com.sidlight.xobot.core.statemachine.StateMachine;
import com.sidlight.xobot.core.statemachine.StateMachineException;
import com.sidlight.xobot.core.statemachine.annotations.ActionClass;
import com.sidlight.xobot.core.statemachine.annotations.EventAction;
import com.sidlight.xobot.db.domainobject.User;
import com.sidlight.xobot.db.managers.UserManager;
import com.sidlight.xobot.messages.scenarios.KeyboardTag;
import com.sidlight.xobot.messages.scenarios.resource.Events;
import com.sidlight.xobot.messages.scenarios.resource.States;

import java.util.List;

@ActionClass
public class ModeratorEventAction {

    @EventAction(sourceState = States.ModeratorStates.CONFIRM_STREAM, event = Events.ModeratorEvents.START_CONFIRM_STREAM, returnToOriginalState = 0l)
    public static void confirmStateAction(Message message) {
        List<User> users = UserManager.get().getAllUserFrom(StateRegister.PENDING_CONFIRMATION);
        if (users.isEmpty()) {
            message.getUserIdentifier().getMessageExecutor().sendText("Запросы на регистрацию отсутствуют", null);
            return;
        }
        message.getUserIdentifier().getMessageExecutor().sendText(users.get(0).toString(), KeyboardBuilder.buildKeyBoard(KeyboardTag.STREAM_CONFIRM, message));
    }

    @EventAction(targetState = States.ModeratorStates.CONFIRM_STREAM, event = Events.ModeratorEvents.START_CONFIRM_STREAM, returnToOriginalState = 0l)
    public static void cancelStreamConfirm(Message message) throws StateMachineException {
        StateMachine.executeEvent(message, Events.ModeratorEvents.STOP_STREAM_CONFIRM);
    }
}
