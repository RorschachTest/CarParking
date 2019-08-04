package com.parkinglot.interfaces;

import com.parkinglot.ParkingSpot;

import java.util.List;

public interface SearchInterface {

  ParkingSpot getBestSpot();

  List<String> getRegistrationForColor(String color);

  Integer getSpotIdForRegistration(String regId);

  List<Integer> getSpotForColour(String color);

}
