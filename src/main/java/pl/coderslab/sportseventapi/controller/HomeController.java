package pl.coderslab.sportseventapi.controller;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.sportseventapi.entity.Competition;
import pl.coderslab.sportseventapi.entity.Game;
import pl.coderslab.sportseventapi.entity.Team;
import pl.coderslab.sportseventapi.repository.CompetitionRepository;
import pl.coderslab.sportseventapi.repository.TeamRepository;
import pl.coderslab.sportseventapi.service.FakeService;
import pl.coderslab.sportseventapi.service.HistoryService;
import pl.coderslab.sportseventapi.service.JsonService;
import pl.coderslab.sportseventapi.service.impl.GameServiceImpl;
import pl.coderslab.sportseventapi.service.impl.OddServiceImpl;
import pl.coderslab.sportseventapi.service.impl.TeamServiceImpl;

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
    private HistoryService historyService;


    @RequestMapping(method = RequestMethod.GET, path = "/hello")
    @ResponseBody
    public String hello() {
        Team team = new Team();
        team.setId(1);
        int i = historyService.lastFiveMatchesTotalPoints(team);
        System.out.println("points:" + i);
        return "login_page";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/gameWeekSchedule")
    public String gameWeekSchedule() {
        List<Game> games = gameServiceImpl.getAllScheduledGames();
        jsonService.createJsonFromGameList(games);
        return jsonService.getJsonGames().toString();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/gameWeekResults")
    public String gameWeekResults() {
        List<Game> results = gameServiceImpl.getAllActiveGame();
        jsonService.createJsonFromGameList(results);
        return jsonService.getJsonGames().toString();
    }

//    @RequestMapping(method = RequestMethod.GET, path = "/history")
//    public String history() {
//        List<Game> historyResults = gameServiceImpl.totalPointsLastFiveMatches(1);
//        jsonService.createJsonFromGameList(historyResults);
//
//    }
}
