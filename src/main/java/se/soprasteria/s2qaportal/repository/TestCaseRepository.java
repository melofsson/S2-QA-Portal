package se.soprasteria.s2qaportal.repository;


import se.soprasteria.s2qaportal.Database.DBConnection;
import se.soprasteria.s2qaportal.model.TestRunResult;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TestCaseRepository implements ITestCaseRepository {

    private DBConnection dbConnection = new DBConnection();

    @Override
    public List<TestRunResult> getAllTestName(String testName, String jobName) throws SQLException {

        List<TestRunResult> testList = new ArrayList<>();
        String SQL = "SELECT * FROM TEST_RUN where test_name = '" + testName + "' AND JOB_NAME = '" + jobName + "' ";

        Statement pstmt = dbConnection.getConnection().createStatement();
        ResultSet rs = pstmt.executeQuery(SQL);

        while (rs.next()) {
            TestRunResult testRunResult = new TestRunResult();
            testRunResult.setTestRunId(rs.getInt("TEST_RUNID"));
            testRunResult.setBuildNumber(rs.getInt("BUILD_NUMBER"));
            testRunResult.setDuration(rs.getInt("DURATION"));
            testRunResult.setJobName(rs.getString("JOB_NAME"));
            testRunResult.setStatus(rs.getString("STATUS"));
            testRunResult.setTestName(rs.getString("TEST_NAME"));
            testList.add(testRunResult);
        }
        return testList;
    }
}
