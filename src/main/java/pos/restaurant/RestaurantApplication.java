package pos.restaurant;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pos.restaurant.service.DishHistoryService;
import pos.restaurant.utils.WeeklyTaskScheduler;

@SpringBootApplication
public class RestaurantApplication {
    private final DishHistoryService dishHistoryService;

    public RestaurantApplication(DishHistoryService dishHistoryService) {
        this.dishHistoryService = dishHistoryService;
    }

//    @PostConstruct
//    public void init() {
//        WeeklyTaskScheduler scheduler = new WeeklyTaskScheduler();
//        scheduler.startWeeklyTask(() -> {
//            System.out.println("Weekly task executed ");
//            dishHistoryService.deleteHistory();
//        });
//    }

    public static void main(String[] args) {
        SpringApplication.run(RestaurantApplication.class, args);
    }

}
