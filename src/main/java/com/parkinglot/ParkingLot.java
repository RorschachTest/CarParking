package com.parkinglot;

import com.parkinglot.interfaces.SearchInterface;

import java.util.*;

public class ParkingLot implements SearchInterface {

  private static ParkingLot INSTANCE = null;

  private List<ParkingSpot> allSpot;
  private Queue<ParkingSpot> availableSpots;

  private Map<String, List<String>> colorToRegMap = new HashMap<>();
  private Map<String, Integer> regToSlotMap = new HashMap<>();

  // creating singleton for parkingLot as there can be only one car parking
  public ParkingLot getInstance() {
    if(INSTANCE == null){
      throw new RuntimeException("Sorry, parking lot is not created");
    }
    return INSTANCE;
  }

  public void createParkingLot(Integer spotCount){
    INSTANCE = new ParkingLot(spotCount);
  }

  public ParkingLot(Integer spotCount) {
    if(INSTANCE!=null){
      // making singleton class reflection safe
      // object can't be created twice
      throw new RuntimeException("Parking lot is already created");
    }

    this.allSpot = new ArrayList<>();


  }
}
