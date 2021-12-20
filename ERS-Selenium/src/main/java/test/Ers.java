package test;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Ers {

    WebDriver driver;
    Wait<WebDriver> wait;

    WebElement inputElem;
    WebElement btn;

    public Ers(WebDriver driver){
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

    public void submitReimbursement(Double amount){

        inputElem = driver.findElement(By.id("reimb-amount"));
        inputElem.sendKeys(amount.toString());

        Select select = new Select(driver.findElement(By.id("reimb-type")));
        select.selectByIndex(1);

        inputElem = driver.findElement(By.id("reimb-description"));
        inputElem.sendKeys("This is the description for why I need this reimbursement approved");

        btn = driver.findElement(By.id("submit-btn"));
        btn.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }
}


