package com.parkinglot.interfaces;

import com.parkinglot.ParkingSpot;

import java.util.List;

public interface SearchInterface {

  ParkingSpot getBestSpot();

  List<String> getRegistrationForColor(String color);

  Integer getSlotIdForRegistration(String regId);

  List<Integer> getSlotForColour(String color);

}
