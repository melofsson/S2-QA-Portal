package se.soprasteria.s2qaportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import se.soprasteria.s2qaportal.model.TestRun;
import se.soprasteria.s2qaportal.repository.TestRepository;
import se.soprasteria.s2qaportal.services.JenkinsDataCollector;
import se.soprasteria.s2qaportal.services.mocked.MockedJenkinsDataCollector;

import java.util.List;

@Component
public class BaseController implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private TestRepository testRepository;
    //Set to true if to use mockedData, false if to use data from Telia Jenkins (REQUIRES CONNECTION TO THEIR VPN)
    boolean useMockedData = true;

    private MockedJenkinsDataCollector mockedJenkinsDataCollector;
    private JenkinsDataCollector jenkinsDataCollector;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent){
        mockedJenkinsDataCollector = new MockedJenkinsDataCollector();
        jenkinsDataCollector = new JenkinsDataCollector();
        initData();
    }

    private void initData(){
        testRepository.deleteAll();
        List<TestRun> testRuns = getData();
        for (TestRun testRun : testRuns) {
            testRepository.save(testRun);
        }
    }

    private List<TestRun> getData(){
        if (useMockedData){
            return mockedJenkinsDataCollector.getTestRuns(mockedJenkinsDataCollector.getJobs());
        } else {
            return jenkinsDataCollector.getTestRuns(jenkinsDataCollector.getJobs());
        }
    }

}
