package automation.tests;

import automation.pageobjects.*;
import automation.testcomponents.Retry;
import automation.testcomponents.TestUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


/*
 *@className - SOPRevisionTest
 * @classObjective- To define the test cases required for SOP revision(1st child SOP record created after pending QA
 *                   approval stage in parent ) all stages.
 */

public class SOPRevisionTest extends TestUtils {
    @DataProvider
    public Object[][] testData() throws IOException {
        List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir") + "//src//test//java//automation/data//Credentials.json");
        return new Object[][]{{data.get(6)}};
    }


    //Test case 1: Selecting no activity and check validation for SOP creation in initial stage.
    @Test(dataProvider = "testData",retryAnalyzer = Retry.class)
    public void verifyNoActivitySelectionForSOPRevision(HashMap<String, String> input) throws InterruptedException {
        MyTaskPage myTaskPage = loginPage.loginApplication(input.get("email"), input.get("password"));
        //  MyTaskPage myTaskPage = new MyTaskPage(driver);
        DivisionPage divisionPage = myTaskPage.clickCreateSOP();
        //  DivisionPage divisionPage = new DivisionPage(driver);
        divisionPage.selectDivisionAndProject(input.get("division"));
        Assert.assertEquals("https://qssencedms.demoventuringdigitally.com/Sop_form", driver.getCurrentUrl());
        DocumentInformationPage documentInformationPage = new DocumentInformationPage(driver);
        String successMessage = documentInformationPage.happyWorkflowOfDocInfo(input);
        Assert.assertTrue(successMessage.contains("Submit is completed."));
        QAPendingApprovalStage qaPendingApprovalStage = new QAPendingApprovalStage(driver);
        qaPendingApprovalStage.performQAPendingApprovalActivity();
        PendingRevisionRequestStage pendingRevisionRequestStage = new PendingRevisionRequestStage(driver);
        Assert.assertTrue(pendingRevisionRequestStage.errorTextRevisionReq().contains("You can not perform any activity while the child is not effective."));
        //childEffective
        pendingRevisionRequestStage.childEffectiveInPendingRevisionReq();
        SOPRevisionInitiateStage sopRevisionInitiateStage = new SOPRevisionInitiateStage(driver);
        driver.navigate().refresh();
        // sopRevisionInitiateStage.fillDataInDocInfoTabForSOPRevision();
        String activityRequiredText =sopRevisionInitiateStage.noSelectActivity();
        Assert.assertTrue(activityRequiredText.contains("Activity is required"));



    }

}
