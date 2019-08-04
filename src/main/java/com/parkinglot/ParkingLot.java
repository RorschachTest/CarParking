package com.parkinglot;

import com.parkinglot.interfaces.SearchInterface;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ParkingLot implements SearchInterface {

  private static ParkingLot INSTANCE = null;

  private int MAX_CAPACITY;

  // stores spots with idx -> spotId
  private ParkingSpot[] allSpot;

  // stores in a priorityQueue, lowest id on top
  private Queue<ParkingSpot> availableSpots;

  private Map<String, Set<String>> colorToRegMap = new ConcurrentHashMap<>();
  private Map<String, Integer>     regToSpotMap  = new ConcurrentHashMap<>();

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

    MAX_CAPACITY = spotCount;
    allSpot = new ParkingSpot[spotCount+1];
    availableSpots = new PriorityQueue<>(spotCount, new Comparator<ParkingSpot>() {

      @Override public int compare(ParkingSpot o1, ParkingSpot o2) {

        return o2.getSpotId() - o1.getSpotId();
      }
    });

    // all spots are available
    for(int i=1; i<=MAX_CAPACITY; i++){
      availableSpots.add(allSpot[i]);
    }
  }

  public synchronized ParkingTicket parkVehicle(Vehicle vehicle){
    ParkingSpot parkingSpot = getBestSpot();
    allSpot[parkingSpot.getSpotId()].setFree(false);

    ParkingTicket parkingTicket = new ParkingTicket(new Date(), parkingSpot.getSpotId());

    // updating map for color and registration nos.
    regToSpotMap.put(vehicle.getRegisterationNumber(), parkingSpot.getSpotId());
    if(!colorToRegMap.containsKey(vehicle.getColour())){
      colorToRegMap.put(vehicle.getColour(), new HashSet<>());
    }
    colorToRegMap.get(vehicle.getColour()).add(vehicle.getRegisterationNumber());

    return parkingTicket;
  }

  @Override public ParkingSpot getBestSpot() {

    if(availableSpots.isEmpty()){
      throw new RuntimeException("Sorry Parking is full");
    }

    return availableSpots.poll();
  }

  @Override public List<String> getRegistrationForColor(String color) {

    return colorToRegMap.get(color).stream().collect(Collectors.toList());
  }

  @Override public Integer getSlotIdForRegistration(String regId) {

    return null;
  }

  @Override public List<Integer> getSlotForColour(String color) {

    return null;
  }
}
