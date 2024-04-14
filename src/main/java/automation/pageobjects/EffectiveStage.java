package automation.pageobjects;

import automation.utils.CommonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
/*
 *@className - EffectiveStage
 *@Objective - To define all the values that are common in effective stage.
 */
public class EffectiveStage extends CommonUtils {
    WebDriver driver;
    SOPDocumentPage sopDocumentPage;
    Logger logger = LogManager.getLogger(EffectiveStage.class);
    @FindBy(xpath="//div[@class ='bar']//div[@class='bar-step  incomplete ']")
    WebElement currentBarStep;
    public EffectiveStage (WebDriver driver){
        super(driver);
        this.sopDocumentPage = new SOPDocumentPage(driver);
        PageFactory.initElements(driver,this);
    }


}
