package se.soprasteria.s2qaportal.model;

import java.io.Serializable;

public class TestRunResult implements Serializable {

    private int testRunId;
    private int buildNumber;
    private int duration;
    private String jobName;
    private String status;
    private String testName;

    public TestRunResult() {
    }

    public int getTestRunId() {
        return testRunId;
    }

    public void setTestRunId(int testRunId) {
        this.testRunId = testRunId;
    }

    public int getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(int buildNumber) {
        this.buildNumber = buildNumber;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    @Override
    public String toString() {
        return "TestRunResult{" +
                "testRunId=" + testRunId +
                ", buildNumber=" + buildNumber +
                ", duration=" + duration +
                ", jobName='" + jobName + '\'' +
                ", status='" + status + '\'' +
                ", testName='" + testName + '\'' +
                '}';
    }
}

