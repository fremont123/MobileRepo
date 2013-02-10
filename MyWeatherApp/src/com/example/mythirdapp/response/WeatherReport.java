package com.example.mythirdapp.response;

import java.io.Serializable;
import java.util.ArrayList;

public class WeatherReport implements Serializable {
	
	private ArrayList<CurrentWeather> curr_weather;

	public ArrayList<CurrentWeather> getCurr_weather() {
		return curr_weather;
	}

	public void setCurr_weather(ArrayList<CurrentWeather> curr_weather) {
		this.curr_weather = curr_weather;
	}
	
	


}
