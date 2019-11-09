package com.example.hackkstate19;

import com.mashape.unirest.http.HttpResponse;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.junit.Test;
import com.mashape.unirest.request.HttpRequest;
import static org.junit.Assert.*;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        String fullText = "Sometimes services offer paged requests. How this is done is not standardized but Unirest proves a mechanism to follow pages until all have been consumed. You must provide two functions for extracting the next page.";
        String response = Summary.request("", "Okay", fullText, 20, -1);
        String summary="";
        try {
            JSONObject test = new JSONObject(response);
           Object summary2 =  test.get("sentences");

        } catch (Exception e){
            System.out.println("Ummmm");
        }

        assertEquals("You must provide two functions for extracting the next page.", summary);
    }
}