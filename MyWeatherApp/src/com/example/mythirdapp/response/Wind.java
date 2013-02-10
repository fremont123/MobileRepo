package com.example.mythirdapp.response;

import java.io.Serializable;

public class Wind implements Serializable {
	
	private String direction;
	private String speed;
	private String windUnit;
	
	
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public String getWindUnit() {
		return windUnit;
	}
	public void setWindUnit(String windUnit) {
		this.windUnit = windUnit;
	}
	

}
