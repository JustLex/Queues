package by.zhakov.queues.logic;

import by.zhakov.queues.model.Cash;
import by.zhakov.queues.model.Client;
import by.zhakov.queues.model.Restaurant;
import by.zhakov.queues.settings.Settings;
import by.zhakov.queues.threads.RestaurantProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class SimulationController {
    public static void start(){
        int cashesCount = Settings.maxCashesCount + Settings.normalCashesCount + Settings.minCashesCount;
        Restaurant restaurant = new Restaurant();
        for (int i = 0; i < cashesCount; i++){
            restaurant.addCash(new Cash());
        }
        RestaurantProcessor restProc = new RestaurantProcessor(restaurant);
        restProc.start();
        for (int i = 0; i < Settings.clientsQuantity; i++){
            Client newClient = new Client();
            restProc.putClient(newClient);
        }
    }
}
