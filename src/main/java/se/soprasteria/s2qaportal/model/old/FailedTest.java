package se.soprasteria.s2qaportal.model.old;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static se.soprasteria.s2qaportal.controller.ViewController.ID_GENERATOR;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FailedTest {

    private int id = ID_GENERATOR.getAndIncrement();
    private List<String> platforms;
    private String errorMessage;
    private String testName;
    private String testURL;
    private String shortName;

    public FailedTest(List<String> platforms, String errorMessage, String testName, String testURL, String shortName) {
        this.platforms = platforms;
        this.errorMessage = errorMessage;
        this.testName = testName;
        this.testURL = testURL;
        this.testURL = shortName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<String> platforms) {
        this.platforms = platforms;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestURL() {
        return testURL;
    }

    public void setTestURL(String testURL) {
        this.testURL = testURL;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
