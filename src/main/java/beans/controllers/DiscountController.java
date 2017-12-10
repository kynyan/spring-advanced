package beans.controllers;

import beans.models.Event;
import beans.models.User;
import beans.services.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DiscountController {
    @Autowired
    private DiscountService discountService;

    @RequestMapping(path = "/discount")
    public double getDiscount(@RequestParam("user") User user, @RequestBody Event event) {
        return discountService.getDiscount(user, event);
    }
}
