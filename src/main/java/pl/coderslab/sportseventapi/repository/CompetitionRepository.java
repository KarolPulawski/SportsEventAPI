package pl.coderslab.sportseventapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.sportseventapi.entity.Competition;

import java.util.List;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Integer> {
    List<Competition> findAllByEnabled();
}
