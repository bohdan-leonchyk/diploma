package kpi.leonchyk.diploma.service.impl;

import kpi.leonchyk.diploma.domain.*;
import kpi.leonchyk.diploma.repository.FilmRepo;
import kpi.leonchyk.diploma.repository.RoleRepo;
import kpi.leonchyk.diploma.repository.SubscriptionRepo;
import kpi.leonchyk.diploma.repository.UserRepo;
import kpi.leonchyk.diploma.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.*;

import static kpi.leonchyk.diploma.util.Utility.isSubscriptionEnd;
import static kpi.leonchyk.diploma.util.Utility.processSubscriptionPeriodAndPrice;
import static org.apache.logging.log4j.util.Strings.trimToNull;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final String ROLE = "PORTAL_USER";

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private SubscriptionRepo subscriptionRepo;
    @Autowired
    private FilmRepo filmRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username.toLowerCase());
    }

    @Override
    public void signUp(String username, String password) {
        if (trimToNull(username) == null || trimToNull(password) == null) {
            throw new RuntimeException("Required field is null.");
        }
        User user = new User();
        user.setUsername(username.toLowerCase());
        user.setPassword(passwordEncoder.encode(password));
        Role role = roleRepo.findByRole(ROLE);
        user.setRoles(new HashSet<>(Collections.singletonList(role)));
        user.setRegDate(new Date());
        user.setBalance(0);
        user.setActive(true);
        user.setSubscribed(false);
        userRepo.save(user);
        LOGGER.info("User successfully created: {}", user);
    }

    @Override
    public void checkSubscriptionDate(User user) {
        if (isSubscriptionEnd(user)) {
            user.setSubscribed(false);
            userRepo.save(user);
            LOGGER.info("Subscription has been ended");
        }
    }

    @Override
    @Transactional
    public void subscribe(SubscriptionType subscriptionType, Principal principal) {
        User user = userRepo.findByUsername(principal.getName());
        Subscription subscription = processSubscriptionPeriodAndPrice(subscriptionType, user);
        user.getSubscriptions().add(subscription);
        user.setSubscribed(true);
        subscriptionRepo.save(subscription);
        LOGGER.info("Subscribed: {}", subscription);
    }

    @Override
    public Collection<Film> getFilms() {
        return filmRepo.findAll();
    }

    @Override
    public Collection<Film> findFilmsByTitle(String title) {
        LOGGER.info("Find films by title: {}", title);
        return filmRepo.findByTitleContaining(title);
    }

    @Override
    public Collection<Film> findFilmsByGenre(String genre) {
        LOGGER.info("Find films by genre: {}", genre);
        try {
            Genre genreType = Genre.valueOf(genre);
            return filmRepo.findByGenre(genreType.name());
        } catch (IllegalArgumentException e) {
            return Collections.emptyList();
        }
    }
}
