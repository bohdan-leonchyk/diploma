package kpi.leonchyk.diploma.repository;

import kpi.leonchyk.diploma.domain.Film;
import kpi.leonchyk.diploma.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepo extends JpaRepository<Film, Integer> {
    List<Film> findByTitleContaining(String title);

    @Query(value = "select * from film f where f.genre = :genre", nativeQuery = true)
    List<Film> findByGenre(@Param("genre") String genre);
}
