package pl.coderslab.sportseventapi.service;

import org.springframework.stereotype.Service;
import pl.coderslab.sportseventapi.entity.Competition;

import java.util.List;

@Service
public interface CompetitionService {
    List<Competition> findAllCompetitionEnabled();
}
