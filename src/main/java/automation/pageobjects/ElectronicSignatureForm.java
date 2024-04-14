package automation.pageobjects;

import automation.utils.CommonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
/*
 *@className - ElectronicSignatureForm
 *@Objective - To define all the web elements and actions required for E-signature Form (can be reused in all stages where e-signature required).
 */
public class ElectronicSignatureForm extends CommonUtils {
    Logger logger = LogManager.getLogger(ElectronicSignatureForm.class);
    public WebDriver driver;
    public  ElectronicSignatureForm(WebDriver driver){
        super(driver);
        //initialize
        this.driver= driver;
        PageFactory.initElements(driver,this);
    }
    @FindBy(xpath = "//form[@id='modalSubmit']")
    WebElement electronicSignatureForm;
    @FindBy(xpath = "//input[@name='email']")
    WebElement userIdField;
    @FindBy(xpath = "//input[@name='password']")
    WebElement passwordField;
    @FindBy(xpath="//button[contains(text(),'Sign')]")
    WebElement signUpButton;
    @FindBy(css="button[onclick='cancel();']")
    WebElement cancelButton;
    @FindBy(xpath = "//button[@class='btn-close']")
    WebElement closeButton;
    @FindBy(xpath="//span[@id='activityName']")
    WebElement activityName;

    //signed for electronic signature
    public void submitElectronicSignature(){
        logger.info("e sign form appeared");
        waitForElementToBeClickable(userIdField);
        userIdField.sendKeys("user@qssence.com");
        passwordField.sendKeys("1234567890");
        signUpButton.click();
        logger.info("user id and password passed and signup button clicked in e-sign form.");
    }

    //cancel the electronic signature form
    public Boolean cancelElectronicSignature(){
       waitForElementToBeClickable(userIdField);
       userIdField.sendKeys("usbfvjj@88.com");
       cancelButton.click();
       waitForElementToDisappear(electronicSignatureForm);
        logger.info("cancel button clicked in e-sign form.");
        return electronicSignatureForm.isDisplayed();

    }

    public Boolean closeButtonInESignForm() throws InterruptedException {
        waitForElementToBeClickable(userIdField);
        userIdField.sendKeys("gjhjjhjhhj@hjbh.cjn");
        closeButton.click();
        waitForElementToDisappear(electronicSignatureForm);
        logger.info("close button clicked in e-sign form.");
        return electronicSignatureForm.isDisplayed();
    }

    //invalid credentials passing in electronic signature.
    public String invalidCredentialsPassedInESignForm() throws InterruptedException {
        waitForElementToBeClickable(userIdField);
        userIdField.sendKeys("gjhjjhjhhj@hjbh.cjn");
        passwordField.sendKeys("c68788ytgh");
        signUpButton.click();
        Thread.sleep(2000);
        Alert alert = driver.switchTo().alert();
        alert.accept();
        System.out.println(alert.getText());
        return alert.getText();
    }



}
