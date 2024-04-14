package automation.testcomponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
/*
 *@className - Retry
 * @classObjective- To define the method so that re run can be done fo the test case required.
 */
public class Retry implements IRetryAnalyzer {
    int count =0;
    //re-run count
    int maxTry=3;
    //how many times we want to rerun test

    @Override
    //test metadata present in result variable
    public boolean retry(ITestResult result ) {
        if(count<maxTry){
            count++;
            return true;
        }
        return false;
    }

}
