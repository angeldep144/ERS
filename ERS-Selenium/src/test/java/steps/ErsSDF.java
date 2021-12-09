package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pom.ErsPOM;

public class ErsSDF {
    WebDriver driver;
    ErsPOM ersPOM;
    String domain = "http://localhost:9000/";


    @Before
    public void setup(){
        System.setProperty("webdriver.chrome.driver", "/Library/tools/selenium/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get(domain);
    }

    @After
    public void teardown(){
        driver.quit();
    }

    @Given("A user is on the ERS App")
    public void aUserIsOnTheERSApp() {
    }

    @When("A user inputs a new reimbursement request in the input form")
    public void aUserInputsANewReimbursementRequestInTheInputForm() {
    }

    @Then("A new reimbursement request will display in the list of reimbursements")
    public void aNewReimbursementRequestWillDisplayInTheListOfReimbursements() {
    }
}
