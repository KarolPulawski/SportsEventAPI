package pl.coderslab.sportseventapi.service;

import org.springframework.stereotype.Service;
import pl.coderslab.sportseventapi.entity.Game;

import java.util.List;

@Service
public interface GameService {
    void saveGameToDb(Game game);

    List<Game> getAllActiveGame();

    List<Game> getAllScheduledGames();

    List<Game> getLastGameWeekResults();

    List<Game> findAll();

    List<Game> totalPointsLastFiveMatches(int teamId);

    List<Game> totalPointsLastThreeHomeMatches(int teamId);
    List<Game> totalPointsLastThreeAwayMatches(int teamId);
}
