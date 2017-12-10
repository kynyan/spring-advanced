package beans.controllers;

import beans.models.Ticket;
import beans.models.User;
import beans.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @RequestMapping(path = "/price")
    public double getTicketPrice(String event, String auditorium, LocalDateTime dateTime, List<Integer> seats, User user) {
        return bookingService.getTicketPrice(event, auditorium, dateTime, seats, user);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/ticket")
    public Ticket bookTicket(User user, Ticket ticket) {
        return bookingService.bookTicket(user, ticket);
    }

    @RequestMapping(path = "/tickets")
    public String getTicketsForEvent(@RequestParam("event") String event, @RequestParam("auditorium") String auditorium,
                                     @RequestParam("date") String date,
                                     @ModelAttribute("model") ModelMap model) {

        System.out.println("date and time: " + date);
        LocalDateTime dateTime = LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        model.addAttribute("tickets", bookingService.getTicketsForEvent(event, auditorium, dateTime));
        return "tickets";
    }
}
