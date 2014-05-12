package by.zhakov.queues.threads;

import by.zhakov.queues.bl.Statistics;
import by.zhakov.queues.model.Cash;
import by.zhakov.queues.model.Client;
import by.zhakov.queues.settings.Settings;
import org.apache.log4j.Logger;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class CashProcessor extends Thread{
    private static Logger log = Logger.getLogger(CashProcessor.class);
    private Cash cash;
    private LinkedBlockingDeque<Client> queue;
    private Statistics statistics;

    public CashProcessor(Cash cash) {
        statistics = new Statistics();
        this.cash = cash;
        queue = new LinkedBlockingDeque<Client>();
    }

    public CashProcessor(ThreadGroup thGroup, String name, Cash cash){
        super(thGroup, name);
        statistics = new Statistics();
        this.cash = cash;
        queue = new LinkedBlockingDeque<Client>();
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public int getSize(){
        return queue.size();
    }

    public void put(Client client){
        try{
            queue.put(client);
        } catch (InterruptedException ie){
            log.error("Interrupted on cash put", ie);
        }
    }

    public Client pull(){
        try{
            return queue.takeLast();
        } catch (InterruptedException ie){
            log.error("Interrupted on cash put", ie);
        }
        return null;
    }

    @Override
    public void run() {
        while(true){
            try{
                Client client = queue.pollFirst(Settings.timeout, TimeUnit.SECONDS);
                if (client == null){
                    break;
                }
                int serveTime = Settings.ignoreServeTime ? 0 : client.getOrderWeight()/cash.getPower();
                TimeUnit.MILLISECONDS.sleep(serveTime);
                statistics.putServed(serveTime);
                log.info(String.format("%s served %s in %d", cash.toString(), client.toString(), serveTime));
            } catch (InterruptedException ie){
                log.error("Interrupted on cash run", ie);
            }
        }
    }
}
