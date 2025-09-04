package tests;

import base.DriverFactory;
import io.qameta.allure.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.NivoChartPage;

import java.time.Duration;

public class NivoColorSchemeTests {
    private static final Logger logger = LogManager.getLogger(NivoColorSchemeTests.class);

    private WebDriver driver;
    private NivoChartPage page;
    private WebDriverWait wait;

    @BeforeClass(alwaysRun = true)
    public void setUpClass() {
        logger.info("Setting up NivoColorSchemeTests");
        driver = DriverFactory.create();
        logger.info("Driver created successfully");
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        logger.info("Test setup completed");
    }

    @BeforeMethod(alwaysRun = true)
    public void openPage() {
        logger.info("Opening Nivo chart page for test");
        page = new NivoChartPage(driver).open();

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[name()='rect' and starts-with(@data-testid,'bar.item')]")
        ));
        logger.info("Chart bars detected, page ready for testing");
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        logger.info("Tearing down NivoColorSchemeTests");
        if (driver != null) {
            driver.quit();
            logger.info("Driver closed successfully");
        }
    }

    @DataProvider(name = "schemes")
    public Object[][] schemes() {
        return new Object[][]{
                {"nivo"},
                {"accent"}
        };
    }

    @Test(dataProvider = "schemes")
    @Epic("Chart Styling")
    @Feature("Color Schemes")
    @Story("Validate color scheme changes affect chart appearance")
    @Severity(SeverityLevel.NORMAL)
    @Description("This test validates that changing color schemes updates the chart's visual appearance")
    public void TestColorSchemeChange(String scheme) {
        logger.info("Testing color scheme change for: {}", scheme);
        
        Allure.step("Get initial bar fill color", () -> {
            logger.info("Getting initial bar fill color");
        });
        String before = page.getFirstBarFill();
        logger.info("Initial bar fill color: {}", before);
        Allure.addAttachment("Initial Color", before);

        Allure.step("Change color scheme to " + scheme, () -> {
            page.changeColorScheme(scheme);
            logger.info("Changed color scheme to: {}", scheme);
        });
        
        Allure.step("Wait for color change and verify", () -> {
            page.waitForFirstBarFillToChangeFrom(before);
        });

        String after = page.getFirstBarFill();
        logger.info("Bar fill color after scheme change: {}", after);
        Allure.addAttachment("Final Color", after);

        Assert.assertNotEquals(after, before,
                "First bar fill should change after selecting scheme: " + scheme);
        logger.info("Color scheme test completed successfully for: {}", scheme);
    }
}