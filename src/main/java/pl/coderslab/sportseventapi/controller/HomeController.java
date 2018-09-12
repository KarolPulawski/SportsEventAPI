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
    private TeamServiceImpl teamServiceImpl;

    @Autowired
    private GameServiceImpl gameServiceImpl;

    @Autowired
    private OddServiceImpl oddServiceImpl;

    @Autowired
    private JsonService jsonService;

    @Autowired
    private CompetitionRepository cr;

    @Autowired
    private FakeService fakeService;

    @RequestMapping(method = RequestMethod.GET, path = "/hello")
    @ResponseBody
    public String hello() {
        Competition competition = new Competition();
        competition.setId(1);
        competition.setName("Premier League");
        fakeService.runGameWeek(competition);
        return "login_page";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/gameWeekSchedule")
    public String gameWeekSchedule(Model model) {

//        FakeService fs = new FakeService(teamServiceImpl, gameServiceImpl, oddServiceImpl);
        Competition competition = new Competition();
        competition.setId(1);
        competition.setName("Premier League");
        fakeService.generateGameWeekLeagueSchedule(competition);

        // RETURN GAME WHICH ARE SCHEDULED
        List<Game> games = gameServiceImpl.getAllScheduledGames();

        jsonService.createJsonFromGameList(games);
        return jsonService.getJsonGames().toString();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/gameWeekResults")
    public String gameWeekResults(HttpServletRequest request) {

        List<Game> games = gameServiceImpl.getAllScheduledGames();

//        FakeService fs = new FakeService(teamServiceImpl, gameServiceImpl, oddServiceImpl);
        List<Game> results = fakeService.generateGameWeekResults(games);

        List<Competition> competitions = cr.findAll();
        for(Competition c : competitions) {
            System.out.println(c.getName());
        }


        jsonService.createJsonFromGameList(results);
        return jsonService.getJsonGames().toString();
    }
}
