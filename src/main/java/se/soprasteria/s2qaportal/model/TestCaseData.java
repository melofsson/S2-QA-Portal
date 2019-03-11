package se.soprasteria.s2qaportal.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestCaseData {

    private TestJob testJob;
    private List<TestRunScheduleData> testRunScheduleDataList = new ArrayList<>();

    /*public TestCaseData(TestJob testJob, List<HashMap<String,String>> testRunScheduleDataList) {
        this.testJob = testJob;
        this.testRunScheduleDataList = testRunScheduleDataList;
    }*/

    public TestCaseData(TestJob testJob) {
        this.testJob = testJob;
    }

    public TestCaseData() {
    }


    public void setTestRunScheduleDataList(List<TestRunScheduleData> testRunScheduleDataList) {
        this.testRunScheduleDataList = testRunScheduleDataList;
    }

    public TestJob getTestJob() {
        return testJob;
    }

    public void setTestJob(TestJob testJob) {
        this.testJob = testJob;
    }

    public List<TestRunScheduleData> getTestRunScheduleDataList() {
        return testRunScheduleDataList;
    }

    public void addTestRuns(List<TestRun> testRuns){
        for (TestRun testRun : testRuns) {
            testRuns.add(testRun);
        }
    }

}


