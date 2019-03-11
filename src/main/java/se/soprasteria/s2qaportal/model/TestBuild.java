package se.soprasteria.s2qaportal.model;

import se.soprasteria.s2qaportal.model.TestRun;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class TestBuild implements Serializable {

    /*@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "buildID")*/
    private Long buildID;
    private int jobID;
    private int number;
    private String URL;
    private int nrOfTests;
    private int nrOfSkippedTests;
    private int nrOfFailedTests;
    List<TestRun> testRuns;

    public TestBuild(int number, String URL) {
        this.number = number;
        this.URL = URL;
        testRuns = new ArrayList<>();
    }

    public List<TestRun> getTestRuns() {
        return testRuns;
    }

    public void addTestCase (TestRun testRun) {
        this.testRuns.add(testRun);
    }

    public int getNumber() {
        return number;
    }

    public String getURL() {
        return URL;
    }


    public int getNrOfTests() {
        return nrOfTests;
    }

    public int getNrOfSkippedTests() {
        return nrOfSkippedTests;
    }

    public int getNrOfFailedTests() {
        return nrOfFailedTests;
    }

    public void setNrOfTests(int nrOfTests) {
        this.nrOfTests = nrOfTests;
    }

    public void setNrOfSkippedTests(int nrOfSkippedTests) {
        this.nrOfSkippedTests = nrOfSkippedTests;
    }

    public void setNrOfFailedTests(int nrOfFailedTests) {
        this.nrOfFailedTests = nrOfFailedTests;
    }
}
