package com.parkinglot.processors;

import com.parkinglot.exceptions.ParkingException;
import com.parkinglot.models.ParkingLot;
import com.parkinglot.models.Vehicle;

import java.io.*;

public class InputParser {
  static ParkingLot parkingLot;

  public void parseTextInput(String inputString) {
    // Split the input string to get command and input value
    String[] inputs = inputString.split(" ");
      try {
        String command = inputs[0];

        switch (command){
          case "create_parking_lot": {
            Integer spotSize = Integer.valueOf(inputs[1]);
            ParkingLot.createParkingLot(spotSize);
            break;
          }
          case "park": {
            String regNumber = inputs[1];
            String color = inputs[2];
            parkingLot = ParkingLot.getInstance();

            Vehicle vehicle = new Vehicle(regNumber, color);
            vehicle.assignParkingTicket(parkingLot.parkVehicle(vehicle));
            break;
          }
          case "leave": {
            Integer spotId = Integer.valueOf(inputs[1]);
            parkingLot = ParkingLot.getInstance();

            parkingLot.unparkVehicle(spotId);
            break;
          }
          case "status": {
            parkingLot = ParkingLot.getInstance();

            parkingLot.getStatus();
            break;
          }
          case "registration_numbers_for_cars_with_colour": {
            String color = inputs[1];
            parkingLot = ParkingLot.getInstance();

            parkingLot.getRegistrationForColor(color);
            break;
          }
          case "slot_numbers_for_cars_with_colour": {
            String color = inputs[1];
            parkingLot = ParkingLot.getInstance();

            parkingLot.getSpotForColour(color);
            break;
          }
          case "slot_number_for_registration_number": {
            String regNo = inputs[1];
            parkingLot = ParkingLot.getInstance();

            parkingLot.getSpotIdForRegistration(regNo);
            break;
          }
          default:{
            System.out.println("Error with the input");
          }
        }

      } catch (ArrayIndexOutOfBoundsException exp){
        exp.printStackTrace();
      } catch (ParkingException ep){
        System.out.println(ep.getMessage());
      }
      catch (Exception e){
        e.printStackTrace();
      }
  }
  public void parseFileInput(String filePath) {
    // Assuming input to be a valid file path.
    File inputFile = new File(filePath);
    try {
      BufferedReader br = new BufferedReader(new FileReader(inputFile));
      String line;
      try {
        while ((line = br.readLine()) != null) {
          parseTextInput(line.trim());
        }
      } catch (IOException ex) {
        System.out.println("Error in reading the input file.");
        ex.printStackTrace();
      }
    } catch (FileNotFoundException e) {
      System.out.println("File not found in the path specified.");
      e.printStackTrace();
    }
  }
}
