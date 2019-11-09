package com.example.hackkstate19;

import org.junit.Test;
import static org.junit.Assert.*;

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
        assertEquals("You must provide two functions for extracting the next page.", response);
    }
}