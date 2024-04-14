package automation.pageobjects;

import automation.utils.CommonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


/*
 *@className - PendingRevisionRequestStage
 *@Objective - To define all the web elements and actions required for Pending Revision Request Stage.
 */
public class AwaitingRevisionStage extends CommonUtils {
    WebDriver driver;
    SOPDocumentPage sopDocumentPage;
    Logger logger = LogManager.getLogger(AwaitingRevisionStage.class);

    public AwaitingRevisionStage(WebDriver driver){
        super(driver);
        //initialize
        this.driver= driver;
        this.sopDocumentPage = new SOPDocumentPage(driver);
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//select[@id='select-div']")
    WebElement activitySelection ;
    @FindBy(css = "#select-div option[value='8'][data-id='0']")
    WebElement newRevisionActivitySelection;
    @FindBy(css = "#select-div option[value='9'][data-id='0']")
    WebElement obsoleteRevisionActivitySelection;
    @FindBy(xpath = "//button[@id='submit']")
    WebElement saveAndProceedButton;
    @FindBy(xpath = "//div[@class='toast-message']")
    WebElement successMsg;
    @FindBy(css = "span[id='ChildData'] b")
    WebElement errorTextForRevisionReq;
    @FindBy(xpath="//div[@class ='bar']//div[@class='bar-step  incomplete ']")
    WebElement currentBarStep;

    //error text returned for child revision sop record creation
    public String errorTextRevisionReq(){
        waitForWebElementToAppear(errorTextForRevisionReq);
        return errorTextForRevisionReq.getText();
    }


    //to click on family button-> parent SOP Record clicked (in effective state)
    public void goToParentSOPRecord(){
        sopDocumentPage.getParentSOPRecord();
        logger.info("parent sop record clicked");
    }

    //click on obsolete revision activity selection
    public String obsoleteRevisionsActivitySelection(){
        goToParentSOPRecord();
        waitForElementToBeClickable(activitySelection);
        if(currentBarStep.getText().contains("Awaiting revision / Obsolete")) {
            activitySelection.click();
            obsoleteRevisionActivitySelection.click();
            saveAndProceedButton.click();
            //System.out.println(successTransactionMsg.getText());
            return successMsg.getText();
        }else{
            return "Not found";
        }
    }

    //click on new revision activity selection
    public String newRevisionsActivitySelection(){
        goToParentSOPRecord();
        waitForElementToBeClickable(activitySelection);
        activitySelection.click();
        newRevisionActivitySelection.click();
        saveAndProceedButton.click();
        return errorTextForRevisionReq.getText();
    }


}
