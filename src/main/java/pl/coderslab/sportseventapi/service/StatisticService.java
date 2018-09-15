package pl.coderslab.sportseventapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.sportseventapi.entity.Game;
import pl.coderslab.sportseventapi.entity.Team;
import pl.coderslab.sportseventapi.service.impl.GameServiceImpl;

import java.util.List;

@Service
public class StatisticService {

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

    public int lastThreeMatchesHomeTotalPoints(Team team) {
        List<Game> games = gameServiceImpl.totalPointsLastThreeHomeMatches(team.getId());
        int totalPoints = 0;

        for(int i = 0; i < 3; i++) {
            totalPoints += games.get(i).getHomePoint();
        }
        return totalPoints;
    }

    public int lastThreeMatchesAwayTotalPoints(Team team) {
        int totalPoints = 0;
        List<Game> games = gameServiceImpl.totalPointsLastThreeAwayMatches(team.getId());
        for(int i = 0; i < 3; i++) {
            totalPoints += games.get(i).getAwayPoint();
        }
        return totalPoints;
    }
}
