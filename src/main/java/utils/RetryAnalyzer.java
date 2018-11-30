package utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 Retry analyzer with pauses to increase tests stability.
 */

public class RetryAnalyzer implements IRetryAnalyzer {

    int counter = 0;
    int retryLimit = 10;

    @Override
    public boolean retry(ITestResult result) {
        if(counter < retryLimit) {
            //Pause will be added for further test runs if the first run failed.
            if (counter > 0) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            counter++;
            return true;
        }
        return false;
    }
}