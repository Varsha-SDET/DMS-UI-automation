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
 *@className - CreateSOPTest
 * @classObjective- To define the test cases required for creating SOP.
 */
public class CreateSOPTest extends TestUtils {

    @DataProvider
    public Object[][] getHappyWorkflowTestData() throws IOException {
        List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir") + "//src//test//java//automation/data//Credentials.json");
        return new Object[][]{{data.get(6)}};
    }

    //Testcase1  : To verify by passing all the details and check SOP is created with the child SOP record effective which is created
    //              after pending QA approval stage in parent record.
//    @Test(dependsOnMethods ={"submitOrder"} )
    @Test(dataProvider = "getHappyWorkflowTestData",retryAnalyzer = Retry.class)
    public void verifyCreateSOPWithChildEffective(HashMap<String, String> input) throws InterruptedException {
        MyTaskPage myTaskPage = loginPage.loginApplication(input.get("email"), input.get("password"));
        //  MyTaskPage myTaskPage = new MyTaskPage(driver);
        DivisionPage divisionPage = myTaskPage.clickCreateSOP();
        //  DivisionPage divisionPage = new DivisionPage(driver);
        divisionPage.selectDivisionAndProject(input.get("division"));
        Assert.assertEquals("https://qssencedms.demoventuringdigitally.com/Sop_form", driver.getCurrentUrl());
        //activity performing on various states
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
        InitiateRevisionStage initiateRevisionStage = new InitiateRevisionStage(driver);
        initiateRevisionStage.inititateRevisionActivitySelection();
        SOPRevisionInitiateStage sopRevisionInitiateStage = new SOPRevisionInitiateStage(driver);
        driver.navigate().refresh();
        String activitySuccessMsg =sopRevisionInitiateStage.fillDataInDocInfoTabForSOPRevision();
        Assert.assertTrue(activitySuccessMsg.contains("Transaction Completed successfully"));

        PendingDocRevisionStage pendingDocRevisionStage = new PendingDocRevisionStage(driver);
        String successMsg =pendingDocRevisionStage.QAReviewCompleteActivitySelection();
        Assert.assertTrue(successMsg.contains("Transaction Completed successfully"));
        PendingReviewStage pendingReviewStage = new PendingReviewStage(driver);
        String formSuccessMsg=pendingReviewStage.QAReviewCompleteActivitySelection();
        Assert.assertTrue(formSuccessMsg.contains("Transaction Completed successfully"));
        PendingHODReviewStage pendingHODReviewStage = new PendingHODReviewStage(driver);
        String successText=pendingHODReviewStage.HODReviewCompleteActivitySelection();
        Assert.assertTrue(successText.contains("Transaction Completed successfully"));
        QAFinalStage qaFinalStage = new QAFinalStage(driver);
        String successTMessage=qaFinalStage.QAFinalApprovalCompleteActivitySelection();
        Assert.assertTrue(successTMessage.contains("Transaction Completed successfully"));
        //testcase related to effective stage-> like color change of stage
       // EffectiveStage effectiveStage = new EffectiveStage(driver);
       // effectiveStage.
       AwaitingRevisionStage awaitingRevisionStage = new AwaitingRevisionStage(driver);
       String obsoleteSuccessText = awaitingRevisionStage.obsoleteRevisionsActivitySelection();
      Assert.assertTrue(obsoleteSuccessText.contains("Obsolete Revisions is completed"));
      PendingObsoleteStage pendingObsoleteStage = new PendingObsoleteStage(driver);
      String obsoleteCompleteText =pendingObsoleteStage.obsoleteActivitySelection();
      Assert.assertTrue(obsoleteCompleteText.contains("Obsolete is completed"));
    }

    //Testcase2  : To verify by passing all the details and check SOP is created with the no child SOP records effective.
    @Test(dataProvider = "getHappyWorkflowTestData")
    public void verifyCreateSOPWithNoChildEffective(HashMap<String, String> input) throws InterruptedException {
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
        // Boolean cancelChildRecordRes =
        //  String closedText =
                pendingRevisionRequestStage.childClosedCancelInPendingRevisionReq();
                driver.navigate().refresh();
        // Assert.assertTrue(closedText.contains(" Close-Cancelled"));
        Assert.assertTrue(driver.getCurrentUrl().contains("https://qssencedms.demoventuringdigitally.com/sop_revision_view"));

    }

    //Testcase1  : To verify by passing all the details and check SOP is created with the child SOP record effective which is created
    //              after pending obsolete stage in parent record.
    @Test(dataProvider = "getHappyWorkflowTestData")
    public void verifyCreateSOPWith2ndChildEffective(HashMap<String, String> input) throws InterruptedException {
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
        String formSuccessMsg=pendingReviewStage.QAReviewCompleteActivitySelection();
        Assert.assertTrue(formSuccessMsg.contains("Transaction Completed successfully"));
        PendingHODReviewStage pendingHODReviewStage = new PendingHODReviewStage(driver);
        String successText=pendingHODReviewStage.HODReviewCompleteActivitySelection();
        Assert.assertTrue(successText.contains("Transaction Completed successfully"));
        QAFinalStage qaFinalStage = new QAFinalStage(driver);
        String successTMessage=qaFinalStage.QAFinalApprovalCompleteActivitySelection();
        Assert.assertTrue(successTMessage.contains("Transaction Completed successfully"));
        //testcase related to effective stage-> like color change of stage
        // EffectiveStage effectiveStage = new EffectiveStage(driver);
        // effectiveStage.
        AwaitingRevisionStage awaitingRevisionStage = new AwaitingRevisionStage(driver);
        //new SOP child created
        String obsoleteStageChildNotEffectiveText = awaitingRevisionStage.newRevisionsActivitySelection();
        Assert.assertTrue(obsoleteStageChildNotEffectiveText.contains("You can not perform any activity while the child is not effective."));

        driver.navigate().refresh();

        //childEffective
        pendingRevisionRequestStage.child2EffectiveInPendingRevisionReq();
       // SOPRevisionInitiateStage sopRevisionInitiateStage = new SOPRevisionInitiateStage(driver);
        driver.navigate().refresh();
        String activitySuccessMsg2 =sopRevisionInitiateStage.fillDataInDocInfoTabForSOPRevision();
        Assert.assertTrue(activitySuccessMsg2.contains("Transaction Completed successfully"));
        //PendingDocRevisionStage pendingDocRevisionStage = new PendingDocRevisionStage(driver);
        String successMsg2 =pendingDocRevisionStage.QAReviewCompleteActivitySelection();
        Assert.assertTrue(successMsg2.contains("Transaction Completed successfully"));
        //PendingReviewStage pendingReviewStage = new PendingReviewStage(driver);
        String formSuccessMsg2=pendingReviewStage.QAReviewCompleteActivitySelection();
        Assert.assertTrue(formSuccessMsg2.contains("Transaction Completed successfully"));
        //PendingHODReviewStage pendingHODReviewStage = new PendingHODReviewStage(driver);
        String successText2=pendingHODReviewStage.HODReviewCompleteActivitySelection();
        Assert.assertTrue(successText2.contains("Transaction Completed successfully"));
        //QAFinalStage qaFinalStage = new QAFinalStage(driver);
        String successTMessage2=qaFinalStage.QAFinalApprovalCompleteActivitySelection();
        Assert.assertTrue(successTMessage2.contains("Transaction Completed successfully"));

    }
}