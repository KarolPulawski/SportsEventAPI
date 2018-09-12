package pl.coderslab.sportseventapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.coderslab.sportseventapi.entity.Competition;
import pl.coderslab.sportseventapi.entity.Team;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {
//    List<Team> findTeamsByCompetitions(Competition competition);
    List<Team> findTeamsByCompetitionsEquals(Competition competition);

    @Query(value = "SELECT * FROM teams " +
            "JOIN teams_competitions tc on teams.id = tc.team_id " +
            "WHERE competitions_id = ?1", nativeQuery = true)
    List<Team> findTeamsByCompetitionId(Integer id);
}
