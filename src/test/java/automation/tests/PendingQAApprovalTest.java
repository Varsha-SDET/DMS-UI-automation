package automation.tests;

import automation.pageobjects.*;
import automation.testcomponents.TestUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class PendingQAApprovalTest extends TestUtils {
    @DataProvider
    public Object[][] getTestData() throws IOException {
        List<HashMap<String,String>> data = getJsonDataToMap(System.getProperty("user.dir") + "//src//test//java//automation/data//Credentials.json");
        return new Object[][]{{data.get(6)}};
        //  return new Object[][]{{data.get(3)}, {data.get(4)}};
    }

    //Testcase1  : To verify by passing all the details and check SOP is created.
    @Test(dataProvider = "getTestData")
    public void verifyCancelQAApprovalActivity(HashMap<String,String> input) throws InterruptedException {
        MyTaskPage myTaskPage= loginPage.loginApplication(input.get("email"),input.get("password"));
        DivisionPage divisionPage = myTaskPage.clickCreateSOP();
        divisionPage.selectDivisionAndProject(input.get("division"));
        DocumentInformationPage documentInformationPage = new DocumentInformationPage(driver);
        String successMessage =documentInformationPage.happyWorkflowOfDocInfo(input);
        Assert.assertTrue(successMessage.contains("Submit is completed."));
      //  documentInformationPage.submitActivitySelection();
        QAPendingApprovalStage qaPendingApprovalStage = new QAPendingApprovalStage(driver);
        String errorText =qaPendingApprovalStage.performCancelActivity();
        Assert.assertTrue(errorText.contains(" Close-Cancelled"));
    }

    //Testcase2  : To verify by passing all the details and check SOP is created.
    @Test(dataProvider = "getTestData")
    public void verifyRejectQAApprovalActivity(HashMap<String,String> input) throws InterruptedException {
        MyTaskPage myTaskPage= loginPage.loginApplication(input.get("email"),input.get("password"));
        DivisionPage divisionPage = myTaskPage.clickCreateSOP();
        divisionPage.selectDivisionAndProject(input.get("division"));
        DocumentInformationPage documentInformationPage = new DocumentInformationPage(driver);
        documentInformationPage.submitActivitySelection();
        String successMessage =documentInformationPage.happyWorkflowOfDocInfo(input);
        Assert.assertTrue(successMessage.contains("Submit is completed."));
        QAPendingApprovalStage qaPendingApprovalStage = new QAPendingApprovalStage(driver);
     //   String errorText =qaPendingApprovalStage.performRejectActivity();
       // Assert.assertTrue(errorText.contains(" Close-Cancelled"));
    }

    //Testcase3   : cancel button click on e signature form
    @Test(dataProvider = "getTestData")
    public void verifyEsignatureCancelFunctionality(HashMap<String,String> input) throws InterruptedException {
        MyTaskPage myTaskPage= loginPage.loginApplication(input.get("email"),input.get("password"));
        DivisionPage divisionPage = myTaskPage.clickCreateSOP();
        divisionPage.selectDivisionAndProject(input.get("division"));
        DocumentInformationPage documentInformationPage = new DocumentInformationPage(driver);
        String successMessage =documentInformationPage.happyWorkflowOfDocInfo(input);
        Assert.assertTrue(successMessage.contains("Submit is completed."));
        QAPendingApprovalStage qaPendingApprovalStage = new QAPendingApprovalStage(driver);
       // qaPendingApprovalStage.performQAPendingApprovalActivity();
        Boolean eSignatureInvisibilityStatus= qaPendingApprovalStage.cancelClickOnElectronicSignatureForm();
        Assert.assertEquals(eSignatureInvisibilityStatus,false);
    }

//    //Testcase3   : cancel button click on e signature form
//    @Test(dataProvider = "getTestData")
//    public void verifyEsignatureCloseButton(HashMap<String,String> input) throws InterruptedException {
//        MyTaskPage myTaskPage= loginPage.loginApplication(input.get("email"),input.get("password"));
//        DivisionPage divisionPage = myTaskPage.clickCreateSOP();
//        divisionPage.selectDivisionAndProject(input.get("division"));
//        DocumentInformationPage documentInformationPage = new DocumentInformationPage(driver);
//        String successMessage =documentInformationPage.happyWorkflowOfDocInfo(input);
//        Assert.assertTrue(successMessage.contains("Submit is completed."));
//        QAPendingApprovalStage qaPendingApprovalStage = new QAPendingApprovalStage(driver);
//        // qaPendingApprovalStage.performQAPendingApprovalActivity();
//        Boolean eSignatureInvisibilityStatus= qaPendingApprovalStage.closeButtonInEsignatureForm();
//
//        Assert.assertEquals(eSignatureInvisibilityStatus,false);
//    }

    //Testcase3   : cancel button click on e signature form
//    @Test(dataProvider = "getTestData")
//    public void verifyEsignatureWithInvalidCredentials(HashMap<String,String> input) throws InterruptedException {
//        MyTaskPage myTaskPage= loginPage.loginApplication(input.get("email"),input.get("password"));
//        DivisionPage divisionPage = myTaskPage.clickCreateSOP();
//        divisionPage.selectDivisionAndProject(input.get("division"));
//        DocumentInformationPage documentInformationPage = new DocumentInformationPage(driver);
//        String successMessage =documentInformationPage.happyWorkflowOfDocInfo(input);
//        Assert.assertTrue(successMessage.contains("Submit is completed."));
//        QAPendingApprovalStage qaPendingApprovalStage = new QAPendingApprovalStage(driver);
//        // qaPendingApprovalStage.performQAPendingApprovalActivity();
//        String eSignatureInvisibilityStatus= qaPendingApprovalStage.invalidCredentialsSignInEsignForm();
//        Assert.assertEquals(eSignatureInvisibilityStatus,"E-signature not matched.");
//    }

}
