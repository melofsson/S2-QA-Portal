package se.soprasteria.s2qaportal.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import se.soprasteria.s2qaportal.model.enums.JobStatus;
import se.soprasteria.s2qaportal.model.enums.TestGroup;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class TestJob implements Serializable {

    /*@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "jobID")*/
    //private Long jobID;
    private String name;
    private String url;
    List<TestBuild> testBuilds;
    List<TestRun> testRuns;

    public TestJob(String name, String url){
        this.name = name;
        this.url = url;
        testBuilds = new ArrayList<>();
        testRuns = new ArrayList<>();
    }

    public TestJob(String name){
        this.name = name;
        testRuns = new ArrayList<>();
    }

    public List<TestRun> getTestRuns() {
        return testRuns;
    }

    public void setTestRuns(List<TestRun> testRuns) {
        this.testRuns = testRuns;
    }

    public String getName() {
        return name;
    }

   /* public Long getJobID() {
        return jobID;
    }*/

    public List<TestBuild> getTestBuilds() {
        return testBuilds;
    }

    public void addBuild(TestBuild testBuild) {
        this.testBuilds.add(testBuild);
    }

    public String getUrl() {
        return url;
    }

    public boolean addTestRun(TestRun testRun) {
        if (testRuns.contains(testRun)){
            return false;
        }
        testRuns.add(testRun);
        return true;
    }

}
