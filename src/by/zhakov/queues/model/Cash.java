package by.zhakov.queues.model;

import by.zhakov.queues.localization.Strings;

public class Cash {
    private int power;

    public Cash(int power) {
        this.power = power;
    }

    public Cash() {
        this.power = (int)(Math.random()*100 + 10);
    }

    public int getPower() {
        return power;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cash)) return false;

        Cash cash = (Cash) o;

        if (power != cash.power) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return power;
    }

    @Override
    public String toString() {
        return Strings.cashName + Strings.separator + Strings.cashPower + Strings.separator +
                + power + Strings.separator;
    }
}
