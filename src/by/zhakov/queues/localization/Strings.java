package by.zhakov.queues.localization;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Aleksei on 04.05.2014.
 */
public class Strings {
    private static final ResourceBundle strings = ResourceBundle.getBundle("by.zhakov.queues.localization.model",
            Locale.getDefault());

    public static String clientName = strings.getString("Client.name");
    public static String clientWeight = strings.getString("Client.weight");
    public static String cashName = strings.getString("Cash.name");
    public static String cashPower = strings.getString("Cash.power");
    public static String separator = strings.getString("Model.separator");
}
