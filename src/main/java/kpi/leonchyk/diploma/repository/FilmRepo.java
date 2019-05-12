package kpi.leonchyk.diploma.repository;

import kpi.leonchyk.diploma.domain.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepo extends JpaRepository<Film, Integer> {
    List<Film> findByTitleContaining(String title);
}
