package kpi.leonchyk.diploma.controller;

import kpi.leonchyk.diploma.domain.SubscriptionType;
import kpi.leonchyk.diploma.domain.User;
import kpi.leonchyk.diploma.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ResponseEntity<Void> registerUser(@RequestParam String username, @RequestParam String password) {
        try {
            userService.signUp(username, password);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/user/home/data", method = RequestMethod.GET)
    public ResponseEntity<?> login(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        userService.checkSubscriptionDate(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/home/subscribe", method = RequestMethod.POST)
    public ResponseEntity<?> subscribe(@RequestParam String subscriptionType, Principal principal) {
        try {
            SubscriptionType type = SubscriptionType.valueOf(subscriptionType);
            userService.subscribe(type, principal);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            LOGGER.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/user/home/films", method = RequestMethod.GET)
    public ResponseEntity<?> getFilms() {
        return new ResponseEntity<>(userService.getFilms(), HttpStatus.OK);
    }

    @RequestMapping(value = "/user/home/films/title", method = RequestMethod.GET)
    public ResponseEntity<?> findFilmsByTitle(@RequestParam String title) {
        if (title == null || title.trim().length() == 0) {
            return new ResponseEntity<>(userService.getFilms(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(userService.findFilmsByTitle(title), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/user/home/films/genre", method = RequestMethod.GET)
    public ResponseEntity<?> findFilmsByGenre(@RequestParam String genre) {
        if (genre == null || genre.trim().length() == 0) {
            return new ResponseEntity<>(userService.getFilms(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(userService.findFilmsByGenre(genre), HttpStatus.OK);
        }
    }
}
