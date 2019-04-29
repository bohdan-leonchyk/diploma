package kpi.leonchyk.diploma.service.impl;

import kpi.leonchyk.diploma.domain.Role;
import kpi.leonchyk.diploma.domain.Subscription;
import kpi.leonchyk.diploma.domain.SubscriptionType;
import kpi.leonchyk.diploma.domain.User;
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
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

import static kpi.leonchyk.diploma.util.Utility.processSubscriptionPeriodAndPrice;
import static org.apache.logging.log4j.util.Strings.trimToNull;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final SubscriptionRepo subscriptionRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo, SubscriptionRepo subscriptionRepo,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.subscriptionRepo = subscriptionRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username.toLowerCase());
    }

    @Override
    public void save(String username, String password) {
        if (trimToNull(username) == null || trimToNull(password) == null) {
            throw new RuntimeException("Required field is null.");
        }
        User user = new User();
        user.setUsername(username.toLowerCase());
        user.setPassword(passwordEncoder.encode(password));
        Role role = roleRepo.findByRole("PORTAL_USER");
        user.setRoles(new HashSet<>(Collections.singletonList(role)));
        user.setRegDate(new Date());
        user.setBalance(0);
        user.setActive(true);
        userRepo.save(user);
        LOGGER.info("User successfully created: {}", user);
    }

    @Override
    @Transactional
    public void subscribe(SubscriptionType subscriptionType, Principal principal) {
        User user = userRepo.findByUsername(principal.getName());
        Subscription subscription = processSubscriptionPeriodAndPrice(subscriptionType, user);
        user.getSubscriptions().add(subscription);
        subscriptionRepo.save(subscription);
    }
}
