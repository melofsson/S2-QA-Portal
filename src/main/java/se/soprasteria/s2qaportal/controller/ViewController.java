package se.soprasteria.s2qaportal.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import se.soprasteria.s2qaportal.model.*;
import se.soprasteria.s2qaportal.model.enums.ErrorCase;
import se.soprasteria.s2qaportal.repository.TestRepository;
//import se.soprasteria.s2qaportal.services.JenkinsDataCollector;
//import se.soprasteria.s2qaportal.services.JenkinsDataCollector;
import se.soprasteria.s2qaportal.services.JenkinsDataCollector;
import se.soprasteria.s2qaportal.services.mocked.MockedDataCollector;
import se.soprasteria.s2qaportal.services.mocked.MockedJenkinsDataCollector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    List<TestCase> testCases = new ArrayList<>();
    MockedDataCollector mockedDataCollector;

    @Autowired
    private TestRepository testRepository;
    private MockedJenkinsDataCollector mockedJenkinsDataCollector;
    private JenkinsDataCollector jenkinsDataCollector;


    public ViewController(TestRepository testRepository) {
        this.testRepository = testRepository;
        this.mockedDataCollector = new MockedDataCollector();
        this.jenkinsDataCollector = new JenkinsDataCollector();
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

    public List<TestCase> getAllDatabaseDataSortedIntoArrayOfTestCases(){
        return mockedDataCollector.getTestCases(testRepository.findAll());
    }
}