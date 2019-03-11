package se.soprasteria.s2qaportal.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class TestJob implements Serializable {

    private long id;
    private String name;
    private String jenkins_url;
    private String OS;
    private String device;
    private String platform;

    List<TestBuild> testBuilds = new ArrayList<>();
    List<TestRun> testRuns = new ArrayList<>();


    public TestJob(int ID, String name, String OS){
        this.id = ID;
        this.name = name;
        this.OS = jenkins_url;
    }

    public TestJob(String name, String jenkins_url, String OS, String device, String platform){
        this.name = name;
        this.jenkins_url = jenkins_url;
        this.OS = OS;
        this.device = device;
        this.platform = platform;
    }

    public TestJob(String name, String os, String device, String platform){
        this.platform = platform;
        this.name = name;
        this.OS = os;
        this.device = device;
        testRuns = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setJenkins_url(String jenkins_url) {
        this.jenkins_url = jenkins_url;
    }

    public String getOS() {
        return OS;
    }

    public void setOS(String OS) {
        this.OS = OS;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
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

    public String getJenkins_url() {
        return jenkins_url;
    }

    public void addTestRun(TestRun newTestRun) {
        boolean foundMatch = false;
        if (testRuns.size()==0) {
            testRuns.add(newTestRun);
        } else {
            for (TestRun testRun : testRuns) {
                if (testRun.getTestName().equalsIgnoreCase(newTestRun.getTestName())){
                    foundMatch = true;
                }
            }
            if (!foundMatch)testRuns.add(newTestRun);
        }

    }

    public String getSQLValueString(){
        return "('"+getName()+"', '"+getJenkins_url()+"', '" + getOS() + "', '" +
                getDevice() + "', '" + getPlatform() + "')";
    }

}
