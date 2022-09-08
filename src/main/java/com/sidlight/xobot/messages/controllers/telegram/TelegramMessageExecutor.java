package com.sidlight.xobot.messages.controllers.telegram;

import com.sidlight.xobot.core.message.MessageExecutor;
import com.sidlight.xobot.core.message.Messenger;
import com.sidlight.xobot.core.message.TypeFile;
import com.sidlight.xobot.messages.controllers.BotController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.media.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EnableAutoConfiguration
public class TelegramMessageExecutor implements MessageExecutor {

    private final TelegramBotController sender;
    private final String chatId;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public TelegramMessageExecutor(TelegramBotController sender, String chatId) {
        this.sender = sender;
        this.chatId = chatId;
    }

    @Override
    public BotController getController(){
        return sender;
    }


    @Override
    public Messenger getMessenger() {
        return Messenger.TELEGRAM;
    }

    @Override
    public void sendText(String text, List<Map<String, String>> keyboards) {
        if (text.length() == 0) return;
        SendMessage sendMessage = new SendMessage();
        if (keyboards != null && !keyboards.isEmpty()) {
            sendMessage.setReplyMarkup(buildInlineKeyBoard(keyboards));
        }
        sendMessage.setChatId(chatId);
        try {
            if (text.length() >= 4096) {
                sendMessage.setText(text.substring(0, 4095));
                sender.execute(sendMessage);
                sendText(text.substring(4096), keyboards);
            }
            sendMessage.setText(text);
            sender.execute(sendMessage);
        } catch (TelegramApiException e) {
            logger.error(e.getMessage());
            SendMessage sendMessage1 = new SendMessage();
            sendMessage1.setChatId(chatId);
            sendMessage1.setText("OverLimitCount " + text.split("\n").length);
            try {
                sender.execute(sendMessage1);
            } catch (TelegramApiException ex) {
                logger.error(ex.getMessage());
            }
        }
    }

    @Override
    public void sendFile(File file, TypeFile typeFile, String description) {
        switch (typeFile) {
            case FILE -> {
                SendDocument sendDocument = new SendDocument();
                sendDocument.setChatId(chatId);
                sendDocument.setDocument(new InputFile(file));
                sendDocument.setCaption(description);
                try {
                    sender.execute(sendDocument);
                } catch (TelegramApiException e) {
                    logger.error(e.getMessage());
                }
            }
            case AUDIO -> {
                SendAudio sendAudio = new SendAudio();
                sendAudio.setChatId(chatId);
                sendAudio.setAudio(new InputFile(file));
                sendAudio.setCaption(description);
                try {
                    sender.execute(sendAudio);
                } catch (TelegramApiException e) {
                    logger.error(e.getMessage());
                }
            }
            case PHOTO -> {
                SendPhoto sendPhoto = new SendPhoto();
                sendPhoto.setCaption(description);
                sendPhoto.setPhoto(new InputFile(file));
                sendPhoto.setChatId(chatId);
                try {
                    sender.execute(sendPhoto);
                } catch (TelegramApiException e) {
                    logger.error(e.getMessage());
                }
            }
            case VIDEO -> {
                SendVideo sendVideo = new SendVideo();
                sendVideo.setCaption(description);
                sendVideo.setVideo(new InputFile(file));
                sendVideo.setChatId(chatId);
                try {
                    sender.execute(sendVideo);
                } catch (TelegramApiException e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }

    @Override
    public void sendFiles(Map<File, TypeFile> files, String description) {
        ArrayList<InputMedia> sendGroup = new ArrayList<>();
        for (Map.Entry<File, TypeFile> entry : files.entrySet()) {
            InputMedia inputMedia = null;
            switch (entry.getValue()) {
                case VIDEO:
                    inputMedia = new InputMediaVideo();
                    break;
                case FILE:
                    inputMedia = new InputMediaDocument();
                    break;
                case AUDIO:
                    inputMedia = new InputMediaAudio();
                    break;
                case PHOTO:
                    inputMedia = new InputMediaPhoto();
                    break;
            }

            if (sendGroup.isEmpty() && inputMedia != null) {
                inputMedia.setCaption(description);
            }
            if (sendGroup.size() > 9) {
                SendMediaGroup sendMediaGroup = new SendMediaGroup();
                sendMediaGroup.setMedias(sendGroup);
                sendMediaGroup.setChatId(chatId);
                sendMediaGroup(sendMediaGroup);
                sendGroup = new ArrayList<>();
            }
        }
        if (!sendGroup.isEmpty()) {
            SendMediaGroup sendMediaGroup = new SendMediaGroup();
            sendMediaGroup.setMedias(sendGroup);
            sendMediaGroup.setChatId(chatId);
            sendMediaGroup(sendMediaGroup);
        }
    }

    @Override
    public void sendGPSCoordinate(double latitude, double longitude) {

    }

    private InlineKeyboardMarkup buildInlineKeyBoard(List<Map<String, String>> keyboards) {
        List<List<InlineKeyboardButton>> resultList = new ArrayList<>();
        for (Map<String, String> row : keyboards) {
            List<InlineKeyboardButton> rowResult = new ArrayList<>();
            for (Map.Entry<String, String> button : row.entrySet()) {
                InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
                inlineKeyboardButton.setText(button.getKey());
                inlineKeyboardButton.setCallbackData(button.getValue());
                rowResult.add(inlineKeyboardButton);
            }
            resultList.add(rowResult);
        }
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(resultList);
        return inlineKeyboardMarkup;
    }

    private void sendMediaGroup(SendMediaGroup sendMediaGroup) {
        try {
            sender.execute(sendMediaGroup);
        } catch (TelegramApiException e) {
            logger.error(e.getMessage());
        }
    }
}
