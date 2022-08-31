package com.sidlight.xobot.core.statemachine;

import com.sidlight.xobot.core.UserIdentifier;
import com.sidlight.xobot.core.Message;
import com.sidlight.xobot.core.statemachine.annotations.EventAction;
import com.sidlight.xobot.core.statemachine.annotations.EventClass;
import com.sidlight.xobot.core.statemachine.annotations.StateAction;
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

    public static void initStateMachine() throws StateMachineException {
        List<StateTreeItem> items = new ArrayList<>();
        for (Class<?> klass : ClassIndex.getAnnotated(EventClass.class)) {
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
    public static boolean checkAndAcceptStateAction(Message message) throws StateMachineException {
        if (!states.containsKey(message.getChatIdentifier())
                || states.get(message.getChatIdentifier()).equals(BasicState.WAITING_STATE)) {
            return false;
        }
        acceptStateAction(message, states.get(message.getChatIdentifier()));
        return true;
    }

    public static boolean executeEvent(Message message, String event) throws StateMachineException {
        acceptEvent(event, message);
        return true;
    }

    private static void acceptStateAction(Message message, String state) {
        List<Method> actions = new ArrayList<>();
        for (Class<?> clazz : ClassIndex.getAnnotated(EventClass.class)) {
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
        for (Class<?> klass : ClassIndex.getAnnotated(EventClass.class)) {
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
            event
                    .get()
                    .invoke(null, message.getChatIdentifier().messenger().getMessageExecutor(null),
                            message);
            String targetEvent = event.get().getAnnotation(EventAction.class).targetState();
            states.put(message.getChatIdentifier(), targetEvent);
            logger.debug("Set state \"" + targetEvent + "\" from " + message.getChatIdentifier().toString());
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new StateMachineException("Error when changing State Machine state", e);
        }
    }


    /*

    public static void initStateMachine() throws StateMachineException {
        Set<String> requiredEvent = new HashSet<>();
        try {
            for (Class<?> clazz : ClassIndex.getAnnotated(Events.class)) {
                Arrays.stream(clazz.getDeclaredFields())
                        .filter(field -> field.getAnnotation(RequiredEvent.class) != null)
                        .forEach(field -> {
                            try {
                                requiredEvent.add((String) field.get(null));
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                        });
            }
        } catch (RuntimeException e) {
            throw new StateMachineException(e, "There are duplicate required events");
        }
        List<Method> allAction = null;
        for (Class<?> clazz : ClassIndex.getAnnotated(EventClass.class)) {
            allAction = Arrays.stream(clazz.getDeclaredMethods())
                    .filter(method -> method.getAnnotation(EventAction.class) != null)
                    .collect(Collectors.toList());
        }
        StateTreeItem waitingNode = new StateTreeItem(BasicState.WAITING_STATE);
        for (Method method : allAction) {
            waitingNode.addNode(method.getAnnotation(EventAction.class).);
        }
        if (true) throw new StateMachineException("Error while initializing the State Machine");
    }
*/


    @Deprecated
    public String getState(UserIdentifier chatIdentifier) {
        if (states.get(chatIdentifier) == null) return BasicState.WAITING_STATE;
        return states.get(chatIdentifier);
    }

    @Deprecated
    public static void setState(UserIdentifier chatIdentifier, String state) {
        StateMachine.states.put(chatIdentifier, state);
    }
}
