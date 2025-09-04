package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BarCalculator {

    private final WebDriverWait wait;

    private static final By ALL_BARS =
            By.xpath("//*[name()='rect' and starts-with(@data-testid,'bar.item')]");

    public BarCalculator(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public List<Integer> getHeights() {
        List<WebElement> bars = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(ALL_BARS));
        List<Integer> heights = new ArrayList<>();
        for (WebElement bar : bars) {
            try {
                heights.add((int) Math.round(Double.parseDouble(Objects.requireNonNull(bar.getAttribute("height")))));
            } catch (Exception e) {
                heights.add(0);
            }
        }
        return heights;
    }

    public List<Integer> getWidths() {
        List<WebElement> bars = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(ALL_BARS));
        List<Integer> widths = new ArrayList<>();
        for (WebElement bar : bars) {
            try {
                widths.add((int) Math.round(Double.parseDouble(Objects.requireNonNull(bar.getAttribute("width")))));
            } catch (Exception e) {
                widths.add(0);
            }
        }
        return widths;
    }
}

