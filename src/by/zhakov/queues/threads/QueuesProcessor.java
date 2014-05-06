package by.zhakov.queues.threads;

import org.apache.log4j.Logger;

import java.util.List;

public class QueuesProcessor extends Thread {
    private static Logger log = Logger.getLogger(QueuesProcessor.class);
    private List<CashProcessor> cashes;

    public QueuesProcessor(List<CashProcessor> cashes) {
        this.cashes = cashes;
        this.setDaemon(true);
    }

    @Override
    public void run() {
        while (true){
            CashProcessor maxQueue = cashes.get(0);
            CashProcessor minQueue = cashes.get(0);
            for (CashProcessor cash : cashes){
                if (cash.getSize() > maxQueue.getSize()){
                    maxQueue = cash;
                }
                if (cash.getSize() < minQueue.getSize()){
                    minQueue = cash;
                }
            }
            if (maxQueue.getSize() > minQueue.getSize() + 1){
                minQueue.put(maxQueue.pull());
                log.info(String.format("moved from %s to %s", maxQueue, minQueue));
            }
        }
    }
}
