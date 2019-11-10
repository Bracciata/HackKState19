package com.example.hackkstate19;


import android.os.AsyncTask;

import com.aylien.textapi.TextAPIClient;
import com.aylien.textapi.TextAPIException;
import com.aylien.textapi.parameters.SummarizeParams;
import com.aylien.textapi.responses.Summarize;


abstract class Summary extends AsyncTask<String, Void, Summarize> {

    protected static String summarizeAPISetUp(String title, String text, Integer percentage) {
        Summarize response = new Summarize();
        System.out.println(title);
        System.out.println(text);
        System.out.println(percentage);
        try {
            SummarizeParams.Builder builder = SummarizeParams.newBuilder();
            TextAPIClient client = new TextAPIClient("dabf4ee3", "cdf4c21cc5423ca1b0f7399489340159");
            builder.setTitle(title);
            builder.setPercentageOfSentences(percentage);
            builder.setText(text);
            response = client.summarize(builder.build());
        } catch (TextAPIException e) {
            e.printStackTrace();
        }

        String sentences = "";
        if (response != null && percentage > 0 && percentage <= 100) {
            for (String sentence : response.getSentences()) {
                sentences += sentence + ' ';
            }
        }

        return sentences;
    }



}