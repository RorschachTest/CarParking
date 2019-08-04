package com.parkinglot;

public class ParkingSpot {
  private static Integer idCounter = 0;

  public static synchronized Integer getIdCounter() {

    return idCounter++;
  }

  private Integer spotId;
  private Boolean isFree;
  private Vehicle parkedVehicle;

  public Integer getSpotId() {

    return spotId;
  }

  public Boolean getFree() {

    return isFree;
  }

  public Vehicle getParkedVehicle() {

    return parkedVehicle;
  }

  public void setFree(Boolean free) {

    isFree = free;
  }

  public void setParkedVehicle(Vehicle parkedVehicle) {

    this.parkedVehicle = parkedVehicle;
  }

  public ParkingSpot() {

    this.spotId = getIdCounter();
    this.isFree = true;
  }
}
