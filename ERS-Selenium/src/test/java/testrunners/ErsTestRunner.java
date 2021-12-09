package testrunners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@CucumberOptions(features = "./test/java/features/ers.feature", glue = "steps.ErsSDF")
@RunWith(Cucumber.class)

public class ErsTestRunner {
}
