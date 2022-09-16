package com.sidlight.xobot.messages.scenarios.workinuser;

import com.sidlight.xobot.core.BotException;
import com.sidlight.xobot.core.command.KeyboardBuilder;
import com.sidlight.xobot.core.message.Message;
import com.sidlight.xobot.core.statemachine.StateMachine;
import com.sidlight.xobot.core.statemachine.StateMachineException;
import com.sidlight.xobot.core.statemachine.annotations.ActionClass;
import com.sidlight.xobot.core.statemachine.annotations.EventAction;
import com.sidlight.xobot.core.statemachine.annotations.StateAction;
import com.sidlight.xobot.core.statemachine.enums.BasicEvent;
import com.sidlight.xobot.db.managers.UserManager;

@ActionClass
public class WorkInUserAction {

    @StateAction(state = States.WAITING_SEND_USER_ID)
    public static void actionWaitingSendUserId(Message message) throws StateMachineException {
        String text = message.getText();
        StringBuilder stringBuilder = new StringBuilder("Подтвердите");
        try {
            for (String str : text.split(" ")) {
                stringBuilder.append(UserManager.get().getUserFromId(Long.parseLong(str)).toString()).append(";\n");
            }
        } catch (NumberFormatException e) {
            StateMachine.executeEvent(message, BasicEvent.CANCEL);
            throw new BotException("Некорректный ввод");
        }
        message.sendRequest(stringBuilder.toString(), KeyboardBuilder.buildKeyboard(WorkInUserCommand.Keyboards.CONFIRM_FROM_ID, message));
    }

    @EventAction(sourceState = States.WAITING_SEND_USER_ID, event = Events.START_CONFIRM_USER_FROM_ID)
    public static void eventActionStartConfirmUserFromId(Message message) {
        message.sendRequest("Введите id пользователей для подтверждения, через пробел");
    }

    public class States {
        public static final String WAITING_CONFIRM_USER_FROM_ID = "waiting_confirm_user_from_id";
        public static final String WAITING_SEND_USER_ID = "waiting_send_user_id";
    }

    public class Events {
        public static final String START_CONFIRM_USER_FROM_ID = "start_confirm_user_from_id";
    }
}
