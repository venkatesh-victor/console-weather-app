package com.venkatesh.weather.weatherview;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import org.json.JSONObject;

public class WeatherModel {
    private static final String API_KEY = "9d79b5d11d4bde6d58f17b00c5ba4f16";
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";

    //http://api.openweathermap.org/data/2.5/weather?qThenkasi&appid=9d79b5d11d4bde6d58f17b00c5ba4f16&units=metric

    private WeatherView weatherView;
    public WeatherModel(WeatherView weatherView) {
        this.weatherView = weatherView;
    }


    public void getWeatherData(String city) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String url = BASE_URL + "?q=" + city + "&appid=" + API_KEY + "&units=metric";

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            parseAndDisplayWeatherData(response.body().string());
        }
    }

    private void parseAndDisplayWeatherData(String response) {
        JSONObject json = new JSONObject(response);
        String cityName = json.getString("name");
        JSONObject main = json.getJSONObject("main");
        double temperature = main.getDouble("temp");
        int humidity = main.getInt("humidity");
        JSONObject weather = json.getJSONArray("weather").getJSONObject(0);
        String description = weather.getString("description");

        System.out.println("Weather in " + cityName + ":");
        System.out.println("Temperature: " + temperature + "°C");
        System.out.println("Humidity: " + humidity + "%");
        System.out.println("Description: " + description);
    }

    public void getLocationWeatherData(double[] coOrdinates) throws IOException {
        String lat = String.valueOf(coOrdinates[0]);
        String lon = String.valueOf(coOrdinates[1]);

        OkHttpClient client = new OkHttpClient();
        String url = BASE_URL + "?lat=" + lat + "&lon=" + lon + "&appid=" + API_KEY + "&units=metric";

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            parseAndDisplayWeatherData(response.body().string());
        }
    }
}
