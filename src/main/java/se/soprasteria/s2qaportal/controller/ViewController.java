package se.soprasteria.s2qaportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import se.soprasteria.s2qaportal.model.TestCase;
import se.soprasteria.s2qaportal.model.TestCaseData;
import se.soprasteria.s2qaportal.model.TestRun;
import se.soprasteria.s2qaportal.model.TestRunResult;
//import se.soprasteria.s2qaportal.repository.ITestRepository;
import se.soprasteria.s2qaportal.repository.TestCaseRepository;
//import se.soprasteria.s2qaportal.services.JenkinsDataCollector;
//import se.soprasteria.s2qaportal.services.mocked.MockedDataCollector;
import se.soprasteria.s2qaportal.services.mocked.MockedJenkinsDataCollector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

//import se.soprasteria.s2qaportal.services.JenkinsDataCollector;
//import se.soprasteria.s2qaportal.services.JenkinsDataCollector;

@Controller
public class ViewController {

    List<TestCaseData> testCaseData;
    List<String> dates;
    final int MOCKED_GROUP = 3;

    @Autowired
   // private ITestRepository testRepository;
    public static AtomicInteger ID_GENERATOR = new AtomicInteger(1000);
    //private MockedDataCollector mockedDataCollector;
    private TestCaseRepository testCaseRepository;
    private MockedJenkinsDataCollector mockedJenkinsDataCollector;
    //private JenkinsDataCollector jenkinsDataCollector;


    public ViewController() {
       // this.testRepository = testRepository;
        //this.mockedDataCollector = new MockedDataCollector();
        //this.jenkinsDataCollector = new JenkinsDataCollector();
        this.testCaseRepository = new TestCaseRepository();
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping({"/index", "/"})
    public String getMessage(Model model) throws SQLException {
        model.addAttribute("testCases", testCaseRepository.getTestCases());
        return "index";
    }

    @RequestMapping({"/test"})
    public String getTestName(Model model) throws SQLException {
        List<TestRunResult> testList = testCaseRepository.getAllTestName("tvCatchup", "android_galaxys7_grid_morning_stable");
        System.out.println(testList);
        model.addAttribute("testCases", testList);
        return "index";
    }

    @GetMapping("/getChosenTestCase/{id}")
    public ModelAndView getChosenTestCase(@PathVariable("id") int id, Model model, HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession(true);

        dates = testCaseRepository.getDates(MOCKED_GROUP);
        testCaseData = testCaseRepository.getTestCaseData(id,MOCKED_GROUP,dates);
        model.addAttribute("testCaseData", testCaseData);
        model.addAttribute("dates", getFormattedDates(dates));
        return new ModelAndView("forward:/index");
    }

    @GetMapping("/getChosenTestRun/{id}")
    public ModelAndView getChosenTestRun(@PathVariable("id") int id, Model model, HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession(true);

        TestRun testRun = testCaseRepository.getTestRun(id);
        model.addAttribute("testCaseData", testCaseData);
        model.addAttribute("dates", getFormattedDates(dates));
        model.addAttribute("testRun", testRun);
        return new ModelAndView("forward:/index");
    }

    @GetMapping("/getOlderRunDataPosts/{id, date, getOlderPosts}")
    public ModelAndView getOlderRunDataPosts(@PathVariable("id") int id, String date, boolean getOlderPosts, Model model, HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession(true);
       // List<TestCaseData> testCaseData = testCaseRepository.getTestCaseData(id);
        model.addAttribute("dates", testCaseRepository.getDates(MOCKED_GROUP, date,true));
       // model.addAttribute("testCaseData", testCaseData);
        return new ModelAndView("forward:/index");
    }

    /*public List<TestCase> getAllDatabaseDataSortedIntoArrayOfTestCases() {
        return mockedDataCollector.getTestCases(testRepository.findAll());
    }*/

    public List<String> getFormattedDates(List<String> dates) {
        List<String> formattedDates = new ArrayList<>();

        for (String date : dates) {
            String day = date.substring(date.lastIndexOf("-") + 1);
            if (day.startsWith("0")) day = day.substring(1);
            String month = date.substring(date.indexOf("-") + 1, date.lastIndexOf("-"));
            if (month.startsWith("0")) month = month.substring(1);
            formattedDates.add(day + "/" + month);
        }
        return formattedDates;
    }
}