package pl.coderslab.sportseventapi.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import pl.coderslab.sportseventapi.entity.Game;

import java.util.ArrayList;
import java.util.List;

@Service
public class JsonService {

    private List<JSONObject> jsonFromGameList = new ArrayList<>();

    public List<JSONObject> getJsonGames() {
        return jsonFromGameList;
    }

    public void createJsonFromGameList(List<Game> games) {
        jsonFromGameList.clear();
        for(Game g : games) {
            JSONObject jObject = new JSONObject();
            jObject.put("teamHome", g.getTeamHome().getName());
            jObject.put("teamAway", g.getTeamAway().getName());
            jObject.put("started", g.getStarted());
            jObject.put("active", g.getActive());
            jObject.put("history", g.getHistory());
            jObject.put("homeGoal", g.getHomeGoal());
            jObject.put("homeCorner", g.getHomeCorner());
            jObject.put("homeYellow", g.getHomeYellow());
            jObject.put("homeRed", g.getHomeRed());
            jObject.put("homePenalty", g.getHomePenalty());
            jObject.put("homePoint", g.getHomePoint());
            jObject.put("awayGoal", g.getAwayGoal());
            jObject.put("awayCorner", g.getAwayCorner());
            jObject.put("awayYellow", g.getAwayYellow());
            jObject.put("awayRed", g.getAwayRed());
            jObject.put("awayPenalty", g.getAwayPenalty());
            jObject.put("awayPoint", g.getAwayPoint());
            jsonFromGameList.add(jObject);
        }
    }
}