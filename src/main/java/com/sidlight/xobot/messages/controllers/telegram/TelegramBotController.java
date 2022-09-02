package com.sidlight.xobot.messages.controllers.telegram;

import com.sidlight.xobot.core.Core;
import com.sidlight.xobot.core.Message;
import com.sidlight.xobot.core.Messenger;
import com.sidlight.xobot.core.UserIdentifier;
import com.sidlight.xobot.messages.controllers.BotController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;
import java.util.List;

@Service
@EnableAutoConfiguration
public class TelegramBotController extends TelegramLongPollingBot implements BotController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${tg.bot.name}")
    private String botUsername;
    @Value("${tg.bot.token}")
    private String token;

    @Value("${tg.bot.admin}")
    private String adminName;


    public TelegramBotController() {

    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void init() throws Exception {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(this);
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = new Message();
        String userName;
        String chatId;
        if (update.getMessage() == null) {
            if (update.getCallbackQuery() != null) {
                message.setText(callBackCommand(update.getCallbackQuery().getData()));
                userName = update.getCallbackQuery().getMessage().getChat().getUserName();
                if (userName == null) {
                    userName = update.getCallbackQuery().getMessage().getChat().getId().toString();
                }
                message.setUserName(userName);
                chatId = update.getCallbackQuery().getMessage().getChatId().toString();
            } else {
                return;
            }
        } else {
            chatId = update.getMessage().getChatId().toString();
            message.setText(update.getMessage().getText());
            userName = update.getMessage().getChat().getUserName();
            if (userName == null) {
                userName = update.getMessage().getChat().getId().toString();
            }
            message.setFiles(getPhotoFile(update.getMessage().getPhoto()));
        }
        message.setUserName(userName);
        message.setUserIdentifier(new UserIdentifier(chatId, Messenger.TELEGRAM));
        Core.accept(message);
    }


    private List<File> getPhotoFile(List<PhotoSize> list) {
        return null;
    }

    private String callBackCommand(String callbackData) {
        return null;
    }
}
