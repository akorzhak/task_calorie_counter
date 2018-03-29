package ua.kpi.tef.model;

import java.time.LocalDateTime;

/**
 * GKislin
 * 11.01.2015.
 */
public class UserMealWithExceed {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean exceed;

    public UserMealWithExceed(LocalDateTime dateTime, String description, int calories, boolean exceed) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
    }

    @Override
    public String toString() {
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_GREEN = "\u001B[32m";

        String string =  "date: " + dateTime.toLocalDate().toString() + "\ttime: "
                + dateTime.toLocalTime().toString() + "\tcalories: " + calories
                + "\t" + description
                + (exceed ? ANSI_RED + "\tTOO MUCH FOOD TODAY!!!" : ANSI_GREEN + "\tGREAT JOB!")
                + ANSI_RESET;
        return string;
    }
}
