package pl.coderslab.sportseventapi.service;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.coderslab.sportseventapi.entity.Competition;
import pl.coderslab.sportseventapi.entity.Game;
import pl.coderslab.sportseventapi.entity.Odd;
import pl.coderslab.sportseventapi.entity.Team;
import pl.coderslab.sportseventapi.service.impl.CompetitionServiceImpl;
import pl.coderslab.sportseventapi.service.impl.GameServiceImpl;
import pl.coderslab.sportseventapi.service.impl.OddServiceImpl;
import pl.coderslab.sportseventapi.service.impl.TeamServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class FakeService {

    private Faker faker;

    @Autowired
    private TeamServiceImpl teamServiceImpl;

    @Autowired
    private OddServiceImpl oddServiceImpl;

    @Autowired
    private GameServiceImpl gameServiceImpl;

    @Autowired
    private StatisticService statisticService;

    @Autowired
    private CompetitionServiceImpl competitionServiceImpl;

    private List<Competition> competitions;

    public FakeService() {
        this.faker = new Faker();
    }

    public Game createGame(Team teamHome, Team teamAway) {
        Game game = new Game();
        game.setTeamHome(teamHome);
        game.setTeamAway(teamAway);
        game.setActive(false);
        game.setHistory(false);
        game.setStarted(DateService.currentTimeWithDelay(5000));

        Odd odd = statisticService.generateOdd(game);
        game.setOdd(odd);
        oddServiceImpl.saveOdd(odd);
        return game;
    }

    public Game generateGame(Game createdGame) {
        createdGame.setAwayCorner(faker.number().numberBetween(0,10));
        createdGame.setHomeCorner(faker.number().numberBetween(0,10));
        
        createdGame.setAwayGoal(faker.number().numberBetween(0,5));
        createdGame.setHomeGoal(faker.number().numberBetween(0,5));
        
        createdGame.setAwayPenalty(faker.number().numberBetween(0,2));
        createdGame.setHomePenalty(faker.number().numberBetween(0,2));
        
        createdGame.setAwayYellow(faker.number().numberBetween(0,7));
        createdGame.setHomeYellow(faker.number().numberBetween(0,7));
        createdGame.setAwayRed(faker.number().numberBetween(0,7));
        createdGame.setHomeRed(faker.number().numberBetween(0,7));
    
        if(createdGame.getHomeGoal() == createdGame.getAwayGoal()) {
            createdGame.setHomePoint(1);
            createdGame.setAwayPoint(1);
        } else if (createdGame.getHomeGoal() > createdGame.getAwayGoal()) {
            createdGame.setHomePoint(3);
            createdGame.setAwayPoint(0);
        } else {
            createdGame.setHomePoint(0);
            createdGame.setAwayPoint(3);
        }

        return createdGame;
    }

    public List<Game> generateGameWeekLeagueSchedule(Competition competition) {

        List<Team> teams = teamServiceImpl.findTeamsByCompetitionId(competition.getId());

        Collections.shuffle(teams);

        List<Game> games = new ArrayList<>();
        for(int i = 0; i < teams.size()/2; i++) {
            Game game = createGame(teams.get(i*2), teams.get(i*2+1));
            game.setCompetition(competition);
            games.add(game);
            gameServiceImpl.saveGameToDb(game);
        }
        saveGameWeekToDb(games);
        return games;
    }

    public void saveGameWeekToDb(List<Game> gameToArchieve) {
        for(Game game : gameToArchieve) {
            gameServiceImpl.saveGameToDb(game);
        }
    }

    public void saveGameWeekHistoryToDb(List<Game> gameToArchieve) {
        for(Game game : gameToArchieve) {
            game.setHistory(true);
            game.setActive(false);
            gameServiceImpl.saveGameToDb(game);
        }
    }
    
    public List<Game> generateGameWeekResults(List<Game> generateGameWeekLeagueSchedule) {
        List<Game> results = new ArrayList<>();
        for(Game g : generateGameWeekLeagueSchedule) {
            results.add(generateGame(g));
        }

        for(Game g : results) {
            System.out.println(g.toString());
            g.setActive(true);
            g.setHistory(false);
            gameServiceImpl.saveGameToDb(g);
        }
        return results;
    }

//    @Scheduled(cron = "0 * * * * ?")
    @Scheduled(fixedDelay = 15_000L)
    public void runGameWeek() {
        this.competitions = competitionServiceImpl.findAllCompetitionEnabled();
        this.competitions.forEach(v -> System.out.println(v.getName()));
        for(Competition competition : this.competitions) {
            List<Game> weekGames = generateGameWeekLeagueSchedule(competition);
            try {
                Thread.sleep(10_000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            generateGameWeekResults(weekGames);
            try {
                Thread.sleep(10_000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            saveGameWeekHistoryToDb(weekGames);
        }
    }


    public void generateOdds(List<Game> games) {
        for(Game g : games) {

        }
    }
}
