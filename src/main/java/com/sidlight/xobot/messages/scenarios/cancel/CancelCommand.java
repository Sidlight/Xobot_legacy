package com.sidlight.xobot.messages.scenarios.cancel;

import com.sidlight.xobot.core.access.Role;
import com.sidlight.xobot.core.command.BotCommands;
import com.sidlight.xobot.core.command.Command;
import com.sidlight.xobot.core.message.Message;
import com.sidlight.xobot.core.statemachine.StateMachine;
import com.sidlight.xobot.core.statemachine.StateMachineException;

@BotCommands
public class CancelCommand {

    @Command(command = "/cancel", roles = {Role.ALL})
    public void cancel(Message message) throws StateMachineException {
        StateMachine.cancel(message);
    }

}
