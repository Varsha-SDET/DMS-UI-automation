package automation.tests;

import automation.testcomponents.TestUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/*
 *@className - LoginTest
 * @classObjective- To define all the test cases required for login page.
 */
public class LoginTest extends TestUtils {
    //Testcase 1: login with valid credentials + time zone and language select.
    @Test(dataProvider = "getValidData")
    public void loginWithValidCredentials(HashMap<String,String> input){
        loginPage.loginApplication(input.get("email"),input.get("password"));
        // Assert.assertEquals();
        Assert.assertEquals(driver.getCurrentUrl(),"https://qssencedms.demoventuringdigitally.com/mytask");
    }

    @DataProvider
    public Object[][] getInvalidData() throws IOException {
        List<HashMap<String,String>> data = getJsonDataToMap(System.getProperty("user.dir") + "//src//test//java//automation/data//Credentials.json");
        return new Object[][]{{data.get(1)}};
        //  return new Object[][]{{data.get(3)}, {data.get(4)}};
    }

    @DataProvider
    public Object[][] getValidData() throws IOException {
        List<HashMap<String,String>> data = getJsonDataToMap(System.getProperty("user.dir") + "//src//test//java//automation/data//Credentials.json");
        return new Object[][]{{data.get(0)}};
        //  return new Object[][]{{data.get(3)}, {data.get(4)}};
    }

    //Testcase 2: login with invalid credentials -> password email empty, password empty email valid,
    //            invalid email password, empty email password.
    @Test(dataProvider = "getInvalidData")
    public void loginWithInvalidCredentials(HashMap<String,String> input){
      String errorMessage =loginPage.invalidLoginApplication(input.get("email"),input.get("password"));
        System.out.println(errorMessage);
      Assert.assertEquals(errorMessage,"Login failed.");

    }

    //Testcase 3: To check the message is displayed on successful logout
    @Test(dataProvider = "getValidData")
    public void verifyLogout(HashMap<String,String> input){
        loginPage.loginApplication(input.get("email"),input.get("password"));
        // Assert.assertEquals();
        Assert.assertEquals(driver.getCurrentUrl(),"https://qssencedms.demoventuringdigitally.com/mytask");
        String logoutMsg=loginPage.checkLogout();
        Assert.assertTrue(logoutMsg.contains("Logout Successfully"));
    }
}
