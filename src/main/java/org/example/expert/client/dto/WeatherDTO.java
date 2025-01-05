package org.example.expert.client.dto;

import lombok.Getter;

@Getter
public class WeatherDTO {

    private final String date;
    private final String weather;

    public WeatherDTO(String date, String weather) {
        this.date = date;
        this.weather = weather;
    }
}
