package beans.controllers;

import beans.models.Ticket;
import beans.models.User;
import beans.services.BookingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class BookingController {

    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @RequestMapping(path = "/price")
    public double getTicketPrice(String event, String auditorium, LocalDateTime dateTime, List<Integer> seats, User user) {
        return bookingService.getTicketPrice(event, auditorium, dateTime, seats, user);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/ticket")
    public Ticket bookTicket(User user, Ticket ticket) {
        return bookingService.bookTicket(user, ticket);
    }

    @RequestMapping(path = "/tickets")
    public List<Ticket> getTicketsForEvent(String event, String auditorium, LocalDateTime date) {
        return bookingService.getTicketsForEvent(event, auditorium, date);
    }
}
