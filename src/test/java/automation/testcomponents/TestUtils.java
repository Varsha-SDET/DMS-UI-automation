package automation.testcomponents;

import automation.pageobjects.LoginPage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
/*
 *@className - TestUtils
 * @classObjective- To define all the test utilities method required for all the tests.
 */
public class TestUtils {
   public WebDriver driver;
   public LoginPage loginPage;
    //to implement logger
     Logger logger = LogManager.getLogger(TestUtils.class);

    //   public WebDriver initializeDriver() throws IOException{
//       Properties properties = new Properties();
//       FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir")+"//src//main//java//automation//resources//FrameworkConfig.properties");
//       properties.load(fileInputStream);
//
//       String browserName = System.getProperty("browser")!=null ?
//               System.getProperty("browser") : properties.getProperty("browser");
//
//       if(browserName.contains("chrome")){
//           WebDriverManager.chromedriver().setup();
//           this.driver =new ChromeDriver();
//       }
//       else if(browserName.equalsIgnoreCase("edge")){
//           WebDriverManager.edgedriver().setup();
//           driver = new EdgeDriver();
//       }
////       driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
////                  WebDriverManager.chromedriver().setup();
////           this.driver =new ChromeDriver();
//       driver.manage().window().maximize();
//       return driver;
//   }
   //driver initialise
    public WebDriver initializeDriver() throws IOException{
        //object of Interface frameworkConfig created(with same name as properties file)
        FrameworkConfig config = ConfigFactory.create(FrameworkConfig.class);
        // driver is initialized with browser i.e. passed in frameworkconfig.properties file
        if(config.browser().name().equalsIgnoreCase("chrome")){
            ChromeOptions options = new ChromeOptions();
            WebDriverManager.chromedriver().setup();
            //to run in headless mode
            if(config.browser().name().contains("headless")){
                options.addArguments("headless");
            }
            this.driver =new ChromeDriver();
            //full screen dimension
            driver.manage().window().setSize(new Dimension(1440,900));

        }
        else if (config.browser().name().equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            this.driver = new EdgeDriver();
        }
        else if (config.browser().name().equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            this.driver= new FirefoxDriver();
        }

        driver.manage().window().maximize();
        return driver;

    }
    //to call it without creating obj and directly can be accessible in its child class
    public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
        //to read json to string
        String jsonContent = FileUtils.readFileToString(new File(filePath),
                StandardCharsets.UTF_8);
        //string to hashmap jackson databind
        ObjectMapper mapper = new ObjectMapper();
        List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
        });
        //list-> of hashmaps returned using object mapper read value method
        return data;
    }

    //capture screnshots on test failure
    public String getScreenshot(String testCaseName,WebDriver driver) throws IOException {
        TakesScreenshot ts=(TakesScreenshot)driver;
        File source =ts.getScreenshotAs(OutputType.FILE);
        File file = new File(System.getProperty("user.dir")+"//reports//"+ testCaseName + ".png");
        FileUtils.copyFile(source,file);
        logger.info("Screenshot captured.");
        //after taking ss returning path of local system where it is stored
        return System.getProperty("user.dir")+ "//reports//" + testCaseName + ".png";
    }

    //software initialize and always run at any condition.
    @BeforeMethod(alwaysRun = true)
    public LoginPage launchApplication()throws IOException{
        logger.info("Initializing WebDriver.");
        this.driver = initializeDriver();
        loginPage = new LoginPage(driver);
        loginPage.goTo();
        return loginPage;
    }

    //close all the browser opened
    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        logger.info("Closing the browser.");
       // driver.quit();
    }

    //add logging before starting test suite
    @BeforeSuite
    public void setUpSuite() {
//        // Explicitly initialize Log4j
//        System.setProperty("log4j2.configurationFile", "C:\\Users\\varsh\\IdeaProjects\\qssence-automation\\src\\main\\java\\automation\\resources\\log4j2.xml");
//        logger.info("Log4j2 initialized explicitly.");
        System.setProperty("log4j.configurationFile", System.getProperty("user.dir") + "\\src\\main\\java\\automation\\resources\\log4j2.xml");
        logger.info("Log4j2 initialized explicitly.");
    }


}
