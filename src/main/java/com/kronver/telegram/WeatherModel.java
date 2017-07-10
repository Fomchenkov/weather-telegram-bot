package com.kronver.telegram;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONException;

/**
 * Created by slava on 10.07.17.
 * Weather model class.
 */
public class WeatherModel {

    private final String weatherToken;
    private final String searchCity;

    private String city;
    private String desc;
    private String wind;
    private String temp;

    public WeatherModel(String token, String city) {
        weatherToken = token;
        searchCity = city;
    }

    public String getCity() {
        return city;
    }

    public String getDesc() {
        return desc;
    }

    public String getWind() {
        return wind;
    }

    public String getTemp() {
        return temp;
    }

    /**
     * Get weather data.
     * @throws UnirestException - request exception.
     */
    public void getWeather() throws UnirestException {
        String url = "http://api.openweathermap.org/data/2.5/weather";
        JsonNode node = Unirest.post(url)
                .queryString("q", searchCity)
                .queryString("appid", weatherToken)
                .queryString("lang", "ru")
                .queryString("units", "metric")
                .asJson().getBody();

        try {
            node.getObject().getString("name");
        } catch (JSONException e) {
            return;
        }

        city = node.getObject().getString("name");
        desc = node.getObject().getJSONArray("weather")
                .getJSONObject(0).getString("description");
        wind = node.getObject().getJSONObject("wind").get("speed").toString();
        temp = node.getObject().getJSONObject("main").get("temp").toString();
    }
}
