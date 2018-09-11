package pl.coderslab.sportseventapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.coderslab.sportseventapi.entity.Competition;
import pl.coderslab.sportseventapi.entity.Game;
import pl.coderslab.sportseventapi.entity.Team;
import pl.coderslab.sportseventapi.repository.TeamRepository;
import pl.coderslab.sportseventapi.service.FakeService;
import pl.coderslab.sportseventapi.service.impl.GameServiceImpl;
import pl.coderslab.sportseventapi.service.impl.TeamServiceImpl;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private TeamServiceImpl teamServiceImpl;

    @Autowired
    private GameServiceImpl gameServiceImpl;

    @RequestMapping(method = RequestMethod.GET, path = "/hello")
    @ResponseBody
    public String hello() {
        Competition competition = new Competition();
        competition.setId(1);
        competition.setName("Premier League");
        FakeService fs = new FakeService(teamServiceImpl, gameServiceImpl);
        fs.runGameWeek(competition);
        return "login_page";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/gameWeekSchedule")
    @ResponseBody
    public String gameWeekSchedule() {



//        FakeService fs = new FakeService(teamServiceImpl, gameServiceImpl);
//        fs.generateGameWeekLeagueSchedule(1);


//        List<Game> games = gameServiceImpl.getAllScheduledGames();
//        StringBuilder sb = new StringBuilder();
//
//        for(Game g : games) {
//            sb.append(g.toString()).append(" | ");
//        }

        // RETURN GAME WHICH ARE SCHEDULED
        List<Game> games = gameServiceImpl.getAllScheduledGames();
        StringBuilder sb = new StringBuilder();
        for(Game g : games) {
            sb.append(g.toString()).append(" | ");
        }

        return sb.toString();
    }
}
