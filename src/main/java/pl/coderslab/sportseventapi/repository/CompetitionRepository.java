package pl.coderslab.sportseventapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.sportseventapi.entity.Competition;

import java.util.List;

public interface CompetitionRepository extends JpaRepository<Competition, Integer> {

}
