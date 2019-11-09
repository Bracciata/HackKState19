package com.example.hackkstate19;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

public class Summary{

    private static int countSentences(
            String someString, int index) {
        if (index >= someString.length()) {
            return 0;
        }

        int count = someString.charAt(index) == '.' ? 1 : 0;
        return count + countSentences(
                someString, index + 1);
    }

    protected HttpResponse<String> request(String url, String text, int percentage) {

        int sentenceCount =  countSentences(text, 0);
        String sentNum = String.valueOf(((percentage/100)*sentenceCount));

        text = text.replaceAll("\\b \\b", "\\%20"); // Converting the string to the the proper format

        HttpResponse<String> response;

        try {
            response = Unirest.post("https://textanalysis-text-summarization.p.rapidapi.com/text-summarizer")
                    .header("x-rapidapi-host", "textanalysis-text-summarization.p.rapidapi.com")
                    .header("x-rapidapi-key", "0534417232msh84f78ec581c2123p1e0312jsn15a2c5ceafd1")
                    .header("content-type", "application/json")
                    .header("accept", "application/json")
                    .body(String.format("{\"url\":%s,\"text\":%s,\"sentnum\":%s}", url, text, sentNum))
                    .asString();
        } catch (Exception e) {
            System.out.println("Uh Oh"); // Add in Exception Handler
            return null;
        }
        return response;
    }

}