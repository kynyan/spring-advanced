package beans;

import beans.aspects.CounterAspect;
import beans.aspects.DiscountAspect;
import beans.aspects.LuckyWinnerAspect;
import beans.models.*;
import beans.services.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class StartupFiller implements ApplicationListener<ContextRefreshedEvent> {
    private static int counter;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent appEvent) {
        if (counter == 0) {
            ApplicationContext ctx = appEvent.getApplicationContext();

            AuditoriumService auditoriumService = (AuditoriumService) ctx.getBean("auditoriumServiceImpl");
            BookingService bookingService = (BookingService) ctx.getBean("bookingServiceImpl");
            EventService eventService = (EventService) ctx.getBean("eventServiceImpl");
            UserService userService = (UserService) ctx.getBean("userServiceImpl");
            DiscountService discountService = (DiscountService) ctx.getBean("discountServiceImpl");

            String email = "dmitriy.vbabichev@gmail.com";
            String name = "Dmytro Babichev";
            String eventName = "The revenant";
            String auditoriumName = "Blue hall";
            Auditorium blueHall = auditoriumService.getByName(auditoriumName);
            Auditorium yellowHall = auditoriumService.getByName("Yellow hall");
            Auditorium redHall = auditoriumService.getByName("Red hall");
            LocalDateTime dateOfEvent = LocalDateTime.of(LocalDate.of(2016, 2, 5), LocalTime.of(15, 45, 0));

            userService.register(new User(email, name, LocalDate.now()));
            userService.register(new User("laory@yandex.ru", name, LocalDate.of(1992, 4, 29)));

            User userByEmail = userService.getUserByEmail(email);
            System.out.println("User with email: [" + email + "] is " + userByEmail);
            System.out.println();

            System.out.println("All users with name: [" + name + "] are: ");
            userService.getUsersByName(name).forEach(System.out::println);
            System.out.println();

            Event event1 = eventService.create(
                    new Event(eventName, Rate.HIGH, 60, LocalDateTime.of(LocalDate.of(2016, 2, 5), LocalTime.of(9, 0, 0)),
                            blueHall));
            System.out.println();
            System.out.println("Event by name: " + eventService.getByName(event1.getName()));
            System.out.println();
            eventService.create(new Event(eventName, Rate.HIGH, 60, dateOfEvent, blueHall));
            Event event2 = eventService.create(
                    new Event(eventName, Rate.HIGH, 60, LocalDateTime.of(LocalDate.of(2016, 2, 5), LocalTime.of(21, 18, 0)),
                            blueHall));
            eventService.create(
                    new Event(eventName, Rate.HIGH, 90, LocalDateTime.of(LocalDate.of(2016, 2, 5), LocalTime.of(21, 18, 0)),
                            redHall));
            Event event3 = new Event(eventName, Rate.HIGH, 150,
                    LocalDateTime.of(LocalDate.of(2016, 2, 5), LocalTime.of(21, 18, 0)), yellowHall);
            event3 = eventService.create(event3);

            System.out.println("List of all events:");
            eventService.getAll().forEach(System.out::println);
            System.out.println();

            System.out.println(
                    "Discount for user: [" + email + "] for event: [" + eventName + "] in auditorium: [" + auditoriumName +
                            "] on date: [" + dateOfEvent + "] is [" +
                            discountService.getDiscount(userByEmail, eventService.getEvent(eventName, blueHall, dateOfEvent))
                            + "]");
            System.out.println();

            eventService.remove(event2);
            System.out.println("List of all events:");
            eventService.getAll().forEach(System.out::println);
            System.out.println();

            List<Integer> seats = Arrays.asList(23, 24, 25, 26);
            double ticketPrice = bookingService.getTicketPrice(event3.getName(), event3.getAuditorium().getName(),
                    event3.getDateTime(), seats, userByEmail);
            System.out.println("Price for event: [" + event3 + "], seats: [" + seats + "], user: [" + userByEmail + "] = "
                    + ticketPrice);

            List<Integer> seats2 = Arrays.asList(27, 28, 29, 30);
            List<Integer> seats3 = Arrays.asList(2, 8, 9, 3);
            bookingService.bookTicket(userByEmail, new Ticket(event3, LocalDateTime.now(), seats, userByEmail, ticketPrice));
            bookingService.bookTicket(userByEmail, new Ticket(event3, LocalDateTime.now(), seats2, userByEmail,
                    bookingService.getTicketPrice(event3.getName(),
                            event3.getAuditorium().getName(),
                            event3.getDateTime(), seats2,
                            userByEmail)));
            bookingService.bookTicket(userByEmail, new Ticket(event3, LocalDateTime.now(), seats3, userByEmail,
                    bookingService.getTicketPrice(event3.getName(),
                            event3.getAuditorium().getName(),
                            event3.getDateTime(), seats3,
                            userByEmail)));

            System.out.println();
            System.out.println("Tickets booked for event: [" + event3 + "]");
            List<Ticket> ticketsForEvent = bookingService.getTicketsForEvent(event3.getName(),
                    event3.getAuditorium().getName(),
                    event3.getDateTime());
            IntStream.range(0, ticketsForEvent.size()).forEach(
                    i -> System.out.println("" + i + ") " + ticketsForEvent.get(i)));

            System.out.println();
            eventService.getByName("testName1");
            eventService.getByName("testName2");
            eventService.getByName("testName2");
            eventService.getByName("testName3");
            eventService.getByName(eventName);
            eventService.getEvent(event3.getName(), event3.getAuditorium(), event3.getDateTime());

            bookingService.getTicketPrice(event3.getName(), event3.getAuditorium().getName(), event3.getDateTime(), seats,
                    userByEmail);
            bookingService.getTicketPrice(event3.getName(), event3.getAuditorium().getName(), event3.getDateTime(), seats,
                    userByEmail);

            System.out.println("CounterAspect.getAccessByNameStat() = " + CounterAspect.getAccessByNameStat());
            System.out.println("CounterAspect.getGetPriceByNameStat() = " + CounterAspect.getGetPriceByNameStat());
            System.out.println("CounterAspect.getBookTicketByNameStat() = " + CounterAspect.getBookTicketByNameStat());
            System.out.println();
            System.out.println("DiscountAspect.getDiscountStatistics() = " + DiscountAspect.getDiscountStatistics());
            System.out.println();
            System.out.println("LuckyWinnerAspect.getLuckyUsers() = " + LuckyWinnerAspect.getLuckyUsers());
            counter++;
            System.out.println("counter: " + counter);
        }
    }

}
