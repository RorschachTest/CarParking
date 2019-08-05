package com.parkinglot.models;

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

  // creating singleton for parkingLot as there can be only one vehicle parking
  public static ParkingLot getInstance() {
    if(INSTANCE == null){
      throw new RuntimeException("Sorry, parking lot is not created");
    }
    return INSTANCE;
  }

  public static void createParkingLot(Integer spotCount){
    INSTANCE = new ParkingLot(spotCount);
  }

  private ParkingLot(Integer spotCount) {
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
    System.out.println("Created a parking lot with "+ MAX_CAPACITY +" slots");
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

    System.out.println("Allocated slot number: " + parkingSpot.getSpotId());

    return parkingTicket;
  }

  public synchronized void unparkVehicle(Integer spotId){
    ParkingSpot parkedSpot = allSpot[spotId];

    // get parked vehicle details
    Vehicle parkedVehicle = parkedSpot.getParkedVehicle();

    parkedSpot.setFree(true);
    availableSpots.add(parkedSpot);
    regToSpotMap.remove(parkedVehicle.getRegisterationNumber());
    colorToRegMap.get(parkedVehicle.getColour()).remove(parkedVehicle.getColour());

    System.out.println("Slot number "+ spotId +" is free");
  }

  public void getStatus(){

    System.out.println("Slot No.\tRegistration No.\tColor");

    for(int i=1; i<=MAX_CAPACITY; i++){
      if(allSpot[i].getFree()==false){
        Vehicle parkedVehicle = allSpot[i].getParkedVehicle();
        System.out.println(i+"\t"+parkedVehicle.getRegisterationNumber()+"\t"+parkedVehicle.getColour());
      }
    }
  }

  @Override public ParkingSpot getBestSpot() {

    if(availableSpots.isEmpty()){
      throw new RuntimeException("Sorry, parking lot is full");
    }

    return availableSpots.poll();
  }

  @Override public void getRegistrationForColor(String color) {
    String allReg = String.join(", ", (colorToRegMap.get(color)==null? null : colorToRegMap.get(color))
        .stream()
        .collect(Collectors.toList()));

    System.out.println(allReg);
  }

  @Override public void getSpotIdForRegistration(String regId) {

    System.out.println(regToSpotMap.get(regId));
  }

  @Override public void getSpotForColour(String color) {

    String allSpot = String.join(", ", colorToRegMap.get(color).stream()
        .map(reg -> regToSpotMap.get(reg).toString())
        .collect(Collectors.toList()));

    System.out.println(allSpot);
  }

}
