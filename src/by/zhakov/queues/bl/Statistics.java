package by.zhakov.queues.bl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleksei on 06.05.2014.
 */
public class Statistics {
    private int served;
    private int serveTime;

    public int getServed() {
        return served;
    }

    public int getServeTime() {
        return serveTime;
    }

    public Statistics() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Statistics)) return false;

        Statistics that = (Statistics) o;

        if (serveTime != that.serveTime) return false;
        if (served != that.served) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = served;
        result = 31 * result + serveTime;
        return result;
    }

    public void putServed(int serveTime){
        served++;
        this.serveTime += serveTime;
    }
}
