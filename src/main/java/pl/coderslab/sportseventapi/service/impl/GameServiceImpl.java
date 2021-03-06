package pl.coderslab.sportseventapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.sportseventapi.entity.Game;
import pl.coderslab.sportseventapi.repository.GameRepository;
import pl.coderslab.sportseventapi.service.GameService;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

//    public GameServiceImpl(GameRepository gameRepository) {
//        this.gameRepository = gameRepository;
//    }

    @Override
    public void saveGameToDb(Game game) {
        gameRepository.save(game);
    }

    @Override
    public List<Game> getAllActiveGame() {
        return gameRepository.findAllByActiveTrueAndHistoryFalse();
    }

    @Override
    public List<Game> getAllScheduledGames() {
        return gameRepository.findAllByActiveFalseAndHistoryFalse();
    }

    @Override
    public List<Game> getLastGameWeekResults() {
        return null;
    }

    @Override
    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    @Override
    public List<Game> totalPointsLastFiveMatches(int teamId) {
        return gameRepository.findTotalPointsLastFiveMatches(teamId);
    }

    @Override
    public List<Game> totalPointsLastThreeHomeMatches(int teamId) {
        return gameRepository.findTotalPointsLastThreeMatchesHome(teamId);
    }

    @Override
    public List<Game> totalPointsLastThreeAwayMatches(int teamId) {
        return gameRepository.findTotalPointsLastThreeMatchesAway(teamId);
    }
}
