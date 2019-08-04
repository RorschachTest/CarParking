package com.parkinglot;

public class Vehicle {

  private String registerationNumber;
  private String colour;

  private ParkingTicket parkingTicket;

  public String getRegisterationNumber() {

    return registerationNumber;
  }

  public String getColour() {

    return colour;
  }
  public ParkingTicket getParkingTicket(){
    return this.parkingTicket;
  }

  public void assignParkingTicket(ParkingTicket parkingTicket){
    this.parkingTicket = parkingTicket;
  }

  public Vehicle(String registerationNumber, String colour) {

    this.registerationNumber = registerationNumber;
    this.colour = colour;
  }


}
