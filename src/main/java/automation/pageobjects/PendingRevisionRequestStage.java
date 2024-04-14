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
public class PendingRevisionRequestStage extends CommonUtils {
    WebDriver driver;
    SOPDocumentPage sopDocumentPage;
    Logger logger = LogManager.getLogger(PendingRevisionRequestStage.class);

    public PendingRevisionRequestStage(WebDriver driver){
        super(driver);
        //initialize
        this.driver= driver;
        this.sopDocumentPage = new SOPDocumentPage(driver);
        PageFactory.initElements(driver,this);
    }

    // //b[contains(text(),'You can not perform')]
    @FindBy(css = "span[id='ChildData'] b")
    WebElement errorTextForRevisionReq;
    @FindBy(xpath="//div[@class ='bar']//div[@class='bar-step  incomplete ']")
    WebElement currentBarStep;
    //error text returned for child revision sop record creation
    public String errorTextRevisionReq(){
        waitForWebElementToAppear(errorTextForRevisionReq);
       // System.out.println(errorTextForRevisionReq.getText());
        return errorTextForRevisionReq.getText();
    }


    //child effective in 1st sop record in pending stage
    public void childEffectiveInPendingRevisionReq(){
        SOPDocumentPage sopDocumentPage = new SOPDocumentPage(driver);
        sopDocumentPage.getChildSOPRecord();
        InitiateRevisionStage initiateRevisionStage = new InitiateRevisionStage(driver);
        if(currentBarStep.getText().contains("Pending Revision Req.")) {
            initiateRevisionStage.inititateRevisionActivitySelection();
        }
    }

    //child effective in 2nd sop record in pending stage
    public void child2EffectiveInPendingRevisionReq() throws InterruptedException {
        SOPDocumentPage sopDocumentPage = new SOPDocumentPage(driver);
        sopDocumentPage.get2ndChildSOPRecord();
        InitiateRevisionStage initiateRevisionStage = new InitiateRevisionStage(driver);
        if(currentBarStep.getText().contains("Pending Revision Req."))
        initiateRevisionStage.inititateRevisionActivitySelection();

    }

    //child closed by clicking cancel activity
    public void childClosedCancelInPendingRevisionReq() throws InterruptedException {
       // SOPDocumentPage sopDocumentPage = new SOPDocumentPage(driver);
        sopDocumentPage.getChildSOPRecord();
        logger.info("child SOP revision record clicked");
        InitiateRevisionStage initiateRevisionStage = new InitiateRevisionStage(driver);
       // driver.navigate().refresh();
        initiateRevisionStage.cancelRevisionActivitySelection();

    }

}
