package kpi.leonchyk.diploma.repository;

import kpi.leonchyk.diploma.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {
    Role findByRole(String role);
}
