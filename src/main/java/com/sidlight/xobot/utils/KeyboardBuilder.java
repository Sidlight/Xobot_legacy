package com.sidlight.xobot.utils;

import com.sidlight.xobot.core.BotCommands;
import com.sidlight.xobot.core.Keyboard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.atteo.classindex.ClassIndex;

import java.util.*;
import java.util.stream.Collectors;

public class KeyboardBuilder {

    public static List<Map<String, String>> buildKeyBoard(String tag) {
        List<Button> buttons = new ArrayList<>();
        for (Class<?> klass : ClassIndex.getAnnotated(BotCommands.class)) {
            Arrays.stream(klass.getDeclaredMethods())
                    .filter(method -> method.getAnnotation(Keyboard.class) != null)
                    .filter(method -> method.getAnnotation(Keyboard.class).tag().equals(tag))
                    .forEach(method -> {
                        Keyboard annotation = method.getAnnotation(Keyboard.class);
                        Button button = new Button(annotation.row(), annotation.column(), annotation.tag(), annotation.command());
                       buttons.add(button);
                    });
        }
        List<Map<String,String>> keyboard = new ArrayList<>();
        buttons.stream().
    }

    @AllArgsConstructor
    private static class Button {
        int row;
        @Getter
        int column;
        String tag;
        String command;
    }

    private static class ButtonBuilder {
        private final List<List<Button>> buttons;

        public ButtonBuilder() {
            buttons = new ArrayList<>();
        }

        public void addButton(Button button) {
            if (buttons.size() - 1 < button.row) {
                for (int i = 0; i < button.row - buttons.size() + 1; i++) {
                    buttons.add(new ArrayList<>());
                }
            }
            if (buttons.get(button.row).size() - 1 < button.column) {
                for (int i = 0; i < button.row - buttons.get(button.row).size() + 1; i++) {
                    buttons.get(button.row).add(null);
                }
            }
            buttons.get(button.row).set(button.column, button);
        }

        public List<Map<String, String>> getKeboard() {

            buttons.stream()
                    .sorted(Comparator.comparing(Button::getColumn))
                    .map(buttonRow->buttonRow;

            List<Map<String, String>> keyboard = new ArrayList<>();
            for (List<Button> buttonRow : buttons) {
                for (Button button : buttonRow) {
                    if (keyboard.size() - 1 < button.row) {
                        keyboard.add(new LinkedHashMap<>());
                    }

                }
            }
            return keyboard;
        }
    }
  /*  private static class Keyboard {
        private final ArrayList<Map<String, String>> buttons;
        public Keyboard() {
            this.buttons = new ArrayList<>();
        }

        public Map<String, String> build() {
            buttons.add(null);
            return null;
        }

        private static class Button {
            Button
        }
    }*/
}
