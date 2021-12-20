package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ErsPOM {

    WebDriver driver;
    Wait<WebDriver> wait;

    WebElement inputElem;
    WebElement btn;

    By loginBtnSelector = By.className("btn btn-primary");

    public ErsPOM(WebDriver driver){
        this.driver = driver;
        this.wait = new FluentWait<>(this.driver)
                .withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofMillis(500));
    }

    public void login(String username, String password){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        inputElem = driver.findElement(By.id("username-input"));
        inputElem.sendKeys(username);

        inputElem = driver.findElement(By.id("password-input"));
        inputElem.sendKeys(password);

        btn = driver.findElement(By.id("login-btn"));
        btn.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }
}

