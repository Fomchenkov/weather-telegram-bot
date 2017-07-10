package com.kronver.telegram;

/**
 * Created by slava on 10.07.17.
 * Weather view class.
 */
public class WeatherView {

    private WeatherModel wm;

    public WeatherView(WeatherModel model) {
        wm = model;
    }

    /**
     * Generate weather message.
     * @return message.
     */
    public String getWeatherMessage() {
        if (wm.getCity() == null) {
            return "Нет такого города.";
        }
        return "Город " + wm.getCity() + ".\n" +
                "Сейчас на улице " + wm.getDesc() + ".\n" +
                "Скорость ветра: " + wm.getWind() + " м/с.\n" +
                "Температура воздуха: " + wm.getTemp() + "°C";
    }
}
