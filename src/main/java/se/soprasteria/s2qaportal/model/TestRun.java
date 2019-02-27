package se.soprasteria.s2qaportal.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import se.soprasteria.s2qaportal.model.enums.Platform;
import se.soprasteria.s2qaportal.model.enums.TestStatus;
import se.soprasteria.s2qaportal.model.enums.WorkingStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TestRun implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "testRunID")
    private Long testRunID;
    private String jobName;
    private int buildNumber;
    private String testName;
    private String videoURL;
    private String imageURL;
    private String errorDetails;
    private String errorStacktrace;
    private String skippedMessage;
    private int duration;
    TestBuild build;
    String status;
    WorkingStatus workingStatus;
    Platform platform;

    public TestRun(String jobName, int buildNumber, String testName, int duration, String status) {
        this.jobName = jobName;
        this.buildNumber = buildNumber;
        this.testName = testName;
        this.duration = duration;
        this.status = status;
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
}
