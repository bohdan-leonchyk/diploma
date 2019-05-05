package kpi.leonchyk.diploma.service;

import kpi.leonchyk.diploma.domain.Film;
import kpi.leonchyk.diploma.domain.SubscriptionType;
import kpi.leonchyk.diploma.domain.User;

import java.security.Principal;
import java.util.Collection;

public interface UserService {
    User findByUsername(String username);
    void signUp(String username, String password);
    void checkSubscriptionDate(User user);
    void subscribe(SubscriptionType subscriptionType, Principal principal);
    Collection<Film> getFilms();
}
