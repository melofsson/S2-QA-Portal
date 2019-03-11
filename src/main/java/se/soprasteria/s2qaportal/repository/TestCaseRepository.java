package se.soprasteria.s2qaportal.repository;


import se.soprasteria.s2qaportal.Database.DBConnection;
import se.soprasteria.s2qaportal.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestCaseRepository implements ITestCaseRepository {

    private DBConnection dbConnection = new DBConnection();

    @Override
    public List<TestRunResult> getAllTestName(String testName, String jobName) throws SQLException {

        List<TestRunResult> testList = new ArrayList<>();
        String SQL = "SELECT * FROM TEST_RUN where test_name = '" + testName + "' AND JOB_NAME = '" + jobName + "' ";

        Connection connection = dbConnection.getConnection();
        Statement pstmt = connection.createStatement();
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

        connection.close();
        return testList;
    }

    @Override
    public List<TestCase> getTestCases() throws SQLException {

        List<TestCase> testCases = new ArrayList<>();

        String SQL = "SELECT * FROM TEST_CASE";
        Connection connection = dbConnection.getConnection();
        Statement pstmt = connection.createStatement();
        System.out.println("Executing query");
        ResultSet rs = pstmt.executeQuery(SQL);

        while (rs.next()) {
            TestCase testCase = new TestCase(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("classPath"));
            testCases.add(testCase);
        }
        dbConnection.closeConnection(connection);
        return testCases;
    }

    @Override
    public void addTestCases(List<TestCase> testCases) throws SQLException {

        System.out.println("***** SAVING TEST JOBS TO DATABASE *****");
        StringBuilder SQL = new StringBuilder();
        System.out.println("Appending data");

        SQL.append("INSERT INTO TEST_CASE(name, classpath)");
        SQL.append("VALUES");
        int count = testCases.size();
        for (TestCase testCase : testCases) {
            SQL.append(testCase.getSQLValueString()).append(",");
        }
        SQL.setLength(SQL.length()-1);
        SQL.append("ON CONFLICT(name)");
        SQL.append("DO NOTHING;");
        executeDatabaseRequest(SQL);
    }

    @Override
    public void addTestJobs(List<TestJob> testJobs) throws SQLException {
        System.out.println("***** SAVING TEST JOBS TO DATABASE *****");
        StringBuilder SQL = new StringBuilder();
        System.out.println("Appending data");

        SQL.append("INSERT INTO TEST_JOB(name, jenkins_url, os, device, target_platform)");
        SQL.append("VALUES");
        for (TestJob testJob : testJobs) {
            SQL.append(testJob.getSQLValueString()).append(",");
        }
        SQL.setLength(SQL.length()-1);
        SQL.append("ON CONFLICT(name)");
        SQL.append("DO NOTHING;");
        executeDatabaseRequest(SQL);

            // return id;
        }

    public void executeDatabaseRequest(StringBuilder SQL) throws SQLException {
        Connection connection = dbConnection.getConnection();
        Statement pstmt = connection.createStatement();
        System.out.println("Executing query");
        pstmt.executeUpdate(SQL.toString());
        dbConnection.closeConnection(connection);
    }

    @Override
    public void addTestRuns(List<TestRun> testRuns) throws SQLException {
        final String INSERT_STATEMENT = "INSERT INTO TEST_RUN(job_id, testcase_id, build_number, video_url, image_url, date, duration, error_details, error_stacktrace, status_id, skipped_message) VALUES";
        final String ON_CONFLICT_STATEMENT = "ON CONFLICT(job_ID, build_number, testcase_id) DO NOTHING;";

        System.out.println("****** SAVING TEST RUNS TO DATABASE ******");
        StringBuilder SQL = new StringBuilder();
        System.out.println("Appending data");
        int stopCounter = 0;
            SQL.append(INSERT_STATEMENT);
            for (TestRun testRun : testRuns) {
                if (stopCounter < 500) {
                    SQL.append(testRun.getSQLValueString()).append(",");
                    stopCounter++;
                } else {
                    SQL.setLength(SQL.length()-1);
                    SQL.append(ON_CONFLICT_STATEMENT);
                    System.out.println("Prepared "+stopCounter+" inserts");
                    executeDatabaseRequest(SQL);
                    stopCounter = 0;
                    SQL = new StringBuilder();
                    SQL.append(INSERT_STATEMENT);
                }
            }
            if (stopCounter != 0){
                SQL.setLength(SQL.length()-1);
                SQL.append(ON_CONFLICT_STATEMENT);
                System.out.println("Prepared "+stopCounter+" inserts");
                executeDatabaseRequest(SQL);
                }
    }

    @Override
    public List<String> getDates(int group, String fromDate, boolean getOlderRecords) throws SQLException {
        System.out.println("READING DATE DATA FROM DATABASE...");
        String SQL = "";
        List<String> dates = new ArrayList<>();
        if (getOlderRecords) {
            SQL = "SELECT DISTINCT date, " +
                    "job.group_id as group_id " +
                    "FROM TEST_RUN " +
                    "INNER JOIN TEST_JOB job ON TEST_RUN.job_id = job.id " +
                    "WHERE group_id = " + group + " AND date > " + fromDate + "  ORDER BY date DESC FETCH FIRST 7 ROWS ONLY";
        }
        Connection connection = dbConnection.getConnection();
        Statement pstmt = connection.createStatement();
        ResultSet result = pstmt.executeQuery(SQL);

        while (result.next()){
            String date = result.getDate("date").toString();
            String day = date.substring(date.lastIndexOf("-") + 1);
            if (day.startsWith("0")) day = day.substring(1);
            String month = date.substring(date.indexOf("-") + 1, date.lastIndexOf("-"));
            if (month.startsWith("0")) month = month.substring(1);
            dates.add(day + "/" + month);
        }
        return dates;
    }

    @Override
    public TestRun getTestRun(int testRunID) throws SQLException {
        System.out.println("READING TEST RUN DATA FROM DATABASE...");
        String SQL = "SELECT * FROM TEST_RUN " +
                    "WHERE id = " + testRunID + ";";

        Connection connection = dbConnection.getConnection();
        Statement pstmt = connection.createStatement();
        ResultSet result = pstmt.executeQuery(SQL);

        result.next();
        TestRun testRun = new TestRun();
        testRun.setBuildNumber(result.getInt("build_number"));
        testRun.setDate(result.getDate("date").toString());
        testRun.setDuration(result.getInt("duration"));
        testRun.setStatusID(result.getInt("status_id"));
        testRun.setErrorDetails(result.getString("error_details"));
        testRun.setErrorStacktrace(result.getString("error_stacktrace"));

        return testRun;
    }

    @Override
    public List<String> getDates(int group) throws SQLException {
        System.out.println("READING DATE DATA FROM DATABASE...");
        String SQL;
        List<String> dates = new ArrayList<>();
            SQL = "SELECT DISTINCT date, " +
                    "job.group_id as group_id " +
                    "FROM TEST_RUN " +
                    "INNER JOIN TEST_JOB job ON TEST_RUN.job_id = job.id " +
                    "WHERE group_id = " + group + " ORDER BY date DESC FETCH FIRST 7 ROWS ONLY";
        Connection connection = dbConnection.getConnection();
        Statement pstmt = connection.createStatement();
        ResultSet result = pstmt.executeQuery(SQL);

        while (result.next()){
            dates.add(result.getDate("date").toString());
        }
        return dates;
    }

    @Override
    public List<TestCaseData> getTestCaseData(int testCaseID, int groupID, List<String> dates) throws SQLException {
        System.out.println("READING TEST CASE DATA FROM DATABASE...");
        List<TestCaseData> testCaseDataList = new ArrayList<>();
        TestCaseData testCaseData = null;
        TestJob testJob = null;
        List<HashMap<String,String>> listOfTestRuns = new ArrayList<>();
        HashMap<String,String> testRun = new HashMap();

            String SQL = "SELECT DISTINCT job.name as job_name, " +
                    "job.group_id as group_id, " +
                    "job.target_platform as platform, " +
                    "job.os as os, " +
                    "job.id as job_id, " +
                    "job.device as device, " +
                    "TEST_RUN.id, " +
                    "status.status as status, " +
                    "date, " +
                    "skipped_message " +
                    "FROM TEST_RUN " +
                    "INNER JOIN TEST_JOB job ON TEST_RUN.job_id = job.id " +
                    "INNER JOIN TEST_STATUS status ON TEST_RUN.status_id = status.id " +
                    "WHERE testcase_id=" +testCaseID + " " +
                    "AND group_id=" +groupID + " " +
                    "AND (date='" +dates.get(0) + "' " +
                    "OR date='" +dates.get(1) + "' " +
                    "OR date='" +dates.get(2) + "' " +
                    "OR date='" +dates.get(3) + "' " +
                    "OR date='" +dates.get(4) + "' " +
                    "OR date='" +dates.get(5) + "' " +
                    "OR date='" +dates.get(6) + "') " +
                    "ORDER BY job_id, date DESC;";

        Connection connection = dbConnection.getConnection();
        Statement pstmt = connection.createStatement();
        ResultSet result = pstmt.executeQuery(SQL);

        int dateCounter = 0;
        String lastCatchedJobName = "";
        while (result.next()) {
            String currentJobName = result.getString("job_name");
            if (!currentJobName.contentEquals(lastCatchedJobName)) {
                if (testCaseData != null) {
                    testCaseData.setTestRunScheduleDataList(getFormattedListForCalendar(listOfTestRuns, dates));
                    testCaseDataList.add(testCaseData);
                    listOfTestRuns = new ArrayList<>();
                }
                testJob = new TestJob(result.getString("job_name"),
                        result.getString("os"),
                        result.getString("device"),
                        result.getString("platform"));
                testJob.setId(result.getLong("job_id"));
                testCaseData = new TestCaseData(testJob);

            }

            testRun = new HashMap();
            testRun.put("testrun_id", result.getString("id"));
            testRun.put("testrun_status", result.getString("status"));
            testRun.put("testrun_date", result.getDate("date").toString());
            listOfTestRuns.add(testRun);
            lastCatchedJobName = currentJobName;
        }

        connection.close();
        return testCaseDataList;
    }

    public List<TestRunScheduleData> getFormattedListForCalendar (List<HashMap<String,String>> listOfTestRuns, List<String> dates ) {
        HashMap<String,String> newMap = null;
        HashMap<String,String> existingMap = new HashMap();
        TestRunScheduleData testRunScheduleData;
        List<TestRunScheduleData> testRunScheduleDataList = new ArrayList<>();

        int currentTestRunListIndex = 0;
        existingMap = listOfTestRuns.get(currentTestRunListIndex);
        for (int i = 0; i < dates.size(); i++){
            if (dates.get(i).equalsIgnoreCase(existingMap.get("testrun_date"))){
                testRunScheduleData = new TestRunScheduleData();
                testRunScheduleData.hasData = true;
                int count = 0;
                while (dates.get(i).equalsIgnoreCase(existingMap.get("testrun_date"))){
                    existingMap.put("number",""+count);
                    count++;
                    testRunScheduleData.testRunStatusList.add(0,existingMap);
                    if (++currentTestRunListIndex != listOfTestRuns.size()) {
                        existingMap = listOfTestRuns.get(currentTestRunListIndex);
                    } else {
                        break;
                    }
                }
                testRunScheduleDataList.add(testRunScheduleData);
            } else {
                testRunScheduleData = new TestRunScheduleData();
                testRunScheduleDataList.add(testRunScheduleData);
            }
        }
        return testRunScheduleDataList;
    }

   /* @Override
    public List<TestCaseData> getTestCaseData(int testCaseID, int groupID, List<String> dates) throws SQLException {
        System.out.println("READING TEST CASE DATA FROM DATABASE...");
        List<TestCaseData> testCaseDataList = new ArrayList<>();
        TestCaseData testCaseData = null;
        TestJob testJob = null;
        TestRun testRun = null;

        String SQL = "SELECT job.name as job_name, " +
                "job.group_id as group_id, " +
                "job.os as os, " +
                "job.device as device, " +
                "test.name as test_name, " +
                "test.classpath as test_classpath, " +
                "status.status as status, " +
                "build_number, " +
                "video_url, " +
                "image_url, " +
                "image_url, " +
                "date, " +
                "duration, " +
                "error_details, " +
                "error_stacktrace, " +
                "skipped_message " +
                "FROM TEST_RUN " +
                "INNER JOIN TEST_JOB job ON TEST_RUN.job_id = job.id " +
                "INNER JOIN TEST_CASE test ON TEST_RUN.testcase_id = test.id " +
                "INNER JOIN TEST_STATUS status ON TEST_RUN.status_id = status.id " +
                "WHERE testcase_id=" +testCaseID + " " +
                "AND group_id=" +groupID + " " +
                "AND date='" +dates.get(0) + "' " +
                "OR date='" +dates.get(1) + "' " +
                "OR date='" +dates.get(2) + "' " +
                "OR date='" +dates.get(3) + "' " +
                "OR date='" +dates.get(4) + "' " +
                "OR date='" +dates.get(5) + "' " +
                "OR date='" +dates.get(6) + "' " +
                "ORDER BY job_id, build_number DESC;";

        Connection connection = dbConnection.getConnection();
        Statement pstmt = connection.createStatement();
        ResultSet result = pstmt.executeQuery(SQL);

        int dateCounter = 0;
        String lastCatchedJobName = "";
        while (result.next()) {
            String currentJobName = result.getString("job_name");
            if (!currentJobName.contentEquals(lastCatchedJobName)) {
                if (testCaseData != null) {
                        testCaseDataList.add(testCaseData);
                }
                testJob = new TestJob(result.getString("job_name"),
                        result.getString("os"),
                        result.getString("device"));
                testCaseData = new TestCaseData(testJob);
            }

            TestRun testRun1 = new TestRun(result.getString("test_classpath"),
                    result.getInt("build_number"),
                    result.getString("test_name"),
                    result.getInt("duration"),
                    result.getString("status"),
                    result.getDate("date").toString(),
                    result.getString("os"));

            for (String date:dates) {
                if (date.equalsIgnoreCase(testRun1.getDate())){
                    testCaseData.addTestRun(testRun1);
                    break;
                } else {
                    testCaseData.addTestRun(new TestRun());
                }
            }
            lastCatchedJobName = currentJobName;
        }
        connection.close();
        return testCaseDataList;
    }*/


}
