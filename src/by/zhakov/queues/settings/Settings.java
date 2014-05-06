package by.zhakov.queues.settings;

import java.util.ResourceBundle;

public class Settings {
    private static ResourceBundle settings = ResourceBundle.getBundle("queues");

    public static int clientsQuantity = Integer.parseInt(settings.getString("Clients.quantity"));
    public static int timeout = Integer.parseInt(settings.getString("Queues.timeout"));
    public static int minCashesCount = Integer.parseInt(settings.getString("Cashes.minCashes"));
    public static int normalCashesCount = Integer.parseInt(settings.getString("Cashes.normalCashes"));
    public static int maxCashesCount = Integer.parseInt(settings.getString("Cashes.maxCashes"));
    public static boolean ignoreServeTime = Boolean.parseBoolean(settings.getString("Cashes.ignoreServeTime"));
}
