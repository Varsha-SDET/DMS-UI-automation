package automation.tests;

import automation.pageobjects.DivisionPage;
import automation.pageobjects.DocumentInformationPage;
import automation.pageobjects.MyTaskPage;
import automation.testcomponents.TestUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


/*
 *@className - DocumentInformationTabTest
 * @classObjective- To define all the test cases required for document information tab.
 */
public class DocumentInformationTabTest extends TestUtils {
    @DataProvider
    public Object[][] getTestData() throws IOException {
        //Data provider for test case
        List<HashMap<String,String>> data = getJsonDataToMap(System.getProperty("user.dir") + "//src//test//java//automation/data//Credentials.json");
        return new Object[][]{{data.get(4)}};
    }

    //Testcase1  : To verify by checking cancel button.
    @Test(dataProvider = "getTestData")
    public void verifyClickingCancelButton(HashMap<String,String> input) throws InterruptedException {
        MyTaskPage myTaskPage= loginPage.loginApplication(input.get("email"),input.get("password"));
        //  MyTaskPage myTaskPage = new MyTaskPage(driver);
        DivisionPage divisionPage = myTaskPage.clickCreateSOP();
        //  DivisionPage divisionPage = new DivisionPage(driver);
        divisionPage.selectDivisionClickCancelButton(input.get("division"));
        // Assert.ass("https://qssencedms.demoventuringdigitally.com/Sop_form","URL is ");
        Assert.assertEquals("https://qssencedms.demoventuringdigitally.com/mytask",driver.getCurrentUrl());

    }

    //Testcase2  : To verify by checking all the mandatory fields have validations.
    @Test(dataProvider = "getTestData")
    public void verifyMandatoryFieldsForDivisionTab(HashMap<String,String> input) throws InterruptedException {
        MyTaskPage myTaskPage= loginPage.loginApplication(input.get("email"),input.get("password"));
        //  MyTaskPage myTaskPage = new MyTaskPage(driver);
        DivisionPage divisionPage = myTaskPage.clickCreateSOP();
        //  DivisionPage divisionPage = new DivisionPage(driver);
        String errorText =divisionPage.checkMendatoryValues(input.get("division"));
        Assert.assertEquals(errorText,"Please Select a Project...");

    }

    //Testcase3  : To verify by passing all the details and check SOP is created.
    @Test(dataProvider = "getTestData")
    public void verifyCancelActivity(HashMap<String,String> input) {
        MyTaskPage myTaskPage= loginPage.loginApplication(input.get("email"),input.get("password"));
        //  MyTaskPage myTaskPage = new MyTaskPage(driver);
        DivisionPage divisionPage = myTaskPage.clickCreateSOP();
        //  DivisionPage divisionPage = new DivisionPage(driver);
        divisionPage.selectDivisionAndProject(input.get("division"));
        DocumentInformationPage documentInformationPage = new DocumentInformationPage(driver);
        String cancelActivityText =documentInformationPage.cancelActivitySelection();
        Assert.assertTrue(cancelActivityText.contains("Close-Cancelled"));

    }

    //Testcase4  : To verify by passing all the details and check SOP is created.
    @Test(dataProvider = "getTestData")
    public void verifyMandatoryField(HashMap<String,String> input) {
        MyTaskPage myTaskPage= loginPage.loginApplication(input.get("email"),input.get("password"));
        //  MyTaskPage myTaskPage = new MyTaskPage(driver);
        DivisionPage divisionPage = myTaskPage.clickCreateSOP();
        //  DivisionPage divisionPage = new DivisionPage(driver);
        divisionPage.selectDivisionAndProject(input.get("division"));
        DocumentInformationPage documentInformationPage = new DocumentInformationPage(driver);
        String errorText =documentInformationPage.withoutMandatoryFieldPassedInDocInfo();
        Assert.assertTrue(errorText.contains("Title is required."));

    }


}
