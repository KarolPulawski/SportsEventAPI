package pl.coderslab.sportseventapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.sportseventapi.entity.Game;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Integer> {

    List<Game> findAllByActiveTrue();

    List<Game> findAllByActiveFalseAndHistoryFalse();
}
