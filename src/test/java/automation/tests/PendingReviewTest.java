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
 *@className - PendingReviewTest
 * @classObjective- To define the test cases required for pending review test for revision SOP record.
 */

public class PendingReviewTest extends TestUtils {
    @DataProvider
    public Object[][] TestData() throws IOException {
        List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir") + "//src//test//java//automation/data//Credentials.json");
        return new Object[][]{{data.get(6)}};
        //  return new Object[][]{{data.get(3)}, {data.get(4)}};
    }

    //Testcase1  : To verify by passing all the details and check SOP revision record of pending review stage for activity selection SME Review complete.
    @Test(dataProvider = "TestData",retryAnalyzer = Retry.class)
    public void verifySMEReviewCompleteActivity(HashMap<String, String> input) throws InterruptedException {
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
        String childNotEffectiveText =pendingRevisionRequestStage.errorTextRevisionReq();

        Assert.assertTrue(childNotEffectiveText.contains("You can not perform any activity while the child is not effective."));
        //childEffective
        pendingRevisionRequestStage.childEffectiveInPendingRevisionReq();
        SOPRevisionInitiateStage sopRevisionInitiateStage = new SOPRevisionInitiateStage(driver);
        driver.navigate().refresh();
        String activitySuccessMsg =sopRevisionInitiateStage.fillDataInDocInfoTabForSOPRevision();
        Assert.assertTrue(activitySuccessMsg.contains("Transaction Completed successfully"));
        PendingDocRevisionStage pendingDocRevisionStage = new PendingDocRevisionStage(driver);
        String successMsg =pendingDocRevisionStage.QAReviewCompleteActivitySelection();
        Assert.assertTrue(successMsg.contains("Transaction Completed successfully"));
        PendingReviewStage pendingReviewStage = new PendingReviewStage(driver);
        String formSuccessMsg=pendingReviewStage.SMEReviewCompleteActivitySelection();
        Assert.assertTrue(formSuccessMsg.contains("Transaction Completed successfully"));

    }

    //Testcase2  : To verify by passing all the details and check SOP revision record of pending review stage for activity selection request changes.
    @Test(dataProvider = "TestData",retryAnalyzer = Retry.class)
    public void verifyRequestChangesActivity(HashMap<String, String> input) throws InterruptedException {
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
        String childNotEffectiveText =pendingRevisionRequestStage.errorTextRevisionReq();

        Assert.assertTrue(childNotEffectiveText.contains("You can not perform any activity while the child is not effective."));
        //childEffective
        pendingRevisionRequestStage.childEffectiveInPendingRevisionReq();
        SOPRevisionInitiateStage sopRevisionInitiateStage = new SOPRevisionInitiateStage(driver);
        driver.navigate().refresh();
        String activitySuccessMsg =sopRevisionInitiateStage.fillDataInDocInfoTabForSOPRevision();
        Assert.assertTrue(activitySuccessMsg.contains("Transaction Completed successfully"));
        PendingDocRevisionStage pendingDocRevisionStage = new PendingDocRevisionStage(driver);
        String successMsg =pendingDocRevisionStage.QAReviewCompleteActivitySelection();
        Assert.assertTrue(successMsg.contains("Transaction Completed successfully"));
        PendingReviewStage pendingReviewStage = new PendingReviewStage(driver);
        String formSuccessMsg=pendingReviewStage.requestChangesActivitySelection();
        Assert.assertTrue(formSuccessMsg.contains("Transaction Completed successfully"));

    }


}
