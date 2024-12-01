package pos.restaurant.utils;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WeeklyTaskScheduler {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void startWeeklyTask(Runnable task) {
        long initialDelay = 0; // Uruchom natychmiast
        long period = TimeUnit.DAYS.toMillis(7);

        scheduler.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.MILLISECONDS);
    }
}
