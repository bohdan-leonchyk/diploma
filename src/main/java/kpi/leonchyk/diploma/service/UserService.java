package kpi.leonchyk.diploma.service;

import kpi.leonchyk.diploma.domain.SubscriptionType;
import kpi.leonchyk.diploma.domain.User;

import java.security.Principal;

public interface UserService {
    User findByUsername(String username);
    void save(String username, String password);
    void subscribe(SubscriptionType subscriptionType, Principal principal);
}
