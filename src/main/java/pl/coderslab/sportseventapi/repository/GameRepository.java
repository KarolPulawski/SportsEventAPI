package pl.coderslab.sportseventapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.coderslab.sportseventapi.entity.Game;
import pl.coderslab.sportseventapi.entity.Team;

import javax.persistence.criteria.CriteriaBuilder;
import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    List<Game> findAllByActiveTrue();

    List<Game> findAllByActiveFalseAndHistoryFalse();
    List<Game> findAllByActiveTrueAndHistoryFalse();


    @Query("SELECT g FROM Game g WHERE g.id IN ?1")
    List<Game> findAllGameByIdList(List<Integer> id);

//    @Query("SELECT g.homePoint, g.awayPoint FROM Game g WHERE g.teamAway = ?1 AND g.teamHome = ?1")
//    @Query("SELECT g.teamHome, g.teamAway, g.homePoint, g.awayPoint FROM Game g WHERE team_home_id = ?1 OR team_away_id = ?1 ORDER BY started DESC")
    @Query("SELECT g FROM Game g WHERE team_home_id = ?1 OR team_away_id = ?1 ORDER BY started DESC")
    List<Game> findTotalPointsLastFiveMatches(int teamId);
}
