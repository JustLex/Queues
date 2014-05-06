package by.zhakov.queues.model;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private List<Cash> cashes;

    public Restaurant() {
        cashes = new ArrayList<Cash>();
    }

    public int getSize(){
        return cashes.size();
    }

    public Cash getCash(int index){
        return cashes.get(index);
    }

    public void addCash(Cash cash){
        cashes.add(cash);
    }
}
