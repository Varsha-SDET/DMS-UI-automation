package automation.pageobjects;

import automation.utils.CommonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/*
 *@className - DivisionPage
 * @classObjective- To define all the web elements and action method for division selecting and project selection(process) page.
 */
public class DivisionPage extends CommonUtils {
    Logger logger = LogManager.getLogger(DivisionPage.class);
    public WebDriver driver;
    public  DivisionPage(WebDriver driver){
        super(driver);
        //initialize
        this.driver= driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(css=".divisionlinks")
    List<WebElement> divisionLinks;

    //project section
    @FindBy(name="process_id")
    WebElement SOP;

    @FindBy(xpath ="//button[contains(text(),'Proceed')]")
    WebElement proceedButton;

    @FindBy(xpath = "(//span[@id='process_span'])[1]")
    WebElement errorMessage;

    @FindBy(xpath = "//button[contains(text(),'Cancel')]")
    WebElement cancelButton;


    //select division
    public void selectDivision(String division){

        //List<WebElement> divisionList =divisionCheckbox.stream().toList();
        for (WebElement divisionLabel : divisionLinks) {
            if (divisionLabel.getText().equals(division)) {
                waitForWebElementToAppear(divisionLabel);

                System.out.println(divisionLabel.getText());
                divisionLabel.findElement(By.tagName("input")).click();
//                divisionLabel.click();
                logger.info("division selected");
                break;
            }
        }
    }


    //select project for division selected
   // public void selectProject(String project){

        public void selectProject(){
        SOP.click();
        logger.info("project selected");

    }

    //proceed button click
    public void clickProceedButton(){
        proceedButton.click();
    }

    //cancel button click
    public void clickCancelButton(){
        cancelButton.click();
    }

    //select division then select project and click on proceed button.
    public void selectDivisionAndProject(String division)  {
        selectDivision(division);
        selectProject();
        //selectProject(project);
        clickProceedButton();
        String childId = getChildWindowId(this.driver);
        waitForNewWindow(driver);
        driver.switchTo().window(childId).manage().window().maximize();
        // Thread.sleep(2000);
        // waitForNewWindow(childId, driver);
        System.out.println("currentWindowID: "+ driver.getWindowHandle() + " current url:"+ driver.getCurrentUrl());
        logger.info("switched to child window handler and driver object returning");

    }

    //select division then select project and click on proceed button.
    public String checkMendatoryValues(String division){
        selectDivision(division);
        clickProceedButton();
        logger.info("proceed button clicked");
        // Switch to the alert
        Alert alert = driver.switchTo().alert();
        // Accept the alert (click OK)
        alert.accept();
        // After handling the alert, switch back to the default content
        driver.switchTo().defaultContent();
        logger.info("alert handled and switched to default content");
        return errorMessage.getText();

    }


    //select division then click on cancel button
    public void selectDivisionClickCancelButton(String division){
        selectDivision(division);
        //selectProject(project);
        clickCancelButton();
        logger.info("cancel button clicked.");
    }

}
