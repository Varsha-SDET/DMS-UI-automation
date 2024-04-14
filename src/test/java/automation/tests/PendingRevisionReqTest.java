package automation.tests;

import automation.pageobjects.*;
import automation.testcomponents.TestUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class PendingRevisionReqTest extends TestUtils {
    @DataProvider
    public Object[][] getTestData() throws IOException {
        List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir") + "//src//test//java//automation/data//Credentials.json");
        return new Object[][]{{data.get(6)}};
    }

    //Testcase1  : To verify child closed in revision request.
    @Test(dataProvider = "getTestData")
    public void verifyChildClosedInPendingRevisionReq(HashMap<String, String> input) throws InterruptedException {
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

    }
}
