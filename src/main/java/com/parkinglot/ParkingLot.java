package com.parkinglot;

import com.parkinglot.interfaces.SearchInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class ParkingLot implements SearchInterface {

  private static ParkingLot INSTANCE = null;

  private List<ParkingSpot> allSpot;
  private Queue<ParkingSpot> availableSpots;

  private Map<String, List<String>> colorToRegMap = new HashMap<>();
  private Map<String, Integer> regToSlotMap = new HashMap<>();

  public ParkingLot getInstance() throws Exception{
    if(INSTANCE == null){
      throw new Exception("Sorry, parking lot is not created");
    }
    return INSTANCE;
  }

}
