package com.sidlight.xobot.core.statemachine;

import com.sidlight.xobot.core.command.UserActionException;
import com.sidlight.xobot.core.message.Message;
import com.sidlight.xobot.core.message.UserIdentifier;
import com.sidlight.xobot.core.statemachine.annotations.ActionClass;
import com.sidlight.xobot.core.statemachine.annotations.EventAction;
import com.sidlight.xobot.core.statemachine.annotations.StateAction;
import com.sidlight.xobot.core.statemachine.enums.BasicEvent;
import com.sidlight.xobot.core.statemachine.enums.BasicState;
import org.atteo.classindex.ClassIndex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class StateMachine {

    private static Logger logger = LoggerFactory.getLogger(StateMachine.class);

    private static AtomicBoolean serverStatus = new AtomicBoolean(false);

    private static final Map<UserIdentifier, String> states = new ConcurrentHashMap<>();

    private static final Map<UserIdentifier, Object> storage = new ConcurrentHashMap<>();

    public static void cancel(Message message) {
        states.remove(message.getUserIdentifier());
        states.remove(message.getUserIdentifier());
    }

    public static Object getObjectFromStage(UserIdentifier userIdentifier) throws StateMachineException {
        if (storage.containsKey(userIdentifier) && storage.get(userIdentifier) != null) {
            Object obj = storage.get(userIdentifier);
            storage.remove(obj);
            return obj;
        }
        throw new StateMachineException("Object from userIdentifier not found");
    }

    public static Object getObjectWithoutDelete(UserIdentifier userIdentifier) throws StateMachineException {
        if (storage.containsKey(userIdentifier) && storage.get(userIdentifier) != null) {
            return storage.get(userIdentifier);
        }
        throw new StateMachineException("Object from userIdentifier not found");
    }

    public static void setObjectFromStage(UserIdentifier userIdentifier, Object object) throws StateMachineException {
        storage.put(userIdentifier, object);
    }

    public static void initStateMachine() throws StateMachineException {
        List<StateTreeItem> items = new ArrayList<>();
        for (Class<?> klass : ClassIndex.getAnnotated(ActionClass.class)) {
            Arrays.stream(klass.getDeclaredMethods())
                    .forEach(method ->
                            {
                                if (method.getAnnotation(EventAction.class) != null) {
                                    String event = method.getAnnotation(EventAction.class).event();
                                    String targetState = method.getAnnotation(EventAction.class).targetState();
                                    String sourceState = method.getAnnotation(EventAction.class).sourceState();
                                    items.add(new StateTreeItem(sourceState, targetState, event));
                                }
                            }
                    );

        }
        new StateChecker().checkStates(items);
    }

    /**
     * @param message сообщение
     * @return флаг нахождения в каком либо состоянии кроме ожидания
     * @throws StateMachineException
     */
    public static boolean checkAndAcceptStateAction(Message message) throws StateMachineException, UserActionException {
        if (!states.containsKey(message.getUserIdentifier())
                || states.get(message.getUserIdentifier()).equals(BasicState.WAITING_STATE)) {
            return false;
        }
        acceptStateAction(message, states.get(message.getUserIdentifier()));
        return true;
    }

    public static boolean executeEvent(Message message, String event) throws StateMachineException {
        if (event == BasicEvent.END) {
            states.remove(message.getUserIdentifier());
            storage.remove(message.getUserIdentifier());
            return true;
        }
        acceptEvent(event, message);
        return true;
    }

    private static void acceptStateAction(Message message, String state) {
        List<Method> actions = new ArrayList<>();
        for (Class<?> clazz : ClassIndex.getAnnotated(ActionClass.class)) {
            actions.addAll(Arrays.stream(clazz.getDeclaredMethods())
                    .filter(method -> {
                        return (method.getAnnotation(StateAction.class) != null
                                && method.getAnnotation(StateAction.class).state().equals(state));
                    })
                    .toList());
        }
        if (actions.isEmpty()) {
            logger.warn("Event from state " + state + " not found");
        }
        for (Method action : actions) {
            try {
                action.invoke(null, message);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void acceptEvent(String eventName, Message message) throws StateMachineException {
        Optional<Method> event = Optional.empty();
        for (Class<?> klass : ClassIndex.getAnnotated(ActionClass.class)) {
            event = Arrays.stream(klass.getDeclaredMethods())
                    .filter(method ->
                            {
                                if (method.getAnnotation(EventAction.class) != null) {
                                    EventAction annotation = method.getAnnotation(EventAction.class);
                                    return annotation.event().equals(eventName);
                                } else return false;
                            }
                    )
                    .findFirst();
            if (event.isPresent()) break;
        }
        if (event.isEmpty()) throw new StateMachineException("Event not found", null);
        try {
            String targetEvent = event.get().getAnnotation(EventAction.class).targetState();
            states.put(message.getUserIdentifier(), targetEvent);
            event
                    .get()
                    .invoke(null, message);
            logger.debug("Set state \"" + targetEvent + "\" from " + message.getUserIdentifier().toString());
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new StateMachineException("Error when changing State Machine state", e);
        }
    }
}
