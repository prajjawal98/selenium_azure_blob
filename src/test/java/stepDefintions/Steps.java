package stepDefintions;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class  Steps {
    private static WebDriver driver;

    @Given("^I navigate to website$")
    public void iNavigateToWebsite() {
        initializeWebDriver();
        driver.get("https://www.flipkart.com/?cmpid=socialorg_Video_YT_FathersDay&gad_source=1&gclid=CjwKCAjwlbu2BhA3EiwA3yXyu9vf6jcpLfHQpLRfd4S76DKMJN6sN4-pZ5wC6zfOiXUa5DLDYOliuxoCVOwQAvD_BwE");
    }

    @When("^Network conditions are set to offline$")
    public void networkConditionsAreSetToOffline()  {
        driver.navigate().to("https://www.amazon.in/?&tag=googhydrabk1-21&ref=pd_sl_5szpgfto9i_e&adgrpid=155259813593&hvpone=&hvptwo=&hvadid=674893540034&hvpos=&hvnetw=g&hvrand=7649831893606498515&hvqmt=e&hvdev=c&hvdvcmdl=&hvlocint=&hvlocphy=9182719&hvtargid=kwd-64107830&hydadcr=14452_2316413&gad_source=1");

    }

    @Then("^I should see the console logs in the output$")
    public void iShouldSeeTheConsoleLogsInTheOutput() {
        String pageTitle = driver.getTitle();
        Assert.assertTrue(pageTitle.contains("Amazon"));
        BlobStorageUtil blobStorageUtil = new BlobStorageUtil();
        blobStorageUtil.uploadDirectory("target/surefire-reports");
    }

    private static void initializeWebDriver() {
        WebDriverManager.chromedriver().setup(); // Automatically manage ChromeDriver binaries
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Enable headless mode
        options.addArguments("--disable-gpu"); // Optional: Disables GPU acceleration

        driver = new ChromeDriver(options);
    }


    // Optionally, add a @After method to clean up after the test
    @io.cucumber.java.After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


}


