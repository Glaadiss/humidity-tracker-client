package com.example.bartlomiejgladys.temptracker.model;

public class Response {

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getTemperatureOut() {
        return temperatureOut;
    }

    public void setTemperatureOut(float temperatureOut) {
        this.temperatureOut = temperatureOut;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHumidityOut() {
        return humidityOut;
    }

    public void setHumidityOut(float humidityOut) {
        this.humidityOut = humidityOut;
    }

    public float getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(float temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public float getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(float temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    public float getHumidityMax() {
        return humidityMax;
    }

    public void setHumidityMax(float humidityMax) {
        this.humidityMax = humidityMax;
    }

    public float getHumidityMin() {
        return humidityMin;
    }

    public void setHumidityMin(float humidityMin) {
        this.humidityMin = humidityMin;
    }

    private int updatedAt;
    private float temperature;
    private float humidity;
    private float temperatureOut;
    private float humidityOut;
    private float temperatureMax;
    private float temperatureMin;
    private float humidityMax;
    private float humidityMin;

    public int getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(int updatedAt) {
        this.updatedAt = updatedAt;
    }
}


