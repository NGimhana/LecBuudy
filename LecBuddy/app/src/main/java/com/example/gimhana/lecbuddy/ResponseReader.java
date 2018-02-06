package com.example.gimhana.lecbuddy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by gimhana on 11/12/17.
 */

public class ResponseReader {

    String finalText = "";
    JSONParser parser = new JSONParser();

    public ResponseReader(JSONObject input) throws JSONException {


        String success = (String) input.get("status");
        System.out.println(success);

        if (success.contains("Succeeded")) {

            JSONObject results = (JSONObject) input.get("recognitionResult");

            JSONArray lines = (JSONArray) results.get("lines");

            for (int i = 0; i < lines.length(); i++) {

                JSONObject obj = (JSONObject) lines.get(i);

                String temp = (String) obj.get("text");

                finalText = finalText + temp;
            }

        } else {
            finalText = "Converting not Succeeded";
        }
    }

    public String getFinalText() {
        return finalText;
    }


    public static JSONObject convertStringToJSON(String x) {

        String temp = x.replaceAll("\"", "\\\"");
        JSONParser parser = new JSONParser();
        JSONObject y = null;
        try {
            y = (JSONObject) parser.parse(temp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return y;
    }
}
