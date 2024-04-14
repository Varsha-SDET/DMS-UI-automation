package automation.pageobjects;

import automation.utils.CommonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/*
 *@className - HeaderComponent
 * @classObjective- To define all the web elements and interactions action method for header component (access it with composition in entire framework)
 */
public class HeaderComponent extends CommonUtils {
    public WebDriver driver;
    Logger logger = LogManager.getLogger(HeaderComponent.class);
    public HeaderComponent(WebDriver driver){
        super(driver);
        //initialize
        this.driver= driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//div[@class='icon-grid']/div[@class='icon-drop']/div[1]")
    WebElement userButton;
    @FindBy(xpath = "//a[contains(text(),'Helpdesk')]")
    WebElement helpDeskButton;
    @FindBy(xpath = "//a[contains(text(),'Log')]")
    WebElement logOutButton;
    @FindBy(linkText = "Help")
    WebElement helpButton;
    @FindBy(xpath = "//div[contains(text(),'About')]")
    WebElement aboutButton;
    @FindBy(xpath = "//div[contains(text(),'Settings')]")
    WebElement settingButton;
    @FindBy(xpath="//div[contains(text(),'Logout Successfully.')]")
    WebElement logoutMsg;
    public String clickOnLogoutButtonFromHeader(){
        driver.navigate().refresh();
        waitForElementToBeClickable(userButton);
        userButton.click();
        logOutButton.click();
        waitForWebElementToAppear(logoutMsg);
        logger.info("logout from software y clicking through header");
        return logoutMsg.getText();
    }
}
