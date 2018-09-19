package pl.coderslab.sportseventapi.service;

import com.github.javafaker.Faker;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
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

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class FakeService {

    private Faker faker;

    private final String URL_SERVER_SCHEDULED = "http://localhost:8081/api/game";
    private final String URL_SERVER_RESULT = "http://localhost:8081/api/result";

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

    @Autowired
    private JsonService jsonService;

    private List<Competition> competitions;

    public FakeService() {
        this.faker = new Faker();
    }

    public Game createGame(Team teamHome, Team teamAway) throws ParseException {
        Game game = new Game();
        game.setTeamHome(teamHome);
        game.setTeamAway(teamAway);
        game.setActive(false);
        game.setHistory(false);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = dateFormat.format(new Timestamp(System.currentTimeMillis() + 5000));
        Date date = dateFormat.parse(dateString);
        Timestamp timestamp = new Timestamp(date.getTime());

        game.setStarted(timestamp);

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

    public List<Game> generateGameWeekLeagueSchedule(Competition competition) throws ParseException {

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
            g.setScheduled(true);
            g.setActive(false);
            g.setHistory(false);
            g.setFinished(true);
            gameServiceImpl.saveGameToDb(g);
        }
        return results;
    }

    @Scheduled(fixedDelay = 15_000L)
    public void runGameWeek() throws ParseException {
        this.competitions = competitionServiceImpl.findAllCompetitionEnabled();
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

    private void sendGameToServer(Game g, String url) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        jsonService.createJsonFromGame(g);
        String json = jsonService.getJsonFromGame().toString();
        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        CloseableHttpResponse response = client.execute(httpPost);
        client.close();
    }

    private List<Game> sendScheduledGame() throws IOException, ParseException {
        List<Game> weekGames = new ArrayList<>();
        this.competitions = competitionServiceImpl.findAllCompetitionEnabled();
        for(Competition competition : this.competitions) {
            weekGames = generateGameWeekLeagueSchedule(competition);
            for(Game g : weekGames) {
                g.setScheduled(true);
                sendGameToServer(g, URL_SERVER_SCHEDULED);
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return weekGames;
    }

    private void sendResultGame(List<Game> currentGame) throws IOException {
        List<Game> resultGames = generateGameWeekResults(currentGame);
        for(Game g : resultGames) {
            g.setFinished(true);
            sendGameToServer(g, URL_SERVER_RESULT);
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Scheduled(fixedDelay = 500L)
    public void runGames() throws IOException, ParseException {
        List<Game> currentGames = sendScheduledGame();
        try {
            Thread.sleep(15_000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sendResultGame(currentGames);
    }
}
