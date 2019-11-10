package com.example.hackkstate19;

import android.os.AsyncTask;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONObject;


class Summary extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... strings) {
        String url_Construction = "https://aylien-text.p.rapidapi.com/summarize?title=What&text=";
        System.out.println(strings[0] + "DANNY");
        url_Construction +=  strings[0].replaceAll("\\s", "\\%20");
        url_Construction += "&sentences_percentage=20";
        HttpResponse<String> response;
        System.out.println(url_Construction);
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
        System.out.println("It Made it ");
        try {
            String summaryObject = response.getBody();
            JSONObject test = new JSONObject(summaryObject);
            System.out.println("11");
            Object sentenceObject =  test.get("sentences");
            System.out.println("2");
            summary = sentenceObject.toString().substring(2,sentenceObject.toString().length()-2);
            System.out.println(summary);
        } catch (Exception e){
            System.out.println("Ummmm");
        }

        return summary;
    }

    @Override
    protected void onPostExecute(String result) {
        MainActivity.setSummary(result);
    }
}
