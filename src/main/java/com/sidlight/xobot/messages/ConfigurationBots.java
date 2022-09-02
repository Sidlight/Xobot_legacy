package com.sidlight.xobot.messages;

import com.sidlight.xobot.messages.controllers.telegram.TelegramBotController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:bots.properties")
public class ConfigurationBots {

    @Bean
    public TelegramBotController getTGBotController() {
        return new TelegramBotController();
    }

}
