package se.soprasteria.s2qaportal.controller;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
//import se.soprasteria.s2qaportal.repository.ITestRepository;
import se.soprasteria.s2qaportal.model.TestJob;
import se.soprasteria.s2qaportal.repository.TestCaseRepository;
//import se.soprasteria.s2qaportal.services.JenkinsDataCollector;
import se.soprasteria.s2qaportal.services.RealJenkinsDataCollector;
import se.soprasteria.s2qaportal.services.mocked.MockedJenkinsDataCollector;

import java.sql.SQLException;
import java.util.List;

@Component
public class BaseController implements ApplicationListener<ContextRefreshedEvent> {

    //@Autowired
    //private ITestRepository testRepository;
    private TestCaseRepository testCaseRepository;
    //Set to true if to use mockedData, false if to use data from Telia Jenkins (REQUIRES CONNECTION TO THEIR VPN)
    boolean useMockedData = true;

    private MockedJenkinsDataCollector mockedJenkinsDataCollector;
    private RealJenkinsDataCollector jenkinsDataCollector;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent){
        mockedJenkinsDataCollector = new MockedJenkinsDataCollector();
        jenkinsDataCollector = new RealJenkinsDataCollector();
        this.testCaseRepository = new TestCaseRepository();
        try {
            initData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initData() throws SQLException {
        /*testRepository.deleteAll();
        List<TestRun> testRuns = getData();
        for (TestRun testRun : testRuns) {
            testRepository.save(testRun);
        }
*/
/*
        List<TestJob> testJobs = jenkinsDataCollector.getJobs();
        testCaseRepository.addTestJobs(testJobs);
        testCaseRepository.addTestCases(jenkinsDataCollector.getUniqueTestCases());
        testCaseRepository.addTestRuns(jenkinsDataCollector.getTestRuns());
*/





    }

    /*private List<TestRun> getData(){
        //if (useMockedData){
            //return mockedJenkinsDataCollector.getTestRuns(mockedJenkinsDataCollector.getJobs());
       // } else {
            //return jenkinsDataCollector.getTestRuns(jenkinsDataCollector.getJobs());
        //}
    }*/

}
