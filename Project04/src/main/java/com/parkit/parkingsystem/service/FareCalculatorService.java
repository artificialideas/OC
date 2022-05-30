package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

import java.util.concurrent.TimeUnit;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket) {
        if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
            throw new IllegalArgumentException("Out time provided is incorrect: " + ticket.getOutTime().toString());
        }

        // Get times and transform milliseconds to minutes
        long inMinute = TimeUnit.MILLISECONDS.toMinutes(ticket.getInTime().getTime());
        long outMinute = TimeUnit.MILLISECONDS.toMinutes(ticket.getOutTime().getTime());

        double duration = outMinute - inMinute;
        // The firsts 30' are free. They must be discounted from total duration
        if (duration >= 30) {
            switch (ticket.getParkingSpot().getParkingType()) {
                case CAR: {
                    ticket.setPrice((duration - 30) * (Fare.CAR_RATE_PER_HOUR / 60));
                    break;
                }
                case BIKE: {
                    ticket.setPrice((duration - 30) * (Fare.BIKE_RATE_PER_HOUR / 60));
                    break;
                }
                default:
                    throw new IllegalArgumentException("Unknown Parking Type.");
            }
        }
    }
}