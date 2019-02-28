package se.soprasteria.s2qaportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import se.soprasteria.s2qaportal.model.TestCase;
import se.soprasteria.s2qaportal.model.TestRunResult;
import se.soprasteria.s2qaportal.repository.ITestRepository;
import se.soprasteria.s2qaportal.repository.TestCaseRepository;
import se.soprasteria.s2qaportal.services.JenkinsDataCollector;
import se.soprasteria.s2qaportal.services.mocked.MockedDataCollector;
import se.soprasteria.s2qaportal.services.mocked.MockedJenkinsDataCollector;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

//import se.soprasteria.s2qaportal.services.JenkinsDataCollector;
//import se.soprasteria.s2qaportal.services.JenkinsDataCollector;

@Controller
public class ViewController {

    @Autowired
    private ITestRepository testRepository;
    public static AtomicInteger ID_GENERATOR = new AtomicInteger(1000);
    private MockedDataCollector mockedDataCollector;
    private TestCaseRepository testCaseRepository;
    private MockedJenkinsDataCollector mockedJenkinsDataCollector;
    private JenkinsDataCollector jenkinsDataCollector;


    public ViewController(ITestRepository testRepository) {
        this.testRepository = testRepository;
        this.mockedDataCollector = new MockedDataCollector();
        this.jenkinsDataCollector = new JenkinsDataCollector();
        this.testCaseRepository = new TestCaseRepository();
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping({"/index", "/"})
    public String getMessage(Model model) {
        model.addAttribute("testCases", getAllDatabaseDataSortedIntoArrayOfTestCases());
        return "index";
    }

    @RequestMapping({"/test"})
    public String getTestName(Model model) throws SQLException {
        List<TestRunResult> testList = testCaseRepository.getAllTestName("tvCatchup", "android_galaxys7_grid_morning_stable");
        System.out.println(testList);
        //model.addAttribute("testCases", testList);
        return "index";
    }

    public List<TestCase> getAllDatabaseDataSortedIntoArrayOfTestCases() {
        return mockedDataCollector.getTestCases(testRepository.findAll());
    }
}