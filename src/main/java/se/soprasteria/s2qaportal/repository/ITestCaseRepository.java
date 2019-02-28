package se.soprasteria.s2qaportal.repository;


import se.soprasteria.s2qaportal.model.TestRunResult;

import java.sql.SQLException;
import java.util.List;

public interface ITestCaseRepository {

    List<TestRunResult> getAllTestName(String testName, String jobName) throws SQLException;
}
