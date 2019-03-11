package se.soprasteria.s2qaportal.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import se.soprasteria.s2qaportal.model.TestBuild;
import se.soprasteria.s2qaportal.model.TestCase;
import se.soprasteria.s2qaportal.model.TestJob;
import se.soprasteria.s2qaportal.model.TestRun;
import se.soprasteria.s2qaportal.repository.TestCaseRepository;
import se.soprasteria.s2qaportal.services.mocked.MockedWebRequest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class RealJenkinsDataCollector {

    private static String urlForJobsUnstable = "https://jenkins.tvoip.telia.com/view/OTT%20Test%20(Unstable)/api/json";
    private static String urlForJobsStable = "https://jenkins.tvoip.telia.com/view/OTT%20Test%20(Stable)/api/json";
    private static String apiSuffix = "api/json";
    private static String testReportSubstring = "/se.kentor.automatedtesting$telia-playplus/testReport/";

    private List<TestJob> testJobs;
    private int count = 0;
    private List<TestCase> testCases;
    private List<TestRun> testRuns;
    private TestCaseRepository testCaseRepository = new TestCaseRepository();



    public List<TestJob> getTestData() {
        return getJobs();
    }


    public List<TestJob> getJobs() {
        testJobs = new ArrayList<>();
        testCases = new ArrayList<>();
        testRuns = new ArrayList<>();
        JsonArray jobsArray = null;


            Response response = WebRequest.GET(urlForJobsStable);
            if (response != null) {
                jobsArray = response.asJsonArray("jobs");
            }


        for (JsonElement responseElement : jobsArray) {
            JsonObject responseObject = responseElement.getAsJsonObject();
            String jobName = responseObject.get("name").getAsString().toLowerCase();
            if (!jobName.contains("trigger") &&
                    (jobName.contains("stable") || jobName.contains("unstable"))) {

                /**************
                SAVING TESTJOBS
                 ***************/
                TestJob testJob = createNewTestJob(jobName, responseObject.get("url").getAsString());
                System.out.println(testJob.getName());
                testJobs.add(testJob);

                attachBuildAndTestData(testJob,responseObject.get("url").getAsString() + apiSuffix);
                count++;
                System.out.println(count);

            }
        }
        return testJobs;
    }


    /*****************************
     CREATING NEW TEST JOBS OBJECT
     ******************************/
    public TestJob createNewTestJob(String jobName, String jenkins_url){
       String platform = getTestJobPlatform(jobName);
       String OS = getTestJobOS(jobName);
       String device = null;
       if (platform.equalsIgnoreCase("Android") || platform.equalsIgnoreCase("iOS")) {
           device = getTestJobDevice(jobName);
       }
       if (device!=null) {
           return new TestJob(jobName, jenkins_url, OS, device, platform);
       } else {
           return new TestJob(jobName, jenkins_url, OS, platform);
       }
    }

    public String getTestJobOS(String jobName){
        String os = jobName.toLowerCase();

        if (os.contains("android")){
            return "Android";
        } else if (os.contains("win")){
            return "Windows";
        } else if (os.contains("ios")){
            return "iOS";
        } else if (os.contains("mac")){
            return "MacOS";
        }
        return null;
    }

    public String getTestJobPlatform(String jobName){

       String name = jobName.toLowerCase();

       if (name.contains("chrome")){
           return "Chrome";
       } else if (name.contains("safari")){
           return "Safari";
       } else if (name.contains("internetexplorer")){
           return "Internet explorer";
        } else if (name.contains("edge")){
           return "Edge";
       } else if (name.contains("firefox")){
           return "Firefox";
       } else if (name.contains("opera")){
           return "Opera";
       } else if (name.contains("android")){
           return "Android";
        } else if (name.contains("ios")){
           return "iOS";
       }
       return null;
    }

    public String getTestJobDevice(String platform){

        int indexOfFirstUnderscore = platform.indexOf("_");
        int indexOfSecondUnderscore = platform.indexOf("_", indexOfFirstUnderscore + 1);
        return platform.substring(indexOfFirstUnderscore + 1, indexOfSecondUnderscore);

    }

    public void attachBuildAndTestData(TestJob testJob, String URL) {


        //JsonArray buildsArray = WebRequest.GET(URL).asJsonArray("builds");
        JsonArray buildsArray = null;

            Response response = WebRequest.GET(URL);
            if (response != null) {
                buildsArray = response.asJsonArray("builds");
            }

        for (JsonElement responseElement : buildsArray) {
            JsonObject responseObject = responseElement.getAsJsonObject();
            String buildURL = responseObject.get("url").getAsString();

            TestBuild testBuild = new TestBuild(responseObject.get("number").getAsInt(), buildURL);
            //testJob.addBuild(testBuild);
            attachTestData(testJob, testBuild, buildURL);
        }
    }

    public HashMap getArtifactRelativePaths(JsonArray artifactsArray, String buildURL){



        HashMap artifactRelativePaths = new HashMap();
        for (JsonElement responseElement: artifactsArray) {
            artifactRelativePaths.put("artifactURL", buildURL + responseElement.getAsJsonObject().get("relativePath"));
        }
        return artifactRelativePaths;
    }

    public void attachTestData(TestJob testJob, TestBuild testBuild, String URL){

        Long timestamp = null;
        JsonArray artifactsArray = null;
            Response response = WebRequest.GET(URL + apiSuffix);
            if (response != null) {
                artifactsArray = response.asJsonArray("artifacts");
                timestamp = response.asJsonObject().get("timestamp").getAsLong();
            }
        HashMap artifacts = getArtifactRelativePaths(artifactsArray, URL + apiSuffix);

            Response secondResponse = null;
            secondResponse = WebRequest.GET(URL + testReportSubstring + apiSuffix);
        if (secondResponse != null) {
            JsonObject responseObject = secondResponse.asJsonObject();

            //  testBuild.setNrOfTests(responseObject.get("totalCount").getAsInt());
            testBuild.setNrOfFailedTests(responseObject.get("failCount").getAsInt());
            testBuild.setNrOfSkippedTests(responseObject.get("skipCount").getAsInt());

            JsonArray suitesArray = responseObject.getAsJsonArray("suites");
            JsonArray testsArray = new JsonArray();

            if (suitesArray.size() > 0) {
                testsArray = suitesArray.get(0).getAsJsonObject().getAsJsonArray("cases");
            }

            if (testsArray.size() > 0) {
                for (JsonElement responseElement : testsArray) {
                    //ADDING TEST DETAILS
                    JsonObject testDetailsObject = responseElement.getAsJsonObject();
                    String testStatus = testDetailsObject.get("status").getAsString();
                    TestRun testRun = new TestRun(testJob.getName(),
                            testDetailsObject.get("className").getAsString(),
                            testBuild.getNumber(),
                            testDetailsObject.get("name").getAsString(),
                            testDetailsObject.get("duration").getAsInt(),
                            testStatus);

                    //ADDING LOG MESSAGES
                    if (testStatus.equalsIgnoreCase("failed")) {
                        JsonElement errorDetailsElement = testDetailsObject.get("errorDetails");
                        if (!errorDetailsElement.isJsonNull()) {
                            testRun.setErrorDetails(errorDetailsElement.toString());
                        }
                        JsonElement errorStackTraceElement = testDetailsObject.get("errorStackTrace");
                        if (!errorStackTraceElement.isJsonNull()) {
                            testRun.setErrorStacktrace(errorStackTraceElement.toString());
                        }

                    } else if (testStatus.equalsIgnoreCase("skipped")) {
                        JsonElement skippedMessageElement = testDetailsObject.get("skippedMessage");
                        if (!skippedMessageElement.isJsonNull()) {
                            testRun.setSkippedMessage(skippedMessageElement.toString());
                        }


                        //ADDING ARTIFACTS
                        HashMap testArtifacts = getTestArtifacts(artifacts, testDetailsObject);
                        if (testArtifacts.size() != 0) {
                            if (testArtifacts.containsKey("imageURL"))
                                testRun.setImageURL(testArtifacts.get("imageURL").toString());
                            if (testArtifacts.containsKey("videoURL"))
                                testRun.setVideoURL(testArtifacts.get("videoURL").toString());
                        }
                    }
                    testRun.setTimestamp(timestamp);
                    testRuns.add(testRun);
                    testCases.add(new TestCase(testRun.getTestName(), testRun.getClassPath()));
                    //testJob.addTestRun(testRun);
                }
            }
        }

    }


    public HashMap getTestArtifacts(HashMap artifacts, JsonObject jsonObject){
        HashMap testArtifacts = new HashMap();
        Iterator iterator = artifacts.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry)iterator.next();
            String entryValue = entry.getValue().toString();
            if (entryValue.contains(jsonObject.get("name").toString())){
                if (entryValue.contains("jpg")) {
                    testArtifacts.put("imageURL", entryValue);
                } else if (entryValue.contains("avi") || entryValue.contains("mp4")) {
                    testArtifacts.put("videoURL", entryValue);
                }
            }
            iterator.remove();
        }
        return testArtifacts;
    }

    public List<TestRun> getTestRuns() throws SQLException {
        return this.testRuns;
    }

    public List<TestCase> getUniqueTestCases() {
        List<TestCase> uniqueTestCases = new ArrayList<>();
           for (TestCase testCase : this.testCases) {
               boolean foundMatch = false;
               for (TestCase test : uniqueTestCases) {
                   if (testCase.getName().equalsIgnoreCase(test.getName())){
                       foundMatch = true;
                       break;
                   }
               }
               if (!foundMatch) uniqueTestCases.add(new TestCase(testCase.getName(), testCase.getClassPath()));
           }

        return uniqueTestCases;
    }



}
