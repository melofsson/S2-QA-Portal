package se.soprasteria.s2qaportal.services.mocked;

import com.google.gson.Gson;
import se.soprasteria.s2qaportal.model.FailedTest;
import se.soprasteria.s2qaportal.model.TestCase;
import se.soprasteria.s2qaportal.model.TestJob;
import se.soprasteria.s2qaportal.model.TestRun;
import se.soprasteria.s2qaportal.model.enums.ErrorCase;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class MockedDataCollector {


    public TestCase getPreviouslyStoredTestCaseFromArray(String testName, List<TestCase> testCases) {
        for (TestCase testCase : testCases) {
            if (testCase.getName().equalsIgnoreCase(testName)) {
                return testCase;
            }
        }
        return null;
    }

    public TestJob getPreviouslyStoreTestJobFromArray(List<TestJob> testJobs, List<TestCase> testCases) {

        for (TestJob testJob : testJobs) {
            for (TestCase testCase : testCases) {
                if (testCase.getTestJobs().contains(testJob)) {
                    return testJob;
                }
            }
        }
        return null;
    }

    public boolean isTestCaseAlreadyInArray(String testName, List<TestCase> testCases) {

        boolean foundMatch = false;
        for (TestCase testCase : testCases) {
            foundMatch = testCase.getName().equalsIgnoreCase(testName);
        }
        return foundMatch;
    }

    public List getMockedTestRuns() {
        List<TestRun> testRuns = new ArrayList<>();
        testRuns.add(new TestRun("Job_Win_Chrome", 1, "Test1", 2000, "SKIPPED"));
        testRuns.add(new TestRun("Job_Win_Chrome", 1, "Test2", 1200, "PASSED"));
        testRuns.add(new TestRun("Job_Win_Chrome", 1, "Test3", 5787, "FAILED"));
        testRuns.add(new TestRun("Job_Win_Chrome", 1, "Test4", 3354, "PASSED"));
        testRuns.add(new TestRun("Job_Win_Chrome", 1, "Test5", 23, "FAILED"));
        testRuns.add(new TestRun("Job_Win_Chrome", 1, "Test6", 235, "SKIPPED"));
        testRuns.add(new TestRun("Job_Win_Chrome", 1, "Test7", 4564, "SKIPPED"));
        testRuns.add(new TestRun("Job_Win_Chrome", 1, "Test8", 3445, "PASSED"));

        testRuns.add(new TestRun("Job_Android", 1, "Test1", 2000, "SKIPPED"));
        testRuns.add(new TestRun("Job_Android", 1, "Test2", 2000, "PASSED"));
        testRuns.add(new TestRun("Job_Android", 1, "Test3", 2000, "FAILED"));
        testRuns.add(new TestRun("Job_Android", 1, "Test4", 2000, "FAILED"));

        testRuns.add(new TestRun("Job_Win_Edge", 1, "TEST15", 2000, "PASSED"));
        testRuns.add(new TestRun("Job_Win_Edge", 1, "TEST16", 2000, "PASSED"));
        testRuns.add(new TestRun("Job_Win_Edge", 1, "TEST17", 2000, "PASSED"));
        testRuns.add(new TestRun("Job_Win_Edge", 1, "TEST18", 2000, "PASSED"));
        testRuns.add(new TestRun("Job_Win_Edge", 1, "TEST19", 2000, "PASSED"));
        testRuns.add(new TestRun("Job_Win_Edge", 1, "TEST20", 2000, "PASSED"));


        testRuns.add(new TestRun("Job_Win_Chrome", 2, "Test1", 2000, "SKIPPED"));
        testRuns.add(new TestRun("Job_Win_Chrome", 2, "Test2", 1200, "PASSED"));
        testRuns.add(new TestRun("Job_Win_Chrome", 2, "Test3", 5787, "FAILED"));
        testRuns.add(new TestRun("Job_Win_Chrome", 2, "Test4", 3354, "PASSED"));
        testRuns.add(new TestRun("Job_Win_Chrome", 2, "Test5", 23, "FAILED"));
        testRuns.add(new TestRun("Job_Win_Chrome", 2, "Test6", 235, "SKIPPED"));
        testRuns.add(new TestRun("Job_Win_Chrome", 2, "Test7", 4564, "SKIPPED"));
        testRuns.add(new TestRun("Job_Win_Chrome", 2, "Test8", 3445, "PASSED"));

        testRuns.add(new TestRun("Job_Android", 2, "Test1", 2000, "SKIPPED"));
        testRuns.add(new TestRun("Job_Android", 2, "Test2", 2000, "PASSED"));
        testRuns.add(new TestRun("Job_Android", 2, "Test3", 2000, "FAILED"));
        testRuns.add(new TestRun("Job_Android", 2, "Test4", 2000, "FAILED"));

        testRuns.add(new TestRun("Job_Win_Edge", 2, "TEST15", 2000, "PASSED"));
        testRuns.add(new TestRun("Job_Win_Edge", 2, "TEST16", 2000, "PASSED"));
        testRuns.add(new TestRun("Job_Win_Edge", 2, "TEST17", 2000, "PASSED"));
        testRuns.add(new TestRun("Job_Win_Edge", 2, "TEST18", 2000, "PASSED"));
        testRuns.add(new TestRun("Job_Win_Edge", 2, "TEST19", 2000, "PASSED"));
        testRuns.add(new TestRun("Job_Win_Edge", 2, "TEST20", 2000, "PASSED"));

        testRuns.add(new TestRun("Job_Win_Chrome", 3, "Test1", 2000, "PASSED"));
        testRuns.add(new TestRun("Job_Win_Chrome", 3, "Test2", 1200, "FAILED"));
        testRuns.add(new TestRun("Job_Win_Chrome", 3, "Test3", 5787, "SKIPPED"));
        testRuns.add(new TestRun("Job_Win_Chrome", 3, "Test4", 3354, "PASSED"));
        testRuns.add(new TestRun("Job_Win_Chrome", 3, "Test5", 23, "PASSED"));
        testRuns.add(new TestRun("Job_Win_Chrome", 3, "Test6", 235, "FAILED"));
        testRuns.add(new TestRun("Job_Win_Chrome", 3, "Test7", 4564, "SKIPPED"));
        testRuns.add(new TestRun("Job_Win_Chrome", 3, "Test8", 3445, "SKIPPED"));

        testRuns.add(new TestRun("Job_Android", 3, "Test1", 2000, "PASSED"));
        testRuns.add(new TestRun("Job_Android", 3, "Test2", 2000, "PASSED"));
        testRuns.add(new TestRun("Job_Android", 3, "Test3", 2000, "PASSED"));
        testRuns.add(new TestRun("Job_Android", 3, "Test4", 2000, "PASSED"));
        testRuns.add(new TestRun("Job_Win_Edge", 3, "TEST15", 2000, "FAILED"));
        testRuns.add(new TestRun("Job_Win_Edge", 3, "TEST16", 2000, "FAILED"));
        testRuns.add(new TestRun("Job_Win_Edge", 3, "TEST17", 2000, "SKIPPED"));
        testRuns.add(new TestRun("Job_Win_Edge", 3, "TEST18", 2000, "SKIPPED"));
        testRuns.add(new TestRun("Job_Win_Edge", 3, "TEST19", 2000, "PASSED"));
        testRuns.add(new TestRun("Job_Win_Edge", 3, "TEST20", 2000, "PASSED"));

        return testRuns;


    }


    public FailedTest[] readFailedTestsFromJSON() {
        Gson gson = new Gson();
        try (Reader reader = new FileReader("/Users/framework-dev/Git/S2-QA-Portal/src/main/resources/failedtests.json")) {
            return gson.fromJson(reader, FailedTest[].class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ErrorCase> getTestErrors() {
        FailedTest[] failedTests = readFailedTestsFromJSON();
        List<ErrorCase> errorCases = new ArrayList<>();
        if (failedTests.length != 0) {
            for (FailedTest failedTest : failedTests) {
                boolean addToArray = true;
                for (ErrorCase errorCase : errorCases) {
                    if (isSimilarErrorMessage(errorCase.getErrorMessage(), failedTest.getErrorMessage())) {
                        errorCase.addFailingTest(failedTest.getTestName());
                        errorCase.addFailingPlatform(failedTest.getPlatforms());
                        addToArray = false;
                        break;
                    }
                }
                if (addToArray) errorCases.add(new ErrorCase(failedTest));
            }
        }
        return errorCases;
    }

    protected boolean isSimilarErrorMessage(String errorMessage1, String errorMessage2) {
        List<String> differences = new ArrayList<>();
        List<String> errorMessageArray1 = splitStringIntoLines(errorMessage1);
        List<String> errorMessageArray2 = splitStringIntoLines(errorMessage2);
        if (errorMessageArray1.size() > errorMessageArray2.size()) {
            differences = errorMessageArray1;
            differences.removeAll(errorMessageArray2);
        } else {
            differences = errorMessageArray2;
            differences.removeAll(errorMessageArray1);
        }

        if (errorMessageArray1.size() == 1 || errorMessageArray2.size() == 1) {
            return differences.size() == 0;
        } else if (errorMessageArray1.size() == 2 || errorMessageArray2.size() == 2) {
            return (differences.size() <= 1);
        } else {
            return (differences.size() <= 3);
        }
    }

    protected List<String> splitStringIntoLines(String errorMessage1) {
        List<String> errorMessageArray = new ArrayList<>();
        String lineToAdd;
        while (true) {
            if (errorMessage1.length() >= 75) {
                int indexOfWhiteSpace;
                if (errorMessage1.lastIndexOf(" ") < 75) {
                    indexOfWhiteSpace = errorMessage1.lastIndexOf(" ");
                } else {
                    indexOfWhiteSpace = errorMessage1.indexOf(" ", 75);
                }
                lineToAdd = errorMessage1.substring(0, indexOfWhiteSpace);
                errorMessage1 = errorMessage1.substring(lineToAdd.length());
            } else {
                lineToAdd = errorMessage1;
                errorMessageArray.add(lineToAdd);
                break;
            }
            errorMessageArray.add(lineToAdd);
        }
        return errorMessageArray;
    }

    public List<TestCase> getTestCases(List<TestRun> testRuns) {
        List<TestCase> testCases = new ArrayList<>();
        for (TestRun testRun : testRuns) {
            TestCase testCase = null;
            if (testCases.size() > 0) {
                testCase = getPreviouslyStoredTestCaseFromArray(testRun.getTestName(), testCases);
                if (testCase != null) {
                    testCase.addTestJobAndBuildData(testRun);
                } else {
                    testCase = new TestCase(testRun.getTestName(),
                            testRun.getTestName());
                    testCase.addTestJobAndBuildData(testRun);
                    //System.out.println("Added new testCase:" + testCase.getName());
                    testCases.add(testCase);
                }
            } else {
                testCase = new TestCase(testRun.getTestName(),
                        testRun.getTestName());
                testCase.addTestJobAndBuildData(testRun);
                //System.out.println("Added new testCase:" + testCase.getName());
                testCases.add(testCase);
            }
        }
        return testCases;
    }
}
