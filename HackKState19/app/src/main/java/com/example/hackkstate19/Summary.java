package com.example.hackkstate19;
import android.os.AsyncTask;
import com.aylien.textapi.TextAPIClient;
import com.aylien.textapi.TextAPIException;
import com.aylien.textapi.parameters.SummarizeParams;
import com.aylien.textapi.responses.Summarize;


class Summary extends AsyncTask<String, String, String> {



    @Override
    protected String doInBackground(String... strings) {
        int percentage = 20;
        String title = "TEXT";
        Summarize response = new Summarize();
        System.out.println("SADTOMMY"+strings[0]);

        try {
            SummarizeParams.Builder builder = SummarizeParams.newBuilder();
            TextAPIClient client = new TextAPIClient("dabf4ee3", "cdf4c21cc5423ca1b0f7399489340159");
            builder.setTitle(title);
            builder.setPercentageOfSentences(percentage);
            builder.setText(strings[0]);
            response = client.summarize(builder.build());

        } catch (TextAPIException e) {
            System.out.println("RIPDANNY"+e);
        }
        System.out.println("HATEMYLIFETOMMY");
        String sentences = "";
        if (response != null && percentage > 0 && percentage <= 100) {
            for (String sentence : response.getSentences()) {
                sentences += sentence + ' ';
            }
        }
        System.out.println("SADTOMMY43"+sentences);

        return sentences;
    }
    @Override
    protected void onPostExecute(String result) {
        MainActivity.setSummary(result);
    }
}
