package pl.coderslab.sportseventapi.service.impl;

import org.springframework.stereotype.Service;
import pl.coderslab.sportseventapi.entity.Game;
import pl.coderslab.sportseventapi.repository.GameRepository;
import pl.coderslab.sportseventapi.service.GameService;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    private GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public void saveGameToDb(Game game) {
        gameRepository.save(game);
    }

    @Override
    public List<Game> getAllActiveGame() {
        return gameRepository.findAllByActiveTrue();
    }

    @Override
    public List<Game> getAllScheduledGames() {
        return gameRepository.findAllByActiveFalseAndHistoryFalse();
    }
}
