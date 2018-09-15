package pl.coderslab.sportseventapi.service;

import pl.coderslab.sportseventapi.entity.Competition;

import java.util.List;

public interface CompetitionService {
    List<Competition> findAllCompetitionEnabled();
}
