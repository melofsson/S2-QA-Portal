package se.soprasteria.s2qaportal.repository;


import se.soprasteria.s2qaportal.model.*;

import java.sql.SQLException;
import java.util.List;

public interface ITestCaseRepository {

    List<TestRunResult> getAllTestName(String testName, String jobName) throws SQLException;

    void addTestCases(List<TestCase> testCases) throws SQLException;

    void addTestJobs(List<TestJob> testJobs) throws SQLException;

    void addTestRuns(List<TestRun> testRuns) throws SQLException;

    List<TestCase> getTestCases() throws SQLException;

    TestRun getTestRun(int testRunID) throws SQLException;

    List<String> getDates(int group) throws SQLException;
    List<String> getDates(int group, String fromDate, boolean getOlderRecords) throws SQLException;

    List<TestCaseData> getTestCaseData(int testCaseID, int groupID, List<String> dates) throws SQLException;
}

