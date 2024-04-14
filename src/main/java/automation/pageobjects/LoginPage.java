package automation.pageobjects;

import automation.utils.CommonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/*
 *@className - LoginPage
 * @classObjective- To define all the web elements and action method for login page
 */
public class LoginPage extends CommonUtils {
    Logger logger = LogManager.getLogger(LoginPage.class);
    public WebDriver driver;
    public HeaderComponent header;
    public  LoginPage(WebDriver driver){
        super(driver);
        //initialize
        this.driver= driver;
        this.header = new HeaderComponent(driver);
        PageFactory.initElements(driver,this);
    }

    @FindBy(name="email")
    private WebElement userName;
    @FindBy(name="password")
    private WebElement passwordEle;
    @FindBy(xpath = "//select[@class='goog-te-combo']")
    private WebElement languageDropdown;
    @FindBy(css = "select[name='timezone']")
    private WebElement timeZoneDropdown;
    @FindBy(css=".forgot a[href='forgot-password']")
    private WebElement forgetPassword;
    @FindBy(css="input[value='Login']")
    private WebElement login;
    @FindBy(xpath = "//body/div[@id='toast-container']/div[1]")
    WebElement errorMessage;


    public MyTaskPage loginApplication(String email, String password){
        logger.info("Logging in with username: {}, language: {}, timeZone: {}.",email,password);
        userName.sendKeys(email);
        passwordEle.sendKeys(password);
//        languageDropdown.click();
//        Select languageSelect = new Select(languageDropdown);
//        languageSelect.selectByValue(languageValue);
//        Select timeZoneSelect = new Select(timeZoneDropdown);
//        timeZoneSelect.selectByValue(timezoneValue);
        login.click();
        //returning mytaskpage object
        return new MyTaskPage(driver);

    }

    public String invalidLoginApplication(String email, String password){
        logger.info("Logging in with username: {}, language: {}, timeZone: {}.",email,password);
        String errorMsg = null;
        userName.sendKeys(email);
        passwordEle.sendKeys(password);
        login.click();
        waitForWebElementToAppear(errorMessage);
        if(errorMessage.getText().contains("Login failed.")) {
            errorMsg = "Login failed.";
        }
        return errorMsg;
    }

    //to check user logout successfully from header options
    public String checkLogout(){
        String logoutText =header.clickOnLogoutButtonFromHeader();
        System.out.println(logoutText);
        logger.info("logout successfully by clicking on logout from header-> user button");
          return logoutText;
    }

    public void goTo(){
        logger.info("Navigating to the login page.");
        driver.get("https://qssencedms.demoventuringdigitally.com/");
    }


}
