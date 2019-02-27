package se.soprasteria.s2qaportal.model;

import lombok.*;
import se.soprasteria.s2qaportal.model.enums.TestStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static se.soprasteria.s2qaportal.controller.ViewController.ID_GENERATOR;

public class TestCase implements Serializable {

    /*@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "testID")*/
    private Long testID;
    private int id = ID_GENERATOR.getAndIncrement();
    String imageURL;
    String videoURL;
    private String errorDetails;
    private String errorStackTrace;
    private String skippedMessage;
    private String name;
    private String className;
    private boolean isSkipped;
    private int duration;
    String status;
    List<TestJob> testJobs;

    public TestCase(String className, String name, int duration, String status){
        this.className = className;
        this.name = name;
        this.duration = duration;
        this.status = status;
        testJobs = new ArrayList<>();
    }

    public TestCase(String className, String name){
        this.className = className;
        this.name = name;
        testJobs = new ArrayList<>();
    }

    public boolean addTestJobAndBuildData(TestRun testRun){
        for (TestJob testJob: testJobs) {
         if (testJob.getName().equalsIgnoreCase(testRun.getJobName())){
             testJob.addTestRun(testRun);
             return true;
         }
        }
        TestJob job = new TestJob(testRun.getJobName());
        job.addTestRun(testRun);
        testJobs.add(job);
        return false;
     }

    public void addTestJob(TestJob job){
        testJobs.add(job);
    }

    public List<TestJob> getTestJobs() {
        return testJobs;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public boolean isSkipped() {
        return isSkipped;
    }

    public int getDuration() {
        return duration;
    }

    public Long getTestID() {
        return testID;
    }

    public String getClassName() {
        return className;
    }

    public String getSkippedMessage() {
        return skippedMessage;
    }

    public void setSkippedMessage(String skippedMessage) {
        this.skippedMessage = skippedMessage;
    }

    public String getErrorDetails() {
            return errorDetails;
    }

    public String getErrorStackTrace() {
        return errorStackTrace;
    }

    public void setErrorDetails(String errorDetails) {
        this.errorDetails = errorDetails;
    }

    public void setErrorStackTrace(String errorStackTrace) {
        this.errorStackTrace = errorStackTrace;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getVideoURL() {
        return videoURL;
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

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {

        return Objects.hash(testID);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestCase testCase = (TestCase) o;
        return Objects.equals(testID, testCase.testID);
    }
}
