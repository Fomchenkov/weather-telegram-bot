package com.kronver.telegram;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Telegram Bot can inquire weather.
 * Just send him your city.
 * It's controller class.
 * @author Fomchenkov Vyacheslav.
 * @version 09.07.2017.
 */
public class WeatherBot extends TelegramLongPollingBot {

    private final String WeatherToken = "7ca53812a6dcac1b30f316f807354abd";
    private final String BotToken = "387480715:AAGE4QwIkF6OaTaCln8vB4eAWPwD8Nes_fM";
    private final String BotUsername = "ewtgjerkgltjnblktnbtbnkbot";

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            if (update.getMessage().getText().equals("/start")) {
                String answer = "Привет! Я умею определять погоду. Отправь мне название своего города.";
                long chat_id = update.getMessage().getChatId();

                SendMessage message = new SendMessage()
                        .setChatId(chat_id)
                        .setText(answer);
                try {
                    sendMessage(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                String city = update.getMessage().getText();
                long chat_id = update.getMessage().getChatId();
                String answer = "";

                try {
                    WeatherModel wm = new WeatherModel(WeatherToken, city);
                    wm.getWeather();
                    WeatherView wv = new WeatherView(wm);
                    answer = wv.getWeatherMessage();
                } catch (UnirestException e) {
                    System.out.println(e.getMessage());
                }

                SendMessage message = new SendMessage()
                        .setChatId(chat_id)
                        .setText(answer);
                try {
                    sendMessage(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return BotUsername;
    }

    @Override
    public String getBotToken() {
        return BotToken;
    }
}
