package se.soprasteria.s2qaportal.controller;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import se.soprasteria.s2qaportal.model.FailedTest;
import se.soprasteria.s2qaportal.model.enums.ErrorCase;
import se.soprasteria.s2qaportal.repository.TestRepository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class ViewController {

    public static AtomicInteger ID_GENERATOR = new AtomicInteger(1000);

    private TestRepository testRepository;

    public ViewController (TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping({ "/index", "/" })
    public String getMessage(Model model) {
        if (readFailedTestsFromJSON()!= null){
            model.addAttribute("failedTests", readFailedTestsFromJSON());
            model.addAttribute("errorCases", getTestErrors(readFailedTestsFromJSON()));
        }
        model.addAttribute("tests", testRepository.findAll());
        return "index";
    }


    public FailedTest[] readFailedTestsFromJSON(){
        Gson gson = new Gson();
        try (Reader reader = new FileReader("/Users/framework-dev/Git/S2-QA-Portal/src/main/resources/failedtests.json")) {
            return gson.fromJson(reader, FailedTest[].class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected List<ErrorCase> getTestErrors(FailedTest[] failedTests) {
        List<ErrorCase> errorCases = new ArrayList<>();
        if (failedTests.length != 0) {
            for (FailedTest failedTest : failedTests) {
                boolean addToArray = true;
                for (ErrorCase errorCase : errorCases) {
                    if (isSimilarErrorMessage(errorCase.getErrorMessage(), failedTest.getErrorMessage())) {
                        errorCase.addFailingTest(failedTest.getTestName());
                        errorCase.addFailingPlatform(failedTest.getPlatforms());
                        addToArray = false;
                        break;
                    }
                }
                if (addToArray) errorCases.add(new ErrorCase(failedTest));
            }
        }
        return errorCases;
    }

    protected boolean isSimilarErrorMessage(String errorMessage1, String errorMessage2) {
        List<String> differences = new ArrayList<>();
        List<String> errorMessageArray1 = splitStringIntoLines(errorMessage1);
        List<String> errorMessageArray2 = splitStringIntoLines(errorMessage2);
        if (errorMessageArray1.size() > errorMessageArray2.size()) {
            differences = errorMessageArray1;
            differences.removeAll(errorMessageArray2);
        } else {
            differences = errorMessageArray2;
            differences.removeAll(errorMessageArray1);
        }



        if (errorMessageArray1.size() == 1 || errorMessageArray2.size() == 1) {
            return differences.size() == 0;
        } else if (errorMessageArray1.size() == 2 || errorMessageArray2.size() == 2) {
            return (differences.size() <= 1);
        } else {
            return (differences.size() <= 3);
        }
    }

    protected List<String> splitStringIntoLines(String errorMessage1) {
        List<String> errorMessageArray = new ArrayList<>();
        String lineToAdd;
        while (true) {
            if (errorMessage1.length() >= 75) {
                int indexOfWhiteSpace;
                if (errorMessage1.lastIndexOf(" ") < 75) {
                    indexOfWhiteSpace = errorMessage1.lastIndexOf(" ");
                } else {
                    indexOfWhiteSpace = errorMessage1.indexOf(" ", 75);
                }
                lineToAdd = errorMessage1.substring(0, indexOfWhiteSpace);
                errorMessage1 = errorMessage1.substring(lineToAdd.length());
            } else {
                lineToAdd = errorMessage1;
                errorMessageArray.add(lineToAdd);
                break;
            }
            errorMessageArray.add(lineToAdd);
        }
        return errorMessageArray;
    }
}