package pl.coderslab.sportseventapi.service;

import org.springframework.stereotype.Service;
import pl.coderslab.sportseventapi.entity.Competition;
import pl.coderslab.sportseventapi.entity.Team;

import java.util.List;

@Service
public interface TeamService {
    List<Team> findTeamsByCompetition(Competition competition);

    List<Team> findTeamsByCompetitionId(Integer id);
}
