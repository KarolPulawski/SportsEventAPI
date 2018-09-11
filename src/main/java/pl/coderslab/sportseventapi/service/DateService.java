package pl.coderslab.sportseventapi.service;

import java.sql.Timestamp;
import java.util.Calendar;

public class DateService {

    public static Timestamp currentTimeWithDelay(long milisDelay) {
        return new Timestamp(System.currentTimeMillis() + milisDelay);
    }

}
