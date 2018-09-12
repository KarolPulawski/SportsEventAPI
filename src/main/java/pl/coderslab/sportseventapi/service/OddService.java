package pl.coderslab.sportseventapi.service;

import org.springframework.stereotype.Service;
import pl.coderslab.sportseventapi.entity.Odd;

@Service
public interface OddService {

    Odd saveOdd(Odd odd);
}
