package se.soprasteria.s2qaportal.model;

import se.soprasteria.s2qaportal.model.enums.Platform;
import se.soprasteria.s2qaportal.model.enums.WorkingStatus;

import java.io.Serializable;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;


public class TestRun implements Serializable {

    private Long testRunID;
    private String jobName;
    private String classPath;
    private int buildNumber;
    private String testName;
    private String videoURL;
    private String imageURL;
    private String errorDetails;
    private String errorStacktrace;
    private String skippedMessage;
    private int duration;
    private String device;
    private Long timestamp;
    private TestBuild build;
    private String status;
    private WorkingStatus workingStatus;
    private Platform platform;
    private String SQLValueString;
    private String date;
    private String OS;
    private int statusID;

    public TestRun(String jobName, String classPath, int buildNumber, String testName, int duration, String status) {

        this.jobName = jobName;
        this.classPath = classPath;
        this.buildNumber = buildNumber;
        this.testName = testName;
        this.duration = duration;
        this.status = status;
    }

    public TestRun(String classPath, int buildNumber, String testName, int duration, String status, String date, String OS) {
        this.OS = OS;
        this.classPath = classPath;
        this.buildNumber = buildNumber;
        this.testName = testName;
        this.duration = duration;
        this.status = status;
        this.date = date;
    }
    public TestRun() {
        this.status = " ";
    }

    /*public void setStatus(String status) {
        if (status.contains("PASSED")) {
            this.status = TestStatus.PASSED;
        } else if (status.contains("SKIPPED")) {
            this.status = TestStatus.SKIPPED;
        } else if (status.contains("FAILED")) {
            this.status = TestStatus.FAILED;
        }
    }*/

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    public String getOS() {
        return OS;
    }

    public String getDate() {
        return date;
    }

    public String getSQLValueString() throws SQLException {
        int statusID = getStatusID(getStatus());
        Date date = new Date(getTimestamp());

        SQLValueString = "((SELECT id FROM TEST_JOB WHERE name = '"+getJobName()+"'), " +
                "(SELECT id FROM TEST_CASE WHERE name = '"+getTestName()+"'), " +
                getBuildNumber() + ", '" +
                getVideoURL() + "', '" +
                getImageURL() + "', '" +
                date + "', " +
                getDuration() + ", $STRING$" +
                getErrorDetails() + "$STRING$, $STRING$" +
                getErrorStacktrace() + "$STRING$, " +
                statusID + ", $STRING$" +
                getSkippedMessage() + "$STRING$)";

        return SQLValueString;
    }

    public void setTestRunID(Long testRunID) {
        this.testRunID = testRunID;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public void setBuild(TestBuild build) {
        this.build = build;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSQLValueString(String SQLValueString) {
        this.SQLValueString = SQLValueString;
    }

    public void setOS(String OS) {
        this.OS = OS;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getClassPath() {
        return classPath;
    }

    public Long getTestRunID() {
        return testRunID;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public int getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(int buildNumber) {
        this.buildNumber = buildNumber;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public int getDuration() {
        return duration;
    }

    public String getStatus() {
        return status;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(String errorDetails) {
        this.errorDetails = errorDetails;
    }

    public String getErrorStacktrace() {
        return errorStacktrace;
    }

    public void setErrorStacktrace(String errorStacktrace) {
        this.errorStacktrace = errorStacktrace;
    }

    public String getSkippedMessage() {
        return skippedMessage;
    }

    public void setSkippedMessage(String skippedMessage) {
        this.skippedMessage = skippedMessage;
    }

    public WorkingStatus getWorkingStatus() {
        return workingStatus;
    }

    public void setWorkingStatus(WorkingStatus workingStatus) {
        this.workingStatus = workingStatus;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    @Override
    public int hashCode() {

        return Objects.hash(testRunID);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestRun testRun = (TestRun) o;
        return Objects.equals(testRunID, testRun.getTestRunID());
    }





    public int getStatusID(String status) throws SQLException {
        switch (status.toLowerCase()) {
            case "skipped":
                return 1;
            case "passed":
                return 2;
            case "failed":
                return 3;
            case "regression":
                return 4;
            case "fixed":
                return 5;
        }
        return 0;
    }
}
