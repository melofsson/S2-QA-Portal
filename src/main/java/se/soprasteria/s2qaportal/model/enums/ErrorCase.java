package se.soprasteria.s2qaportal.model.enums;

import lombok.*;
import se.soprasteria.s2qaportal.model.old.FailedTest;

import java.util.ArrayList;
import java.util.List;

import static se.soprasteria.s2qaportal.controller.ViewController.ID_GENERATOR;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ErrorCase {

    private int id = ID_GENERATOR.getAndIncrement();
    String errorMessage;
    List<String> failingTests;
    List<String> failingPlatforms;
    String errorDescriptionURL;

    public ErrorCase(FailedTest test) {
        this.errorMessage = test.getErrorMessage();
        this.failingTests = new ArrayList<>();
        this.failingPlatforms = test.getPlatforms();
        this.errorDescriptionURL = test.getTestURL();
        addFailingTest(test.getTestName());
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public List<String> getFailingTests() {
        return failingTests;
    }

    public List<String> getFailingPlatforms() {
        return failingPlatforms;
    }

    public String getErrorDescriptionURL() {
        return errorDescriptionURL;
    }

    public void addFailingTest(String test){
        boolean nameIsAlreadyStored = false;
        for (String testName: failingTests){
            if (testName.equalsIgnoreCase(test)){
                nameIsAlreadyStored = true;
                break;
            }
        }
        if(!nameIsAlreadyStored)this.failingTests.add(test);
    }

    public void addFailingPlatform(List<String> platforms){
        for (String platform : platforms) {
            if (!this.failingPlatforms.contains(platform)) {
                failingPlatforms.add(platform);
            }
        }
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
