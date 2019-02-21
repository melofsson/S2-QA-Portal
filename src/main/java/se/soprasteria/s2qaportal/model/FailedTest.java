package se.soprasteria.s2qaportal.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static se.soprasteria.s2qaportal.controller.ViewController.ID_GENERATOR;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
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

    public List<String> getPlatforms() {
        return platforms;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getTestName() {
        return testName;
    }

    public String getTestURL() {
        return testURL;
    }

    public String getShortName() {
        return shortName;
    }
}
