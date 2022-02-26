package me.snow.cash.utils;

import me.snow.cash.BoxCash;
import org.bukkit.configuration.file.FileConfiguration;

import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Formatador {

    private static BoxCash main = (BoxCash)BoxCash.getPlugin(BoxCash.class);

    private static final Pattern PATTERN = Pattern.compile("^(\\d+\\.?\\d*)(\\D+)");

    private static FileConfiguration config = BoxCash.plugin.getConfig();

    private static List<String> suffixes = config.getStringList("Formatação");

    public static void changeSuffixes(List<String> suffixes) {
        Formatador.suffixes = suffixes;
    }

    public static String formatNumber(double value) {
        int index = 0;
        double tmp;
        while ((tmp = value / 1000.0D) >= 1.0D) {
            value = tmp;
            index++;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        return String.valueOf(decimalFormat.format(value)) + (String)suffixes.get(index);
    }

    public static double parseString(String value) throws Exception {
        try {
            return Double.parseDouble(value);
        } catch (Exception exception) {
            Matcher matcher = PATTERN.matcher(value);
            if (!matcher.find())
                throw new Exception("Invalid format");
            double amount = Double.parseDouble(matcher.group(1));
            String suffix = matcher.group(2);
            int index = suffixes.indexOf(suffix.toUpperCase());
            return amount * Math.pow(1000.0D, index);
        }
    }
}
