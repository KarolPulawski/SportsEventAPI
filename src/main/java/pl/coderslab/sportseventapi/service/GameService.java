package pl.coderslab.sportseventapi.service;

import pl.coderslab.sportseventapi.entity.Game;

import java.util.List;

public interface GameService {
    void saveGameToDb(Game game);

    List<Game> getAllActiveGame();

    List<Game> getAllScheduledGames();
}
