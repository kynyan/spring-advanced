package beans.controllers;

import beans.models.Auditorium;
import beans.models.Event;
import beans.services.EventService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class EventController {
    private EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/event")
    public Event create(@RequestBody Event event) {
        return eventService.create(event);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/event")
    public ResponseEntity remove(@RequestBody Event event) {
        eventService.remove(event);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @RequestMapping(path = "/event/{name}")
    public Event getEvent(@PathVariable("name") String name,
                          @RequestParam("auditorium") Auditorium auditorium,
                          @RequestParam("date") LocalDateTime dateTime) {
        return eventService.getEvent(name, auditorium, dateTime);
    }

    @RequestMapping(path = "/event/{name}")
    public List<Event> getByName(@PathVariable("name") String name) {
        return eventService.getByName(name);
    }

    @RequestMapping(path = "/events")
    public List<Event> getAll() {
        return eventService.getAll();
    }

    @RequestMapping(path = "/event")
    public List<Event> getEventsByDate(@RequestParam(name = "from", required = false)
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime from,
                                       @RequestParam(name = "to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime to) {
        if (from == null) return eventService.getNextEvents(to);
        return eventService.getForDateRange(from, to);
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/event")
    public Event assignAuditorium(@RequestBody Event event,
                                  @RequestParam("auditorium") Auditorium auditorium,
                                  @RequestParam("date") LocalDateTime date) {
        return eventService.assignAuditorium(event, auditorium, date);
    }
}
