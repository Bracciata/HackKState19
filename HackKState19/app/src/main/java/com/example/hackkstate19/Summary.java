package com.example.hackkstate19;

import com.mashape.unirest.http.Unirest;
import org.json.JSONObject;
import java.util.logging.Logger;

public class Summary{

    public static String request(String url, String title, String text, Integer percentage, Integer sentencesNum) {
        System.out.println("text" + text+ " DAAAAANNNNNNNYY");
        String url_Construction = "https://aylien-text.p.rapidapi.com/summarize?";
        if(url != null && !url.isEmpty()) {
            url_Construction += "url=" + url + '&';
            System.out.println(url);
        }
        if (!title.isEmpty()){
            title = title.replaceAll("\\s", "\\%20"); // Setting string to html format
            url_Construction += "title=" + title + '&';
            System.out.println(title);
        }
        if (!text.isEmpty()) {
            text = text.replaceAll("\\s", "\\%20"); // Setting string to html forma
            url_Construction += "text=" + text + '&';
            System.out.println(text);
        }
        if (sentencesNum != -1) { // Applying -1 as a magic number if no sentence number is given
            url_Construction += "sentences_number=" + sentencesNum + '&';
            System.out.println(sentencesNum);
        }
        if (percentage >= 0 && percentage <= 100) {
            url_Construction += "sentences_percentage=" + percentage;
            System.out.println(percentage);
        }

        String response;
        System.out.println(url_Construction);
        try {
            response = Unirest.get(url_Construction)
                    .header("x-rapidapi-host", "aylien-text.p.rapidapi.com")
                    .header("x-rapidapi-key", "0534417232msh84f78ec581c2123p1e0312jsn15a2c5ceafd1")
                    .asString().getBody();
        } catch (Exception e) {
            return null;
        }
        String summary = "";
        try {
            JSONObject test = new JSONObject(response);
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

}