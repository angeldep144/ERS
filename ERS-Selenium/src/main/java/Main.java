import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import test.Ers;

import java.time.Duration;

public class Main {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "/Users/angeldepena/Downloads/chromedriver");
        WebDriver driver = new ChromeDriver();

        driver.get("http://localhost:9000");

        Ers test = new Ers(driver);

        test.login("kchilds", "password");

        test.submitReimbursement(132.28);

    }
}
