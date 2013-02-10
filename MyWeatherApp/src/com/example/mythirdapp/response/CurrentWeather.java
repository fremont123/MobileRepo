package com.example.mythirdapp.response;

import java.io.Serializable;
import java.util.ArrayList;

public class CurrentWeather implements Serializable {
	
	private ArrayList<Wind> wind;
	private String humidity;
	private String pressure;
	private String temp;
	private String tempUnit;
	private String weatherCode;
	private String weatherText;
	
	public ArrayList<Wind> getWind() {
		return wind;
	}
	public void setWind(ArrayList<Wind> wind) {
		this.wind = wind;
	}
	public String getHumidity() {
		return humidity;
	}
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	public String getPressure() {
		return pressure;
	}
	public void setPressure(String pressure) {
		this.pressure = pressure;
	}
	public String getTemp() {
		return temp;
	}
	public void setTemp(String temp) {
		this.temp = temp;
	}
	public String getTempUnit() {
		return tempUnit;
	}
	public void setTempUnit(String tempUnit) {
		this.tempUnit = tempUnit;
	}
	public String getWeatherCode() {
		return weatherCode;
	}
	public void setWeatherCode(String weatherCode) {
		this.weatherCode = weatherCode;
	}
	public String getWeatherText() {
		return weatherText;
	}
	public void setWeatherText(String weatherText) {
		this.weatherText = weatherText;
	}
	
	
	

}
