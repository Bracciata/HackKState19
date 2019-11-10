package com.example.hackkstate19;

import android.os.AsyncTask;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONException;
import org.json.JSONObject;


class Sentiment extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... strings) {
        String url_Construction = "https://aylien-text.p.rapidapi.com/sentiment?text=";
        url_Construction +=  strings[0].replaceAll("\\s", "\\%20"); // text to appropriate URL format

        // Using Unirest GET request connect with Aylein text recognition API
        HttpResponse<String> response;
        try {
            response = Unirest.get(url_Construction)
                    .header("x-rapidapi-host", "aylien-text.p.rapidapi.com")
                    .header("x-rapidapi-key", "0534417232msh84f78ec581c2123p1e0312jsn15a2c5ceafd1")
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
            return "";
        }

        String output = "";
        //Converting HttpReponse to JSON Objects to get
        try {
            String summaryObject = response.getBody();
            JSONObject test = new JSONObject(summaryObject);

            Object polarityObject =  test.get("polarity");
            String polarity = polarityObject.toString(); // Will need to filter out extra quotations and commas

            Object subjectivityObject =  test.get("subjectivity");
            String subjectivity = subjectivityObject.toString(); // Will need to filter out extra quotations and commas

            Object polarity_confidenceObject =  test.get("polarity_confidence");
            String polarity_confidence = polarity_confidenceObject.toString(); // Will need to filter out extra quotations and commas

            Object subjectivity_confidenceObject =  test.get("subjectivity_confidence");
            String subjectivity_confidence = subjectivity_confidenceObject.toString(); // Will need to filter out extra quotations and commas

            output = toString().format("Polarity : %s, \n" +
                    "Subjectivity: %s, \n" +
                    "Polarity Confidence: %s, \n" +
                    "Subjectivity Confidence: %s.", polarity, subjectivity,
                    polarity_confidence, subjectivity_confidence);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return output;
    }

    @Override
    protected void onPostExecute(String result) {
        MainActivity.setSummary(result);
    }
}
