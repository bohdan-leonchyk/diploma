package kpi.leonchyk.diploma.repository;

import kpi.leonchyk.diploma.domain.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepo extends JpaRepository<Subscription, Integer> {

}
