package se.soprasteria.s2qaportal.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Response {

    String responseString;
    JsonArray responseArray;
    JsonArray responsObject;

    public Response(String responseString) {
        this.responseString = responseString;
    }

    public String getResponseString() {
        return responseString;
    }

    public JsonArray asJsonArray(String property) {
        return new JsonParser()
                .parse(responseString)
                .getAsJsonObject()
                .getAsJsonArray(property);
    }

    public JsonObject asJsonObject() {
        return new JsonParser()
                .parse(responseString)
                .getAsJsonObject();
    }

    public String toString(){
        return responseString;
    }

}
