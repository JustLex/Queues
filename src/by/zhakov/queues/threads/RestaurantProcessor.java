package by.zhakov.queues.threads;

import by.zhakov.queues.bl.Statistics;
import by.zhakov.queues.model.Cash;
import by.zhakov.queues.model.Client;
import by.zhakov.queues.model.Restaurant;
import by.zhakov.queues.settings.Settings;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

public class RestaurantProcessor extends Thread{
    private static Logger log = Logger.getLogger(CashProcessor.class);
    private BlockingQueue<Client> clients;
    private List<CashProcessor> cashes;

    public RestaurantProcessor(Restaurant restaurant) {
        this.cashes = new ArrayList<CashProcessor>();
        clients = new LinkedBlockingDeque<Client>();
        ThreadGroup minGroup = new ThreadGroup("min thread");
        for (int i = 0; i < Settings.minCashesCount; i++){
            String name = "min cash " + i;
            CashProcessor newCash = new CashProcessor(minGroup, name, restaurant.getCash(i));
            newCash.setPriority(MIN_PRIORITY);
            this.cashes.add(newCash);
        }
        ThreadGroup normGroup = new ThreadGroup("norm thread");
        for (int i = Settings.minCashesCount; i < Settings.minCashesCount + Settings.normalCashesCount; i++){
            String name = "norm cash " + i;
            CashProcessor newCash = new CashProcessor(normGroup, name, restaurant.getCash(i));
            newCash.setPriority(NORM_PRIORITY);
            this.cashes.add(newCash);
        }
        ThreadGroup maxGroup = new ThreadGroup("max thread");
        for (int i = Settings.minCashesCount + Settings.normalCashesCount; i < restaurant.getSize(); i++){
            String name = "max cash " + i;
            CashProcessor newCash = new CashProcessor(maxGroup, name, restaurant.getCash(i));
            newCash.setPriority(MAX_PRIORITY);
            this.cashes.add(newCash);
        }
    }

    public void putClient(Client client){
        clients.add(client);
    }

    @Override
    public void run() {
        for (CashProcessor cash : cashes){
            cash.start();
        }
        QueuesProcessor queuesProcessor = new QueuesProcessor(cashes);
        queuesProcessor.start();
        Iterator<CashProcessor> iterator = cashes.iterator();
        while (true){
            try{
                Client client = clients.poll(Settings.timeout, TimeUnit.SECONDS);
                if (client == null){
                    TimeUnit.SECONDS.sleep(Settings.timeout);
                    for (CashProcessor cash : cashes){
                        Statistics statistics = cash.getStatistics();
                        log.info(String.format("%s served %d in %d", cash.toString(), statistics.getServed(),
                                statistics.getServeTime()));
                    }
                    break;
                }
                if (!iterator.hasNext()){
                    iterator = cashes.iterator();
                }
                iterator.next().put(client);
            } catch (InterruptedException ie){
                log.error("Interrupted on restaurant run", ie);
            }
        }
    }
}
