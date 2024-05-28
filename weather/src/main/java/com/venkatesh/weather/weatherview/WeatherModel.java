package com.venkatesh.weather.weatherview;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONObject;
import java.io.FileReader;
import java.io.File;

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

        System.out.println("-------------------------------------------");
        System.out.println("Weather in " + cityName + ":");
        System.out.println("Temperature: " + temperature + "°C");
        System.out.println("Humidity: " + humidity + "%");
        System.out.println("Description: " + description);
        System.out.println("-------------------------------------------");
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

    public void getPinnedCitiesWeatherDetails() {
        File dataFile = new File(".\\src\\main\\java\\com\\venkatesh\\weather\\weatherview\\data.txt");

        try (FileReader reader = new FileReader(dataFile)) {
            int character;
            StringBuilder city = new StringBuilder();

            while ((character = reader.read()) != -1) {
                if(character == '\n') {
                    getWeatherData(city.toString());
                    city.setLength(0);
                } else {
                    city.append((char) character);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void fileOperation(String cityName) {
        File dataFile = new File(".\\src\\main\\java\\com\\venkatesh\\weather\\weatherview\\data.txt");
        if(!dataFile.exists()){
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                System.out.println("can't create file");
            }
        }

        try (FileWriter writer = new FileWriter(dataFile, true)) {
            writer.write(cityName + "\n");
            System.out.println("City name saved as a data.");
        } catch (IOException e) {
            System.out.println("Could not save the data");
        }

        getPinnedCitiesWeatherDetails();
    }
}
