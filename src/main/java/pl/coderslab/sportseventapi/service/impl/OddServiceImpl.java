package pl.coderslab.sportseventapi.service.impl;

import org.springframework.stereotype.Service;
import pl.coderslab.sportseventapi.entity.Odd;
import pl.coderslab.sportseventapi.repository.OddRepository;
import pl.coderslab.sportseventapi.service.OddService;

@Service
public class OddServiceImpl implements OddService {

    private OddRepository oddRepository;

    public OddServiceImpl(OddRepository oddRepository) {
        this.oddRepository = oddRepository;
    }

    @Override
    public Odd saveOdd(Odd odd) {
        return oddRepository.save(odd);
    }
}
