package pl.coderslab.sportseventapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.sportseventapi.entity.Game;
import pl.coderslab.sportseventapi.entity.Team;
import pl.coderslab.sportseventapi.service.FakeService;
import pl.coderslab.sportseventapi.service.StatisticService;
import pl.coderslab.sportseventapi.service.JsonService;
import pl.coderslab.sportseventapi.service.impl.GameServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private GameServiceImpl gameServiceImpl;

    @Autowired
    private JsonService jsonService;

    @Autowired
    private StatisticService statisticService;

    @Autowired
    private FakeService fakeService;


    @RequestMapping(method = RequestMethod.GET, path = "/hello")
    @ResponseBody
    public String hello() throws IOException, InterruptedException {
//        for(int i = 0; i < 100; i++) {
//            fakeService.sendScheduledGame();
//            Thread.sleep(1_000L);
//        }
        return "login_page";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/gameWeekSchedule")
    public String gameWeekSchedule() {
        List<Game> games = gameServiceImpl.getAllScheduledGames();

        for(Game g : games) {
            statisticService.generateOdd(g);
        }

        jsonService.createJsonFromGameList(games);
        return jsonService.getJsonGames().toString();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/gameWeekResults")
    public String gameWeekResults() throws IOException {
        List<Game> results = gameServiceImpl.getAllActiveGame();
        jsonService.createJsonFromGameList(results);
        return jsonService.getJsonGames().toString();
    }

//    @RequestMapping(method = RequestMethod.GET, path = "/history")
//    public String history() {
//        List<Game> historyResults = gameServiceImpl.totalPointsLastFiveMatches(1);
//        jsonService.createJsonFromGameList(historyResults);
//    }
}
