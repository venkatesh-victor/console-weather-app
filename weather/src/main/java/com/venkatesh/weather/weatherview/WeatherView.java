package com.venkatesh.weather.weatherview;

import com.venkatesh.weather.WeatherApp;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class WeatherView {
    Scanner sc = new Scanner(System.in);
    private WeatherModel weatherModel;

    public WeatherView() {
        this.weatherModel = new WeatherModel(this);
    }

    public void init() {
        System.out.println("--- " + WeatherApp.getInstance().getAppName() + " ---");
        System.out.println("Version - " + WeatherApp.getInstance().getAppVersion());

        while (true) {
            try {
                System.out.println("1. View the weather of specific city using city name.");
                System.out.println("2. View the weather of specific place using latitude and longitude");
                System.out.println("3. View the weather of your location.");
                System.out.println("4. Exit the application.");

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        String city = getCityName();
                        weatherModel.getWeatherData(city);
                        break;
                    case 2:
                        double[] coOrdinates = getCoordinates();
                        weatherModel.getLocationWeatherData(coOrdinates);
                        break;
                    case 3:
                        //weatherModel.getLocationWeather();
                        break;
                    case 4:
                        return;
                    default:
                        showAlert("Invalid choice. Please enter a valid choice from the above choices.");
                        init();
                }
            } catch (InputMismatchException e) {
                showAlert("Invalid Input. Please enter a number from the above choices.");
                init();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showAlert(String alertMessage) {
        System.out.println(alertMessage);
    }

    private String getCityName() {
        System.out.print("Enter city name: ");
        return sc.nextLine();
    }

    private double[] getCoordinates() {
        System.out.print("Enter latitude: ");
        double lat = sc.nextDouble();
        System.out.print("Enter longitude: ");
        double lon = sc.nextDouble();

        return new double[]{lat, lon};
    }
}
