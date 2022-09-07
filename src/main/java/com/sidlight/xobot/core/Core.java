package com.sidlight.xobot.core;

import com.sidlight.xobot.core.statemachine.StateMachine;
import com.sidlight.xobot.db.BotDataException;
import com.sidlight.xobot.db.managers.UserManager;
import org.atteo.classindex.ClassIndex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Core {

    private static Logger logger = LoggerFactory.getLogger(Core.class);

    public static void accept(Message message) {
        boolean isCommandNotFound = true;
        Role userRole = null;
        try {
            userRole = UserManager.get().getUserFromUserIdentifier(message.getUserIdentifier()).getRole();
        } catch (BotDataException e) {
            userRole = Role.USER;
        }
        try {
            for (Class<?> klass : ClassIndex.getAnnotated(BotCommands.class)) {
                Role finalUserRole = userRole;
                if (StateMachine.checkAndAcceptStateAction(message)) return;
                String command = message.getCommand();
                if (command == null) {
                    message.getUserIdentifier().getMessageExecutor().sendText("Действие не найдено", null);
                    return;
                }
                List<Method> methodList = Arrays.stream(klass.getDeclaredMethods())
                        .filter(method ->
                                {
                                    if (method.getAnnotation(Command.class) != null) {
                                        Annotation annotation = method.getAnnotation(Command.class);
                                        boolean withPermission = AccessChecker.checkAccess(((Command) annotation).roles(), ((Command) annotation).messengers(), finalUserRole);
                                        if (withPermission && ((Command) annotation).name().equals(command) && method.getAnnotation(Off.class) == null) {
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    } else return false;
                                }
                        )
                        .collect(Collectors.toList());
                if (!methodList.isEmpty()) {
                    try {
                        methodList.get(0).invoke(null,
                                message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    isCommandNotFound = false;
                    break;
                }
            }
            if (isCommandNotFound) {
                //logger.debug("Command not found: " + command);
                message.getUserIdentifier().getMessageExecutor().sendText("Действие не найдено", null);
            }
        } catch (UserActionException e) {
            message.getUserIdentifier().getMessageExecutor().sendText(e.getMessage(), null);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}