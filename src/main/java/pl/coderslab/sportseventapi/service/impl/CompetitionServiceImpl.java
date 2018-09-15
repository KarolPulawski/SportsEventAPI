package pl.coderslab.sportseventapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.sportseventapi.entity.Competition;
import pl.coderslab.sportseventapi.repository.CompetitionRepository;
import pl.coderslab.sportseventapi.service.CompetitionService;

import java.util.List;

@Service
public class CompetitionServiceImpl implements CompetitionService {

    @Autowired
    private CompetitionRepository competitionRepository;

    @Override
    public List<Competition> findAllCompetitionEnabled() {
        return competitionRepository.findAllByEnabled();
    }
}
