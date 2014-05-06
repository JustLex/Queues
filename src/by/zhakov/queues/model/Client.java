package by.zhakov.queues.model;

import by.zhakov.queues.localization.Strings;

public class Client {
    private int orderWeight;

    public Client(int orderWeight) {
        this.orderWeight = orderWeight;
    }

    public Client() {
        this.orderWeight = (int)(Math.random()*10000);
    }

    public int getOrderWeight() {
        return orderWeight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;

        Client client = (Client) o;

        if (orderWeight != client.orderWeight) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return orderWeight;
    }

    @Override
    public String toString() {
        return Strings.clientName + Strings.separator + Strings.clientWeight + Strings.separator +
                + orderWeight + Strings.separator;
    }
}
