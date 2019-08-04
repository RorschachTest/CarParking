package com.parkinglot;

import java.util.Date;
import java.util.UUID;

public class ParkingTicket {

  private String  ticketNumber;
  private Date    startTime;
  private Date    endTime;
  private Double  payableAmount;
  private Integer spotId;

  public ParkingTicket(Date startTime, Integer spotId) {

    this.ticketNumber = UUID.randomUUID().toString();
    this.startTime = startTime;
    this.spotId = spotId;
  }

}
