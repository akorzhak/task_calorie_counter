package ua.kpi.tef.util;

import ua.kpi.tef.model.UserMeal;
import ua.kpi.tef.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {

    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 400),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        List<UserMealWithExceed> mealWithExceed =
                getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(20,0), 2000);
        for (UserMealWithExceed userMeal : mealWithExceed)
            System.out.println(userMeal.toString());
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime,
                                                                    LocalTime endTime, int caloriesPerDay) {

        int calories = 0;
        UserMeal nextUserMeal;
        ListIterator<UserMeal> it = mealList.listIterator(1);
        List<UserMealWithExceed> userMealWithExceed = new ArrayList<UserMealWithExceed>();
        List<UserMeal> dayMealList = new ArrayList<UserMeal>();
        boolean exceed = false;

        for (UserMeal userMeal : mealList) {

            LocalTime mealTime = userMeal.getDateTime().toLocalTime();

            if (mealTime.compareTo(startTime) >= 0 && mealTime.compareTo(endTime) <= 0) {

                calories += userMeal.getCalories();
                dayMealList.add(userMeal);

                if (it.hasNext()) {

                    nextUserMeal = it.next();
                    LocalDate mealDate = userMeal.getDateTime().toLocalDate();
                    LocalDate nextMealDate = nextUserMeal.getDateTime().toLocalDate();

                    if (mealDate.compareTo(nextMealDate) < 0) {

                        if (calories > caloriesPerDay)
                            exceed = true;
                        for (UserMeal dayMeal : dayMealList) {
                            userMealWithExceed.add(new UserMealWithExceed(dayMeal.getDateTime(),
                                    dayMeal.getDescription(), dayMeal.getCalories(), exceed));
                        }
                        dayMealList.clear();
                    }
                } else {

                    if (calories > caloriesPerDay)
                        exceed = true;
                    for (UserMeal dayMeal : dayMealList) {
                        userMealWithExceed.add(new UserMealWithExceed(dayMeal.getDateTime(), dayMeal.getDescription(),
                                dayMeal.getCalories(), exceed));
                    }
                    dayMealList.clear();
                }
            }
        }
        return userMealWithExceed;
    }
}
