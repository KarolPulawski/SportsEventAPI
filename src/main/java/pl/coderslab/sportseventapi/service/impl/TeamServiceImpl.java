package pl.coderslab.sportseventapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import pl.coderslab.sportseventapi.entity.Competition;
import pl.coderslab.sportseventapi.entity.Team;
import pl.coderslab.sportseventapi.repository.TeamRepository;
import pl.coderslab.sportseventapi.service.TeamService;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    private TeamRepository teamRepository;

    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public List<Team> findTeamsByCompetition(Competition competition) {
        return teamRepository.findTeamsByCompetitionsEquals(competition);
    }

    @Override
    public List<Team> findTeamsByCompetitionId(Integer id) {
        return teamRepository.findTeamsByCompetitionId(id);
    }
}
