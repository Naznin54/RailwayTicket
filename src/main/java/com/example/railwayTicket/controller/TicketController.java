package com.example.railwayTicket.controller;



import com.example.entity.Ticket;
import com.example.service.TicketBookingProducer;
import com.example.service.TicketBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketBookingService ticketBookingService;
    private final TicketBookingProducer ticketBookingProducer;

    @Autowired
    public TicketController(TicketBookingService ticketBookingService, TicketBookingProducer ticketBookingProducer) {
        this.ticketBookingService = ticketBookingService;
        this.ticketBookingProducer = ticketBookingProducer;
    }

    @PostMapping
    public ResponseEntity<Ticket> bookTicket(@RequestBody Ticket ticket) {
        Ticket bookedTicket = ticketBookingService.bookTicket(ticket);
        ticketBookingProducer.sendMessage(bookedTicket);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookedTicket);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        Ticket ticket = ticketBookingService.getTicketById(id);
        if (ticket != null) {
            return ResponseEntity.ok(ticket);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> tickets = ticketBookingService.getAllTickets();
        if (!tickets.isEmpty()) {
            return ResponseEntity.ok(tickets);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
