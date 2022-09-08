package com.sidlight.xobot.core.command;

import com.sidlight.xobot.core.access.AccessChecker;
import com.sidlight.xobot.core.message.Message;
import com.sidlight.xobot.core.statemachine.StateMachineException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.atteo.classindex.ClassIndex;

import java.util.*;

public class KeyboardBuilder {

    public static List<Map<String, String>> buildKeyBoard(String tag, Message message) {
        List<Button> buttons = new ArrayList<>();
        for (Class<?> klass : ClassIndex.getAnnotated(BotCommands.class)) {
            Arrays.stream(klass.getDeclaredMethods())
                    .filter(method -> method.getAnnotation(Keyboard.class) != null &&
                            method.getAnnotation(Command.class) != null &&
                            method.getAnnotation(Keyboard.class).tag().equals(tag) &&
                            AccessChecker.checkAccess(method.getAnnotation(Command.class), message))
                    .forEach(method -> {
                        Keyboard annotation = method.getAnnotation(Keyboard.class);
                        Command commandAnnotation = method.getAnnotation(Command.class);
                        Button button = new Button(annotation.row(), annotation.column(), annotation.text(), commandAnnotation.command());
                        buttons.add(button);
                    });
        }
        int maxRow = 0;
        int maxColumn = 0;

        for (Button button : buttons) {
            if (maxRow < button.row) {
                maxRow = button.row;
            }
            if (maxColumn < button.column) {
                maxColumn = button.column;
            }
        }
        do {
            int arrSize = (maxColumn + 1) * (maxRow + 1);
            if (arrSize >= buttons.size()) {
                break;
            }
            maxColumn++;
            maxRow++;
        } while (true);
        List<List> map = new ArrayList<>();
        for (int i = 0; i < maxRow + 1; i++) {
            List<Button> columnsButton = new ArrayList<>();
            for (int j = 0; j < maxColumn + 1; j++) {
                columnsButton.add(null);
            }
            map.add(columnsButton);
        }
        for (Button button : buttons) {
            if (button.row == -1 || button.column == -1) {
                continue;
            }
            map.get(button.row).set(button.column, button);
        }
        for (Button button : buttons) {
            if (button.column == -1 || button.column == -1) {
                for (int i = 0; i < map.size(); i++) {
                    for (int j = 0; j < map.get(i).size(); j++) {
                        if (map.get(i).get(j) == null) {
                            map.get(i).set(j, button);
                        }
                    }
                }
            }
        }
        List<Map<String, String>> keyboard = new ArrayList<>();
        for (List<Button> buttonList : map) {
            if (buttonList.isEmpty()) continue;
            Map<String, String> map1 = new LinkedHashMap<>();
            for (Button button : buttonList) {
                if (button == null) continue;
                map1.put(button.getText(), button.getCommand());
            }
            keyboard.add(map1);
        }
        return keyboard;
    }

    public static Map<String, String> getCommandMap() throws StateMachineException {
        List<Button> buttons = new ArrayList<>();
        for (Class<?> klass : ClassIndex.getAnnotated(BotCommands.class)) {
            Arrays.stream(klass.getDeclaredMethods())
                    .filter(method -> method.getAnnotation(Keyboard.class) != null)
                    .forEach(method -> {
                        Keyboard annotation = method.getAnnotation(Keyboard.class);
                        Command commandAnnotation = method.getAnnotation(Command.class);
                        Button button = new Button(annotation.row(), annotation.column(), annotation.text(), commandAnnotation.command());
                        buttons.add(button);
                    });
        }
        Map<String, String> map = new HashMap<>();
        for (Button button : buttons) {
            if (map.containsKey(button.getText())) {
                throw new StateMachineException("Repeating tag keyboard");
            } else if (map.containsValue(button.getCommand())) {
                throw new StateMachineException("Repeating command keyboard");
            }
            map.put(button.getText(), button.getCommand());
        }
        return map;
    }

    @AllArgsConstructor
    private static class Button {

        @Getter
        private int row;

        @Getter
        int column;

        @Getter
        private String text;

        @Getter
        private String command;
    }

}
