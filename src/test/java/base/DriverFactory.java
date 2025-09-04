package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DriverFactory {

    public static WebDriver create() {
        String remote = System.getenv("REMOTE_URL"); // e.g. http://selenium:4444/wd/hub
        boolean headless = Boolean.parseBoolean(
                Optional.ofNullable(System.getenv("CHROME_HEADLESS")).orElse("true")
        );

        ChromeOptions options = new ChromeOptions();
        if (headless) {
            List<String> args = new ArrayList<>(List.of(
                    "--headless=new",
                    "--no-sandbox",
                    "--disable-dev-shm-usage",
                    "--disable-gpu",
                    "--window-size=1920,1080"
            ));
            options.addArguments(args);
        }

        if (remote != null && !remote.isBlank()) {
            try {
                return new RemoteWebDriver(URI.create(remote).toURL(), options);
            } catch (MalformedURLException e) {
                throw new RuntimeException("Invalid REMOTE_URL: " + remote, e);
            }
        }

        // Local Chrome for non-Docker runs
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver(options);
    }
}