package pl.coderslab.sportseventapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.sportseventapi.entity.Game;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    List<Game> findAllByActiveTrue();

    List<Game> findAllByActiveFalseAndHistoryFalse();
}
