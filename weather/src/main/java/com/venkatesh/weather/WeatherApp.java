package com.venkatesh.weather;

import com.venkatesh.weather.weatherview.WeatherView;

public class WeatherApp{
    private static WeatherApp weatherApp;
    private String appName = "Weather App";
    private String appVersion = "0.0.1";

    private WeatherApp() {}

    public static WeatherApp getInstance() {
        if (weatherApp == null) {
            weatherApp = new WeatherApp();
        }

        return weatherApp;
    }

    private void init() {
        WeatherView weatherView = new WeatherView();
        weatherView.init();
    }

    public String getAppName() {
        return appName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public static void main(String[] args) {
        WeatherApp.getInstance().init();
    }

}
