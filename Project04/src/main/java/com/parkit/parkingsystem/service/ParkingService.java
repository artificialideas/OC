package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.Objects;

public class ParkingService {

    private static final Logger logger = LogManager.getLogger("ParkingService");

    private static FareCalculatorService fareCalculatorService = new FareCalculatorService();

    private InputReaderUtil inputReaderUtil;
    private ParkingSpotDAO parkingSpotDAO;
    private TicketDAO ticketDAO;

    public ParkingService(InputReaderUtil inputReaderUtil, ParkingSpotDAO parkingSpotDAO, TicketDAO ticketDAO) {
        this.inputReaderUtil = inputReaderUtil;
        this.parkingSpotDAO = parkingSpotDAO;
        this.ticketDAO = ticketDAO;
    }

    public void processIncomingVehicle() {
        try {
            ParkingSpot parkingSpot = getNextParkingNumberIfAvailable();
            if (parkingSpot !=null && parkingSpot.getId() > 0) {
                String vehicleRegNumber = getVehicleRegNumber();
                parkingSpot.setAvailable(false);
                parkingSpotDAO.updateParking(parkingSpot); // Allot this parking space and mark its availability as false
                // ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME)

                Date inTime = new Date();
                Ticket ticket = new Ticket();
                // If current vehicle register number is not registered on our DB, process the current ticket
                if (vehicleRegNumber != ticketDAO.getTicket(vehicleRegNumber).getVehicleRegNumber()) {
                    // ticket.setId(ticketID);
                    ticket.setParkingSpot(parkingSpot);
                    ticket.setVehicleRegNumber(vehicleRegNumber);
                    ticket.setPrice(0);
                    ticket.setInTime(inTime);
                    ticket.setOutTime(null);
                    ticketDAO.saveTicket(ticket);
                    System.out.println("Generated Ticket and saved in DB.");
                    System.out.println("Please park your vehicle in spot number: " + parkingSpot.getId());
                    System.out.println("Recorded in-time for vehicle number: " + vehicleRegNumber + " is: " + inTime);
                } else {
                    // Entered number is currently in use
                    System.out.println("Wrong number. Please enter a valid vehicle register number.");
                }
            }
        } catch (Exception e) {
            logger.error("Unable to process incoming vehicle.", e);
        }
    }

    private String getVehicleRegNumber() throws Exception {
        System.out.println("Please type the vehicle registration number and press enter key.");
        return inputReaderUtil.readVehicleRegistrationNumber();
    }

    public ParkingSpot getNextParkingNumberIfAvailable() {
        int parkingNumber = 0;
        ParkingSpot parkingSpot = null;
        try {
            ParkingType parkingType = getVehicleType();
            parkingNumber = parkingSpotDAO.getNextAvailableSlot(parkingType);
            if (parkingNumber > 0) {
                parkingSpot = new ParkingSpot(parkingNumber,parkingType, true);
            } else {
                throw new Exception("Error fetching parking number from DB. Parking slots might be full.");
            }
        } catch (IllegalArgumentException ie) {
            logger.error("Error parsing user input for type of vehicl.e", ie);
        } catch (Exception e) {
            logger.error("Error fetching next available parking slot.", e);
        }
        return parkingSpot;
    }

    private ParkingType getVehicleType() {
        System.out.println("Please select vehicle type from menu:");
        System.out.println("1 CAR");
        System.out.println("2 BIKE");
        int input = inputReaderUtil.readSelection();
        switch (input) {
            case 1: {
                return ParkingType.CAR;
            }
            case 2: {
                return ParkingType.BIKE;
            }
            default: {
                System.out.println("Incorrect input provided.");
                throw new IllegalArgumentException("Entered input is invalid.");
            }
        }
    }

    public void processExitingVehicle() {
        try {
            String vehicleRegNumber = getVehicleRegNumber();
            Ticket ticket = ticketDAO.getTicket(vehicleRegNumber);
            Date outTime = new Date();
            double totalPrice;

            ticket.setOutTime(outTime);
            fareCalculatorService.calculateFare(ticket);
            if (ticketDAO.updateTicket(ticket)) {
                // Free parking spot
                ParkingSpot parkingSpot = ticket.getParkingSpot();
                parkingSpot.setAvailable(true);
                parkingSpotDAO.updateParking(parkingSpot);

                if (ticket.getPrice() != 0) {
                    // Discount of 5% if vehicleRegNumber have been registered on past sessions
                    if ((Objects.equals(vehicleRegNumber, ticketDAO.getTicket(vehicleRegNumber).getVehicleRegNumber())) &&
                            (ticketDAO.getTicket(vehicleRegNumber).getOutTime() != null)) {
                        // It's a recurrent client
                        totalPrice = ticket.getPrice() - (ticket.getPrice() * 0.05);
                    } else {
                        totalPrice = ticket.getPrice();
                    }
                    // Round price to two decimals
                    totalPrice = (double) Math.round(totalPrice * 100) / 100;

                    System.out.println("Please pay the parking fare: " + totalPrice + "€");
                } else {
                    System.out.println("Your stay was less than 30 minutes. You don't have to pay any fare.");
                }
                System.out.println("Recorded out-time for vehicle number " + ticket.getVehicleRegNumber() + " is:" + outTime);
            } else {
                System.out.println("Unable to update ticket information. Error occurred.");
            }
        } catch (Exception e) {
            logger.error("Unable to process exiting vehicle.", e);
        }
    }
}
