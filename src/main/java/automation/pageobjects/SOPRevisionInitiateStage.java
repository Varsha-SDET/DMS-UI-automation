package automation.pageobjects;

import automation.utils.CommonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class SOPRevisionInitiateStage extends CommonUtils {
    public WebDriver driver;
    Logger logger = LogManager.getLogger(SOPRevisionInitiateStage.class);

    public  SOPRevisionInitiateStage(WebDriver driver){
        super(driver);
        this.driver= driver;
        //initialize
        PageFactory.initElements(driver,this);
    }
    @FindBy(id = "document_information")
    WebElement documentInformationTab;
    @FindBy(id="general_information")
    WebElement generalInfo;
    @FindBy(xpath="//label[@for='Parent Id']/following-sibling::input[1]")
    WebElement parentIdField;
    @FindBy(xpath = "//input[@name='originator']")
    WebElement originatorField;
    @FindBy(xpath = "//input[@name='date_opened']")
    WebElement dateOpenedField;
    @FindBy(xpath = "//input[@id='title']")
    WebElement titleField;
    @FindBy(xpath = "//span[@id='select2-head_of_department-container']")
    WebElement HODInititatingDropdown;
    @FindBy(xpath = "//input[@role='textbox']")
    WebElement searchBoxInHODDropdown;
    @FindBy(css = ".select2-results__options li")
    List<WebElement> HODInititatingDropdownList;
    @FindBy(css = "span[id='select2-head_of_department-container'] span[class='select2-selection__clear']")
    WebElement clearButtonForHODInitiateDropdown;
    @FindBy(css="#select2-qa_hod-container")
    WebElement QAHODDropdown;
    @FindBy(xpath = "//input[@role='textbox']")
    WebElement searchBoxInQAHODDropdown;

    @FindBy(css = "span[id='select2-qa_hod-container'] span[class='select2-selection__clear']")
    WebElement clearButtonForQAHODDropdown;

    @FindBy(css = ".select2-results__options li")
    List<WebElement> QAHODDropdownList;

    @FindBy(css="#revision_details")
    WebElement addRevisionRow;
    @FindBy(css="#remove_button_3")
    WebElement removeRevisionRow;
    @FindBy(css= "input[name='ChangesIncorporated[]']")
    WebElement changesIncorporatedField;
    @FindBy(css="input[name='ChangeControlReferenceNo[]']")
    WebElement ChangeControlReferenceNoField;
    @FindBy(xpath="//input[@name='ReasonForChange")

    WebElement reasonForChangeField;
    @FindBy(id="document_information2")
    WebElement documentInfoSubTab;

    //activity selection
    @FindBy(xpath="//select[@id='select-div']")
    WebElement activitySelectionDropdown;
    // //option[contains(text(),'Submit Doc. For Review')]
    @FindBy(css= "option[value='15'][data-id='0']")
    WebElement submitDocActivity;
    @FindBy(id="submit")
    WebElement saveAndProceedButton;

    //SOP Revision
    @FindBy(id="sop_revision")
    WebElement SOPRevisionTab;
    @FindBy(css = "input[name='final_document']")
    WebElement finalDocumentAttachmentField;
    @FindBy(css="#yes")
    WebElement trainingRequiredYes;
    @FindBy(css="#no")
    WebElement trainingRequiredNo;
    @FindBy(xpath="//input[@id='reason_Training']")
    WebElement reasonField;
    @FindBy(id="yes1")
    WebElement SMEReviewRequiredYes;
    @FindBy(id="no1")
    WebElement SMEReviewRequiredNo;
    @FindBy(css="#select2-training_responsible22-container")
    WebElement SMEName;
    @FindBy(css=".select2-results__options li")
    List<WebElement> SMENameList;
    @FindBy(css="span[id='select2-training_responsible22-container'] span[class='select2-selection__clear']")
    WebElement SMENameClear;
    @FindBy(xpath = "input[role='textbox']")
    WebElement searchSMEName;
    @FindBy(css="#reason_SME")
    WebElement reasonSME;

    //document information sub tab
    @FindBy(id = "date_duration")
    WebElement SOPEffectiveNumOfDaysDropdown;
    @FindBy(xpath = "//select[@id='date_duration']/option")
    List<WebElement> dateDurationDropdownList;
    @FindBy(id = "effective_date")
    WebElement effectiveDateField;
    @FindBy(id = "date_duration")
    WebElement SOPEffectiveNumOfDays;
    @FindBy(xpath = "//select[@id='date_duration']/option")
    List<WebElement> SOPEffectiveNumOfDaysList;

    @FindBy(css = "#mandatory_review_date")
    WebElement mandatoryReviewDateField;
    @FindBy(css="span[id='alert'] b")
    WebElement activityRequiredText;
    @FindBy(xpath = "//div[@class='toast-message']")
    WebElement successTransactionMsg;
    @FindBy(xpath="//div[@class ='bar']//div[@class='bar-step  incomplete ']")
    WebElement currentBarStep;

    //fill all the details for document information tab -> general information sub tab , SOP Revision sub tab,document information sub tab
    public String fillDataInDocInfoTabForSOPRevision(){
       waitForElementToBeClickable(documentInformationTab);
       fillDataInGenInfoSubTab();
       logger.info("fill data in general information sub tab in document information tab for SOP revision initial stage");
       fillDataInSOPRevisionSubTab();
       logger.info("fill data in SOP revision sub tab in document information tab for SOP revision initial stage");
       fillDataInDocInfoSubTab();
       logger.info("fill data in document information sub tab in document information tab for SOP revision initial stage");
       String successMsg= activitySelectionForSubmitDocInfo();
       logger.info("Activity selection for submit doc information in SOP revision initial stage");
       return successMsg;
    }

    //activity selection -> submit document info for review
     public String activitySelectionForSubmitDocInfo(){
     //driver.navigate().refresh();
     waitForWebElementToAppear(activitySelectionDropdown);
     activitySelectionDropdown.click();
     submitDocActivity.click();
     saveAndProceedButton.click();
     return successTransactionMsg.getText();

    }

    //no activity selection-> click save and proceed button
    public String noSelectActivity(){

        saveAndProceedButton.click();
        Alert alert = driver.switchTo().alert();
        alert.accept();
        return activityRequiredText.getText();

    }

    //pass data in general info sub tab
    public void fillDataInGenInfoSubTab(){
        generalInfo.click();
        HODInititatingDropdown.click();
        HODInititatingDropdownList.stream().filter(user-> user.getText().
                contains("User QA 1")).findFirst().ifPresent(WebElement::click);
    }

    //pass data in SOP Revision sub tab
    public void fillDataInSOPRevisionSubTab(){
        SOPRevisionTab.click();
        trainingRequiredNo.click();
        reasonField.sendKeys("No reason");
        SMEReviewRequiredNo.click();
        reasonSME.sendKeys("Reason");
        SMEName.click();
        //sme select from dropdown
        SMENameList.stream().filter(user-> user.getText().contains("User")).findFirst()
                .ifPresent(WebElement::click);
    }

    //pass data in document information sub tab
    public void fillDataInDocInfoSubTab(){
      documentInfoSubTab.click();
      SOPEffectiveNumOfDays.click();
      SOPEffectiveNumOfDaysList.stream().filter(day-> day.getText().
              contains("10 Days")).findFirst().ifPresent(WebElement::click);
      SOPEffectiveNumOfDays.click();

    }





}
