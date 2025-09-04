package tests;

import base.DriverFactory;
import io.qameta.allure.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.NivoChartPage;
import utils.BarCalculator;
import utils.Orientation;
import utils.OrientationDetector;

import java.time.Duration;
import java.util.List;

public class NivoChartTests {
    private static final Logger logger = LogManager.getLogger(NivoChartTests.class);

        private WebDriver driver;
        private NivoChartPage nivoPage;
    @BeforeClass
        public void setUp() {
           logger.info("Setting up NivoChartTests");
           driver = DriverFactory.create();
        logger.info("Driver created successfully");
        NivoChartPage page = new NivoChartPage(driver).open();
        logger.info("Nivo chart page opened");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//*[name()='rect' and starts-with(@data-testid,'bar.item')]")));
        logger.info("Chart bars detected on page");
        }

        @AfterClass(alwaysRun = true)
        public void tearDown() {
            logger.info("Tearing down NivoChartTests");
            if (driver != null) {
                driver.quit();
                logger.info("Driver closed successfully");
            }
        }

    @Test
    @Epic("Chart Layout")
    @Feature("Layout Switching")
    @Story("Validate chart orientation change from vertical to horizontal")
    @Severity(SeverityLevel.CRITICAL)
    @Description("This test validates that the chart layout changes correctly from vertical to horizontal orientation")
    public void TestLayoutChangeValidation() {
        logger.info("Starting TestLayoutChangeValidation");
        
        Allure.step("Initialize page objects", () -> {
            logger.info("Initializing page objects");
        });
        NivoChartPage nivoPage = new NivoChartPage(driver);
        BarCalculator calc = new BarCalculator(driver);
        
        Allure.step("Measure chart dimensions before layout change", () -> {
            logger.info("Measuring chart dimensions before layout change");
        });
        List<Integer> heightsBefore = calc.getHeights();
        List<Integer> widthsBefore  = calc.getWidths();
        Orientation oBefore = OrientationDetector.detect(widthsBefore, heightsBefore, 2);
        logger.info("Chart orientation before change: {}", oBefore);
        Allure.addAttachment("Orientation Before", oBefore.toString());
        Assert.assertEquals(oBefore, Orientation.VERTICAL);
        
        Allure.step("Switch chart layout to horizontal", () -> {
            logger.info("Switching chart layout to horizontal");
            nivoPage.switchLayoutToHorizontal();
        });
        
        Allure.step("Verify chart orientation changed to horizontal", () -> {
            logger.info("Measuring chart dimensions after layout change");
        });
        List<Integer> heightsAfter = calc.getHeights();
        List<Integer> widthsAfter  = calc.getWidths();
        Orientation oAfter = OrientationDetector.detect(widthsAfter, heightsAfter, 2);
        logger.info("Chart orientation after change: {}", oAfter);
        Allure.addAttachment("Orientation After", oAfter.toString());
        Assert.assertEquals(oAfter, Orientation.HORIZONTAL);
        logger.info("TestLayoutChangeValidation completed successfully");
    }
    }