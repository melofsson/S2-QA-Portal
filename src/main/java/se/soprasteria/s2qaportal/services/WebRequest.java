package se.soprasteria.s2qaportal.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebRequest{

    public static Response GET(String requestUrl) {
        HttpURLConnection urlConn;
        StringBuffer response = new StringBuffer();
        try {
            URL url = new URL(requestUrl);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("GET");

            int responseCode = urlConn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            } else {
                return null;
            }
            urlConn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Response(response.toString());
    }

    //priv static JsonArray asJsonArray(String URL, String property) {
      //  return new JsonParser().parse(GET(URL)).getAsJsonObject().getAsJsonArray(property);



   // }
}