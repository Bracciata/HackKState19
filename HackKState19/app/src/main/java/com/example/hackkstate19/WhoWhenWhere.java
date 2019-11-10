package com.example.hackkstate19;

import android.os.AsyncTask;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONException;
import org.json.JSONObject;


class WhoWhenWhere extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... strings) {
        String url_Construction = "https://aylien-text.p.rapidapi.com/entities?text=";
        url_Construction +=  strings[0].replaceAll("\\s", "\\%20"); // text to appropriate URL format

        // Using Unirest GET request connect with Aylein text recognition API
        HttpResponse<String> response;
        try {
            response = Unirest.get((url_Construction))
                    .header("x-rapidapi-host", "aylien-text.p.rapidapi.com")
                    .header("x-rapidapi-key", "0534417232msh84f78ec581c2123p1e0312jsn15a2c5ceafd1")
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
            return "";
        }
        String output = "";
        //Converting HttpReponse to JSON Objects to get
        String summaryObject = response.getBody();
        JSONObject test = null;
        try {
            test = new JSONObject(summaryObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            Object whoObject =  test.get("person");
            String who = whoObject.toString(); // Will need to filter out extra quotations and commas
            who = who.replaceAll("\",\"", " ");
            output += String.format("Who: %s,\n", who);
        } catch (JSONException e) {
            output += "Who: UNKNOWN, \n";
        }

        try {
            Object whenObject =  test.get("date");
            String when = whenObject.toString(); // Will need to filter out extra quotations and commas
            when = when.replaceAll("\",\"", " ");
            output += String.format("When: %s,\n", when);
        } catch (JSONException e) {
            output += "When: UNKNOWN, \n";
        }

        try {
            Object whereObject =  test.get("location");
            String where = whereObject.toString(); // Will need to filter out extra quotations and commas
            where = where.replaceAll("\",\"", " ");
            output += String.format("Where: %s,\n", where);
        } catch (JSONException e) {
            output += "Where: UNKNOWN, \n";
        }


        return output;
    }

    @Override
    protected void onPostExecute(String result) {
        MainActivity.setSummary(result);
    }
}
