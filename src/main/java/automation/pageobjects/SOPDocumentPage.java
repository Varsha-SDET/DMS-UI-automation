package automation.pageobjects;

import automation.utils.CommonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/*
 *@className - SOPDocumentPage
 *@Objective - To define all the values that are common in entire sop document creation workflow .
 */
public class SOPDocumentPage extends CommonUtils {
    WebDriver driver;
    Logger logger = LogManager.getLogger(SOPDocumentPage.class);

    public  SOPDocumentPage(WebDriver driver){
        super(driver);
        //initialize
        this.driver= driver;
        PageFactory.initElements(driver,this);
    }
    @FindBy(xpath = "//button[@id='Exit']")
    WebElement exitButton;
    ////button[@id='openSidebarButton']
    @FindBy(id = "openSidebarButton")
    WebElement familyButton;
    @FindBy(xpath = "//button[@id='audit_trial']")
    WebElement auditTrialButton;
    @FindBy(xpath = "//div/table//tr[2]/td/a[contains(@href, 'sop_revision_view')]")
    WebElement childRecordCreated;
    @FindBy(xpath = "//div/table//tr[3]/td[1]/a[contains(@href, 'sop_revision_view')]")
    WebElement child2RecordCreated;
    @FindBy(xpath = "//div/table//tr[1]/td[1]/a[1]/b[1]")
    WebElement parentRecordCreated;
    @FindBy(xpath = "//button[@id='sidebarCloseButton']")
    WebElement closeSidebarOfFamilyButton;
    @FindBy(xpath="//div[@class ='bar']//div[@class='bar-step  incomplete ']")
    WebElement currentBarStep;

    //click on child-1 SOP record
    public void getChildSOPRecord() {
        waitForElementToBeClickable(familyButton);
        familyButton.click();
        logger.info("family button clicked");
        waitForWebElementToAppear(childRecordCreated);
        childRecordCreated.click();
        logger.info("child SOP record clicked");

    }

    //click on parent record from family
    public void getParentSOPRecord() {
        waitForElementToBeClickable(familyButton);
        familyButton.click();
        logger.info("family button clicked");
        waitForWebElementToAppear(parentRecordCreated);
        parentRecordCreated.click();
        logger.info("parent SOP record clicked");

    }

    //click on 2nd child SOP record (created on obsolete stage).
    public void get2ndChildSOPRecord() {
        waitForElementToBeClickable(familyButton);
        familyButton.click();
        logger.info("family button clicked");
        waitForWebElementToAppear(child2RecordCreated);
        child2RecordCreated.click();
        logger.info("2nd child SOP record clicked");

    }

    //get the status of the active stage and text returned.
    public void getActiveStageBarText(){

    }

    //exit button click
    public void clickExitButton(){

        exitButton.click();
    }

    //audit trial button click
    public void clickAuditTrialButton(){

        auditTrialButton.click();
    }

    //state bar with active status

}
