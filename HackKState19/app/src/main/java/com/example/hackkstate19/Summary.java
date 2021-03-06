package com.example.hackkstate19;

import android.os.AsyncTask;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONException;
import org.json.JSONObject;


class Summary extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... strings) {
        String url_Construction = "https://aylien-text.p.rapidapi.com/summarize?title=What&text=";
        url_Construction +=  strings[0].replaceAll("\\s", "\\%20"); // text to appropriate URL format
        url_Construction += toString().format("&sentences_percentage=%d", MainActivity.percentage);
        System.out.println(MainActivity.percentage + "DANNY TRANNN");

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
        String summary = "";

        //Converting HttpReponse to JSON Objects to get
        try {
            String summaryObject = response.getBody();
            JSONObject test = new JSONObject(summaryObject);
            Object sentenceObject =  test.get("sentences");
            summary = sentenceObject.toString().substring(2,sentenceObject.toString().length()-2); // Will need to filter out extra quotations and commas
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return summary;
    }

    @Override
    protected void onPostExecute(String result) {
        MainActivity.setSummary(result);
    }
}
