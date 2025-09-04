package utils;

import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class ChartMetrics {

    public static List<Integer> heights(List<WebElement> bars) {
        List<Integer> out = new ArrayList<>(bars.size());
        for (WebElement bar : bars) {
            try {
                out.add((int) Math.round(Double.parseDouble(bar.getAttribute("height"))));
            } catch (Exception ignored) { out.add(0); }
        }
        return out;
    }

    public static List<Integer> widths(List<WebElement> bars) {
        List<Integer> out = new ArrayList<>(bars.size());
        for (WebElement bar : bars) {
            try {
                out.add((int) Math.round(Double.parseDouble(bar.getAttribute("width"))));
            } catch (Exception ignored) { out.add(0); }
        }
        return out;
    }

    /** Pretty-print all bars with their data-testid + H/W for easy debugging. */
    public static void logBars(List<WebElement> bars) {
        System.out.println("=== Bars ===");
        for (int i = 0; i < bars.size(); i++) {
            WebElement b = bars.get(i);
            String id = b.getAttribute("data-testid");
            String h = b.getAttribute("height");
            String w = b.getAttribute("width");
            System.out.printf("Bar %02d | %s | H=%s W=%s%n", i, id, h, w);
        }
    }
}