package pl.coderslab.sportseventapi.service;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;

@Service
public class DateService {

    public static Timestamp currentTimeWithDelay(long milisDelay) {
        return new Timestamp(System.currentTimeMillis() + milisDelay);
    }

}
