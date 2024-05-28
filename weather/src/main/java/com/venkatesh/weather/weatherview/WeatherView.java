package com.venkatesh.weather.weatherview;

import com.venkatesh.weather.WeatherApp;

import java.io.File;
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

        System.out.println("\n");

        checkSetup();

        weatherModel.getPinnedCitiesWeatherDetails();

        while (true) {
            try {
                System.out.println("1. View the weather of specific city using city name.");
                System.out.println("2. View the weather of specific place using latitude and longitude");
                System.out.println("3. Add more cities in the start screen.");
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
                        setup();
                        break;
                    case 4:
                        showAlert("Thank you for using console-weather app.");
                        return;
                    default:
                        showAlert("Invalid choice. Please enter a valid choice from the above choices.");
                        break;
                }
            } catch (InputMismatchException e) {
                showAlert("Invalid Input. Please enter a number from the above choices.");
                sc.nextLine();
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

    private void setup() {
        System.out.println("Do you want to setup your location so you can view the weather easily without searching?");
        System.out.println("(y for yes and n for no)");
        char choice = sc.next().charAt(0);
        sc.nextLine();  // Consume the leftover newline

        switch (choice) {
            case 'Y':
            case 'y':
                System.out.print("Enter city name: ");
                String cityName = sc.nextLine();
                System.out.println(cityName);
                weatherModel.fileOperation(cityName);
                break;
            case 'N':
            case 'n':
                return;
            default:
                System.out.println("Invalid choice. Aborting setup.");
                break;
        }
    }

    private void checkSetup() {
        File dataFile = new File(".\\src\\main\\java\\com\\venkatesh\\weather\\weatherview\\data.txt");
        if(!dataFile.exists()) {
            setup();
        } else if(dataFile.length() == 0) {
            setup();
        }
    }
}
