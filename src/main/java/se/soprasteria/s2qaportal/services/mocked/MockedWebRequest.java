package se.soprasteria.s2qaportal.services.mocked;

import se.soprasteria.s2qaportal.services.Response;

import java.io.*;

public class MockedWebRequest {

    public static Response GET(String filePath) throws IOException {
        StringBuffer response = new StringBuffer();
        BufferedReader in = new BufferedReader(new FileReader(filePath));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return new Response(response.toString());
    }

}
