package kpi.leonchyk.diploma.service.impl;

import kpi.leonchyk.diploma.domain.Subscription;
import kpi.leonchyk.diploma.repository.SubscriptionRepo;
import kpi.leonchyk.diploma.service.SubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionServiceImpl.class);
    private final SubscriptionRepo subscriptionRepo;

    @Autowired
    public SubscriptionServiceImpl(SubscriptionRepo subscriptionRepo) {
        this.subscriptionRepo = subscriptionRepo;
    }

    @Override
    public void save(Principal principal) {
        Subscription subscription = new Subscription();
    }
}
