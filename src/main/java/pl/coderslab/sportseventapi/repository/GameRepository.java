package pl.coderslab.sportseventapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.coderslab.sportseventapi.entity.Game;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    List<Game> findAllByActiveTrue();

    List<Game> findAllByActiveFalseAndHistoryFalse();

    @Query("SELECT g FROM Game g WHERE g.id IN ?1")
    List<Game> findAllGameByIdList(List<Integer> id);
}
