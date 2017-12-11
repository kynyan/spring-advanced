package beans.controllers;

import beans.models.User;
import beans.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST, path = "/user")
    public User register(User user) {
        return userService.register(user);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/user")
    public ResponseEntity remove(User user) {
        userService.remove(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @RequestMapping(path = "/user/{id}")
    public User getById(@PathVariable("id") long id) {
        return userService.getById(id);
    }

    @RequestMapping(path = "/user")
    public String getUserByEmail(@RequestParam("email") String email, @ModelAttribute Model model) {
        System.out.println("user: " + userService.getUserByEmail(email).getName());
        model.addAttribute("user", userService.getUserByEmail(email));
        return "user";
    }

    @RequestMapping(path = "/users/{name}")
    public String getUsersByName(@PathVariable("name") String name, @ModelAttribute("model") ModelMap model) {
        model.addAttribute("users", userService.getUsersByName(name));
        return "users";
    }
}
