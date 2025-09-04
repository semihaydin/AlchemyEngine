package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class NivoChartPage {
    private static final Logger logger = LogManager.getLogger(NivoChartPage.class);
    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final By ALL_BARS =
            By.xpath("//*[name()='rect' and starts-with(@data-testid,'bar.item')]");

    private static final By LAYOUT_CONTROL =
            By.xpath("//span[text()='Base']");

    private static final By HORIZONTAL_OPTION =
            By.xpath("//label[input[@type='radio' and @value='horizontal']]");

    private static final By FIRST_BAR =
            By.xpath("//*[name()='rect' and @data-testid='bar.item.fries.0']");

    private static final By COLOR_SCHEME_CONTROL =
            By.xpath("//*[name()='svg' and @class='css-8mmkcg']");

    private static final By OPTION_XPATH_TMPL =
            By.xpath("//span[text()='Categorical: Accent']");

    private static final By STYLE_CONTROL =
            By.xpath("//span[text()='Style']");

    public NivoChartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
    }

    public NivoChartPage open() {
        logger.info("Opening Nivo chart page at https://nivo.rocks/bar/");
        driver.get("https://nivo.rocks/bar/");
        wait.until(ExpectedConditions.presenceOfElementLocated(ALL_BARS));
        logger.info("Page loaded successfully, chart bars detected");
        return this;
    }

    public void switchLayoutToHorizontal() {
        logger.info("Switching chart layout to horizontal");
        WebElement layout = wait.until(ExpectedConditions.elementToBeClickable(LAYOUT_CONTROL));
        if ("select".equalsIgnoreCase(layout.getTagName())) {
            logger.debug("Using select dropdown for layout change");
            new Select(layout).selectByVisibleText("horizontal");
        } else {
            logger.debug("Using click interaction for layout change");
            layout.click();
            WebElement horizontal = wait.until(ExpectedConditions.elementToBeClickable(HORIZONTAL_OPTION));
            horizontal.click();
        }
        wait.until(d -> !d.findElements(ALL_BARS).isEmpty());
        logger.info("Layout switched to horizontal successfully");
    }
    public String getFirstBarFill() {
        logger.debug("Getting first bar fill color");
        WebElement bar = wait.until(ExpectedConditions.presenceOfElementLocated(FIRST_BAR));
        String fillColor = bar.getAttribute("fill");
        logger.debug("First bar fill color: {}", fillColor);
        return fillColor;
    }

    public void changeColorScheme(String schemeName) {
        logger.info("Changing color scheme to: {}", schemeName);
        driver.findElement(STYLE_CONTROL).click();
        WebElement control = wait.until(ExpectedConditions.elementToBeClickable(COLOR_SCHEME_CONTROL));
        if ("select".equalsIgnoreCase(control.getTagName())) {
            logger.debug("Using select dropdown for color scheme change");
            new Select(control).selectByVisibleText(schemeName);
        } else {
            logger.debug("Using click interaction for color scheme change");
            control.click();
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(OPTION_XPATH_TMPL));
            option.click();
        }
        logger.info("Color scheme changed to: {}", schemeName);
    }

    public void waitForFirstBarFillToChangeFrom(String previousFill) {
        wait.until(d -> {
            try {
                String now = d.findElement(FIRST_BAR).getAttribute("fill");
                return now != null && !now.equals(previousFill);
            } catch (NoSuchElementException e) {
                return false;
            }
        });
    }
}
