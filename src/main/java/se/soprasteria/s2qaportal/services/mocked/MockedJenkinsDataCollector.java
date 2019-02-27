package se.soprasteria.s2qaportal.services.mocked;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import se.soprasteria.s2qaportal.model.TestBuild;
import se.soprasteria.s2qaportal.model.TestCase;
import se.soprasteria.s2qaportal.model.TestJob;
import se.soprasteria.s2qaportal.model.TestRun;
import se.soprasteria.s2qaportal.services.Response;

import java.io.IOException;
import java.util.*;

public class MockedJenkinsDataCollector {

    private static String urlForJobsUnstable = "https://jenkins.tvoip.telia.com/view/OTT%20Test%20(Unstable)/api/json";
    private static String urlForJobsStable = "https://jenkins.tvoip.telia.com/view/OTT%20Test%20(Stable)/api/json";
    private static String apiSuffix = "api/json";
    private static String testReportSubstring = "/se.kentor.automatedtesting$telia-playplus/testReport/";

    List<TestJob> testJobs;
    int count = 0;
    public List<TestJob> getTestData() {
        return getJobs();
    }

    public List<TestJob> getJobs() {
        testJobs = new ArrayList<>();
        JsonArray jobsArray = null;

        try {
            Response response = MockedWebRequest.GET("/Users/framework-dev/Git/S2-QA-Portal/src/main/resources/mockedJSON/jobs.json");
            if (response != null) {
                jobsArray = response.asJsonArray("jobs");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (JsonElement responseElement : jobsArray) {
            JsonObject responseObject = responseElement.getAsJsonObject();
            String jobName = responseObject.get("name").getAsString().toLowerCase();
            if (!jobName.contains("trigger") &&
                    (jobName.contains("stable") || jobName.contains("unstable"))) {
                TestJob testJob = new TestJob(jobName,
                        responseObject.get("url").getAsString());
                System.out.println(testJob.getName());
                testJobs.add(testJob);
                attachBuildAndTestData(testJob,responseObject.get("url").getAsString() + apiSuffix);
                count++;
                System.out.println(count);

            }
        }
        return testJobs;
    }

    public void attachBuildAndTestData(TestJob testJob, String URL) {


        //JsonArray buildsArray = WebRequest.GET(URL).asJsonArray("builds");
        JsonArray buildsArray = null;

        try {
            Response response = MockedWebRequest.GET("/Users/framework-dev/Git/S2-QA-Portal/src/main/resources/mockedJSON/win_chrome.json");
            if (response != null) {
                buildsArray = response.asJsonArray("builds");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (JsonElement responseElement : buildsArray) {
            JsonObject responseObject = responseElement.getAsJsonObject();
            String buildURL = responseObject.get("url").getAsString();
            TestBuild testBuild = new TestBuild(responseObject.get("number").getAsInt(), buildURL);
            testJob.addBuild(testBuild);
            attachTestData(testBuild, buildURL);
        }
    }

    public HashMap saveArtifactsInArray(TestBuild testBuild, String buildURL){

        JsonArray artifactsArray = null;
        try {
            Response response = MockedWebRequest.GET("/Users/framework-dev/Git/S2-QA-Portal/src/main/resources/mockedJSON/win_chrome_builddetails.json");
            if (response != null) {
                artifactsArray = response.asJsonArray("artifacts");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        HashMap artifactRelativePaths = new HashMap();
        for (JsonElement responseElement: artifactsArray) {
            artifactRelativePaths.put("artifactURL", buildURL + responseElement.getAsJsonObject().get("relativePath"));
        }
        return artifactRelativePaths;
    }

    public void attachTestData(TestBuild testBuild, String URL){
        HashMap artifacts = saveArtifactsInArray(testBuild, URL + apiSuffix);

        Response response = null;
        try {
            response = MockedWebRequest.GET("/Users/framework-dev/Git/S2-QA-Portal/src/main/resources/mockedJSON/win_chrome_tests.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response != null) {
            JsonObject responseObject = response.asJsonObject();

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
                    TestCase testCase = new TestCase(
                            testDetailsObject.get("className").getAsString(),
                            testDetailsObject.get("name").getAsString(),
                            testDetailsObject.get("duration").getAsInt(),
                            testStatus);

                    //ADDING LOG MESSAGES
                    if (testStatus.equalsIgnoreCase("failed")) {
                        JsonElement errorDetailsElement = testDetailsObject.get("errorDetails");
                        if (!errorDetailsElement.isJsonNull()) {
                            testCase.setErrorDetails(errorDetailsElement.toString());
                        }
                        JsonElement errorStackTraceElement = testDetailsObject.get("errorStackTrace");
                        if (!errorStackTraceElement.isJsonNull()) {
                            testCase.setErrorStackTrace(errorStackTraceElement.toString());
                        }

                    } else if (testStatus.equalsIgnoreCase("skipped")) {
                        JsonElement skippedMessageElement = testDetailsObject.get("skippedMessage");
                        if (!skippedMessageElement.isJsonNull()) {
                            testCase.setSkippedMessage(skippedMessageElement.toString());
                        }


                        //ADDING ARTIFACTS
                        HashMap testArtifacts = getTestArtifacts(artifacts, testDetailsObject);
                        if (testArtifacts.size() != 0) {
                            if (testArtifacts.containsKey("imageURL"))
                                testCase.setImageURL(testArtifacts.get("imageURL").toString());
                            if (testArtifacts.containsKey("videoURL"))
                                testCase.setVideoURL(testArtifacts.get("videoURL").toString());
                        }
                    }
                    testBuild.addTestCase(testCase);
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

    public List<TestRun> getTestRuns(List<TestJob> testJobs) {
        List<TestRun> testRuns = new ArrayList<>();
        for (TestJob testJob : testJobs) {
            for (TestBuild testBuild : testJob.getTestBuilds()) {
                for (TestCase testCase : testBuild.getTestCases()) {
                    testRuns.add(new TestRun(testJob.getName(),
                            testBuild.getNumber(),
                            testCase.getName(),
                            testCase.getDuration(),
                            testCase.getStatus()));
                }
            }
        }
        return testRuns;
    }

}
