package beans.controllers;

import beans.models.Auditorium;
import beans.services.AuditoriumService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AuditoriumController {
    private AuditoriumService auditoriumService;

    public AuditoriumController(AuditoriumService auditoriumService) {
        this.auditoriumService = auditoriumService;
    }

    @RequestMapping(path = "/auditoriums")
    public List<Auditorium> getAuditoriums() {
        return auditoriumService.getAuditoriums();
    }

    @RequestMapping(path = "/auditorium/{name}")
    public Auditorium getByName(@PathVariable("name") String name) {
        return auditoriumService.getByName(name);
    }

    @RequestMapping(path = "/auditorium/{name}/seats")
    public int getSeatsNumber(@PathVariable("name") String name) {
        return auditoriumService.getSeatsNumber(name);
    }

    @RequestMapping(path = "/auditorium/{name}/vip-seats")
    public List<Integer> getVipSeats(@PathVariable("name") String name) {
        return auditoriumService.getVipSeats(name);
    }
}
