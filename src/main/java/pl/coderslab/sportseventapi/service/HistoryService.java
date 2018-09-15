package pl.coderslab.sportseventapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.sportseventapi.entity.Game;
import pl.coderslab.sportseventapi.entity.Team;
import pl.coderslab.sportseventapi.service.impl.GameServiceImpl;

import java.util.List;

@Service
public class HistoryService {

    public int number = 5;

    @Autowired
    private GameService gameServiceImpl;

    public int lastFiveMatchesTotalPoints(Team team) {
        List<Game> games = gameServiceImpl.totalPointsLastFiveMatches(team.getId());
        int counter = 1;
        int totalPoints = 0;

        for (Game g : games) {
            if (g.getTeamHome().getId() == team.getId()) {
                totalPoints += g.getHomePoint();
                counter++;
            } else if (g.getTeamAway().getId() == team.getId()) {
                totalPoints += g.getAwayPoint();
                counter++;
            }
            if (counter == 5) break;
        }
        return totalPoints;
    }
}
