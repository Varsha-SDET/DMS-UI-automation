package automation.pageobjects;

import automation.utils.CommonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/*
 *@className - PendingObsoleteStage
 * @classObjective- To define all the web elements and action method for pending obsolete stage.
 */
public class PendingObsoleteStage extends CommonUtils {
    Logger logger = LogManager.getLogger(PendingObsoleteStage.class);
    public WebDriver driver;
    public  PendingObsoleteStage(WebDriver driver){
        super(driver);
        //initialize
        this.driver= driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//select[@id='select-div']")
    WebElement activitySelection ;
    @FindBy(css = "option[value='12'][data-id='0']")
    WebElement obsoleteActivitySelection;
    @FindBy(xpath = "//button[@id='submit']")
    WebElement saveAndProceedButton;
    @FindBy(xpath = "//div[@class='toast-message']")
    WebElement successMsg;
    @FindBy(xpath="//div[@class ='bar']//div[@class='bar-step  incomplete ']")
    WebElement currentBarStep;
    //obsolete activity selection
    public String obsoleteActivitySelection(){
        waitForElementToBeClickable(activitySelection);
        if(currentBarStep.getText().contains("Pending Obsolete")) {
            activitySelection.click();
            obsoleteActivitySelection.click();
            saveAndProceedButton.click();
            //System.out.println(successTransactionMsg.getText());
            return successMsg.getText();
        }else {
            return "Not found";
        }
    }

}
