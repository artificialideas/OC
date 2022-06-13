package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class TicketDAOTest {
    private static Ticket ticket;
    private static TicketDAO ticketDAO;

    @BeforeEach
    public void setup() {
        ticket = new Ticket();
        ticketDAO = new TicketDAO();

        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (45 * 60 * 1000));
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        ticket.setVehicleRegNumber("ABCDEF");
    }

    @Test
    public void givenTicket_whenSave_shouldReturnTrue() {
        ticketDAO.saveTicket(ticket);
        assert(ticketDAO.checkVisits("ABCDEF"));
    }

    @Test
    public void givenTicket_whenUpdate_shouldReturnTrue() {
        ticketDAO.saveTicket(ticket);
        assert(ticketDAO.updateTicket(ticketDAO.getTicket("ABCDEF")));
    }
}
