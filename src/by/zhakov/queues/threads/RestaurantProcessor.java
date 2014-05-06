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
        for (int i = 0; i < restaurant.getSize(); i++){
            CashProcessor newCash = new CashProcessor(restaurant.getCash(i));
            this.cashes.add(newCash);
        }
    }

    public void putClient(Client client){
        clients.add(client);
    }

    @Override
    public void run() {
        ExecutorService exec = Executors.newFixedThreadPool(cashes.size());
        for (CashProcessor cash : cashes){
            exec.execute(cash);
        }
        exec.shutdown();
        QueuesProcessor queuesProcessor = new QueuesProcessor(cashes);
        queuesProcessor.start();
        Iterator<CashProcessor> iterator = cashes.iterator();
        while (true){
            try{
                Client client = clients.poll(Settings.timeout, TimeUnit.SECONDS);
                if (client == null){
                    exec.awaitTermination(Settings.timeout, TimeUnit.SECONDS);
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
