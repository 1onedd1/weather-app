package com.publisher.model;

public class WeatherModel {
    private Weather weather[];
    private Wind wind;
    private Main main;

    public class Weather {
        private String main;

        public String getMain() {
            return main;
        }

        @Override
        public String toString() {
            return "Weather{" +
                    "main='" + main + '\'' +
                    '}';
        }
    }

    public class Wind {
        private double speed;

        public double getSpeed() {
            return speed;
        }

        @Override
        public String toString() {
            return "Wind{" +
                    "speed=" + speed +
                    '}';
        }
    }

    public class Main {
        private int pressure;
        private int humidity;
        private double temp;

        public int getPressure() {
            return pressure;
        }

        public int getHumidity() {
            return humidity;
        }

        public double getTemp() {
            return temp;
        }

        @Override
        public String toString() {
            return "Main{" +
                    "pressure=" + pressure +
                    ", humidity=" + humidity +
                    ", temp=" + temp +
                    '}';
        }
    }

    public Weather[] getWeather() {
        return weather;
    }

    public Wind getWind() {
        return wind;
    }

    public Main getMain() {
        return main;
    }
}