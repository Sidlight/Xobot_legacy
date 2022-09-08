package com.sidlight.xobot.core.message;

import com.sidlight.xobot.messages.ConfigurationBots;
import com.sidlight.xobot.messages.controllers.BotController;
import com.sidlight.xobot.messages.controllers.telegram.TelegramBotController;
import com.sidlight.xobot.messages.controllers.telegram.TelegramMessageExecutor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public enum Messenger {
    TELEGRAM(chatId -> new TelegramMessageExecutor(
            (TelegramBotController) getBotController(TelegramBotController.class), chatId),
            TelegramBotController.class),
    ALL(chatId -> null, null);

    private Class<?> botControllerClazz;

    public Class<?> getBotControllerClazz() {
        return botControllerClazz;
    }

    private MessageExecutorGetter messageExecutorGetter;

    Messenger(MessageExecutorGetter messageExecutorGetter, Class<?> botControllerClazz) {
        this.messageExecutorGetter = messageExecutorGetter;
        this.botControllerClazz = botControllerClazz;
    }

    public MessageExecutor getMessageExecutor(String chatId) {
        return messageExecutorGetter.getMessageExecutor(chatId);
    }

    private static BotController getBotController(Class<?> clazz) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigurationBots.class);
        return (BotController) context.getBean(clazz);
    }

    public static void initBots() throws Exception {
        for (Messenger messengers : Messenger.values()) {
            if (messengers.botControllerClazz == null) continue;
            getBotController(messengers.getBotControllerClazz()).init();
        }
    }
}
