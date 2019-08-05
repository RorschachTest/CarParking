package com.parkinglot.interfaces;

import com.parkinglot.models.ParkingSpot;

import java.util.List;

public interface SearchInterface {

  ParkingSpot getBestSpot();

  void getRegistrationForColor(String color);

  void getSpotIdForRegistration(String regId);

  void getSpotForColour(String color);

}
