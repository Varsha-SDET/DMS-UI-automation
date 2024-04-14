package automation.pageobjects;

import automation.utils.CommonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.List;

/*
 *@className - DocumentInformationPage
 *@Objective - To define all the web elements and actions required for document information page.
 */
public class DocumentInformationPage extends CommonUtils {
    WebDriver driver;
    Logger logger = LogManager.getLogger(DocumentInformationPage.class);

    public  DocumentInformationPage(WebDriver driver){
        super(driver);
        //initialize
        this.driver= driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//div/button[contains(text(),'Document')]")
    WebElement documentInformationButton;
   // Initiation Information

    @FindBy(xpath = "//input[@name='Originator']")
    WebElement originator;
    @FindBy(xpath = "//input[@name='date_opened']")
    WebElement dateOpened;
    @FindBy(xpath = "//input[@id='title']")
    WebElement title;

    //Document Information
    @FindBy(xpath = "label[for='Assigned Document Number']")
    WebElement assignedSOPNumber;
    @FindBy(css = "label[for='Business Entity'] b:nth-child(1)")
    WebElement businessEntity;
    @FindBy(css = "label[for='Document type']")
    WebElement documentType;
    @FindBy(css = "label[for='Document Revision'] b")
    WebElement currentEffectiveVersion;
    @FindBy(css = "label[for='Owning Department']")
    WebElement owningDepartment;
    @FindBy(css = "label[for='Head of Department'] b:nth-child(1)")
    WebElement HODOfInitiatingDepartDropdown;
    @FindBy(css = "input[name='reason']")
    WebElement reason ;
    @FindBy(xpath = "//input[@name='change_control_ref_number']")
    WebElement changeControlRefNumber;

    //ref record table
    @FindBy(xpath = "//input[@name='reference_record']")
    WebElement refRecordTitle;
    @FindBy(css = "input[name='reference_attachment']")
    WebElement chooseAttachmentButton;

    //Document Distribution & Notified Department
    //in table select value in department
    @FindBy(css = "select[name='Department[]']")
    WebElement selectDeptValue;
    @FindBy(css="select[name='Department[]'] option")
    List<WebElement>selectDeptList;
    //in table select value in notified department

    @FindBy(css = "select[name='NotifiedDepartment[]']")
    WebElement selectNotifiedDeptValue;
    @FindBy(css="select[name='NotifiedDepartment[]'] option")
    List<WebElement>selectNotifiedDeptList;

    //in table select value in notified person
    @FindBy(css = "select[name='NotifiedPerson[]']")
    WebElement selectNotifiedPerson;
    @FindBy(css="select[name='NotifiedPerson[]'] option")
    List<WebElement>selectNotifiedPersonList;


    //in table pass the remarks
    @FindBy(xpath = "//input[@name='Remarks[]']")
    WebElement remarks;
    //in table passing comment
    @FindBy(css = "input[name='Comments[]']")
    WebElement comments;
    @FindBy(css = "div[class='button-bar'] button[type='submit']")
    WebElement saveButton;
    @FindBy(css="#document_distribution")
    WebElement addTableRow;
    @FindBy(css="#remove_button_1")
    WebElement removeTableRow;

    //activity selection
    @FindBy(xpath="//select[@id='select-div']")
    WebElement activitySelectionDropdown;

    // List activity selection -> //select[@id='select-div']/option
    //activity submit
    @FindBy(xpath="//select[@id='select-div']/option[@value='2' and contains(text(), 'Submit')]")
    WebElement submitActivity;
    //activity cancel
    @FindBy(xpath="//select[@id='select-div']/option[@value='1' and contains(text(), 'Cancel')]")
    WebElement cancelActivity;
    @FindBy(css="button[id='submit']")
    WebElement saveAndProceedButton;
    @FindBy(xpath = "//div[@class='toast-message']")
    WebElement toastMessage;
    @FindBy(xpath = "//form[@id='reasonSubmit']")
    WebElement alertForm;
    @FindBy(css = "textarea[name='rejectReason']")
    WebElement reasonForCancelActivity;
    @FindBy(xpath = "//form[@id='reasonSubmit']//button[@id='formSubmit']")
    WebElement proceedButtonInAlertForm;
    @FindBy(xpath = "//div[contains(text(),'Close-Cancelled')]")
    WebElement closedCancelText;
    @FindBy(xpath="//span[@id='spanTitle']")
    WebElement titleErrorMessage;
   @FindBy(xpath="//div[@class ='bar']//div[@class='bar-step  incomplete ']")
   WebElement currentBarStep;
    @FindBy(css =".bar-step")
    List<WebElement> barStepList;

    //document information values passing
    public String happyWorkflowOfDocInfo(HashMap<String,String> input) {
        documentInformationButton.click();
        title.sendKeys("Test");
        saveButton.click();

        MyTaskPage myTaskPage = new MyTaskPage(driver);
        myTaskPage.clickNewRecordOpened();
        fillDataInDocInfoFields(input);
        scrollToTop();
        waitForElementToBeClickable(activitySelectionDropdown);
        currentBarStep.isDisplayed();
        //for current stage click activity selection dropdown and select activity.
        if(currentBarStep.getText().contains("Opened")) {
            activitySelectionDropdown.click();
            waitForElementToBeClickable(submitActivity);
           //barStep1.getAttribute("class").contains("incomplete");
            submitActivitySelection();
            saveAndProceedButton.click();
            waitForWebElementToAppear(toastMessage);
            logger.info("toast message for successfully saving details appeared. ");
            return toastMessage.getText();
        }else{
            return "Stage Not found";
        }
    }

    //select activity to submit
    public void submitActivitySelection(){
     // waitForElementToBeClickable(submitActivity);
      submitActivity.click();
    }

    //mandatory field error message returned for documentInformation tab ( alert handle ->click ok if not provided title)
    public String withoutMandatoryFieldPassedInDocInfo() {
        documentInformationButton.click();
        saveButton.click();
        Alert alert =driver.switchTo().alert();
        alert.accept();
        waitForWebElementToAppear(titleErrorMessage);
        logger.info("title for error message appeared for mandatory field -title");
        return titleErrorMessage.getText();
    }


    //cancel activity perform for document information
    public String cancelActivitySelection(){
        documentInformationButton.click();
        title.sendKeys("Test");
        saveButton.click();
        MyTaskPage myTaskPage = new MyTaskPage(driver);
        myTaskPage.clickNewRecordOpened();
        activitySelectionDropdown.click();
        cancelActivity.click();
        saveAndProceedButton.click();
        waitForWebElementToAppear(alertForm);
      //  waitForElementToBeClickable(reasonForCancelActivity);
        // Switch to the alert
       // Alert alert = driver.switchTo().alert();
        reasonForCancelActivity.sendKeys("canceling activity");
        proceedButtonInAlertForm.click();
        waitForWebElementToAppear(closedCancelText);
        logger.info("text for cancelling the activity appears ");
        return closedCancelText.getText();

    }

    //select dept value from dropdown list
    public void selectDeptValue(){
        selectDeptValue.click();
                selectDeptList.stream().filter(value-> value.getText().
                        contains("Quality Assurance")).findAny()
                        .ifPresent(WebElement::click);
                logger.info("dept value returned.");
    }

    //select notified dept value from dropdown list
    public void selectNotifiedDeptValue(){
        selectNotifiedDeptValue.click();
        selectNotifiedDeptList.stream().filter(value-> value.getText().
                        contains("Quality Control")).findAny()
                .ifPresent(WebElement::click);
        logger.info("notified dept value returned");
    }

    //select notified person value from dropdown list
    public void selectNotifiedPersonValue(){
        selectNotifiedPerson.click();
        selectNotifiedPersonList.stream().filter(value-> value.getText().
                        contains("User")).findAny()
                .ifPresent(WebElement::click);
        logger.info("notified person value returned");
    }

    public void fillDataInDocInfoFields(HashMap<String,String> input) {
        scrollDownByScreenHeight();
       // Thread.sleep(2000);
        reason.sendKeys(input.get("reason"));
        remarks.sendKeys(input.get("remarks"));
        comments.sendKeys(input.get("comments"));
        refRecordTitle.sendKeys(input.get("refRecordTitle"));
        selectDeptValue();
        selectNotifiedDeptValue();
        selectNotifiedPersonValue();
        addTableRow.click();
        addTableRow.click();
        removeTableRow.click();
        removeTableRow.click();
        logger.info("document information tab data fields added successfully. ");
    }


    //add attachment for the reference record
    public void addAttachmentInReferenceRecord(){

    }

    //non-editable fields verify
    public void verifyNonEditableFields(){

    }

}
