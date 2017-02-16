package com.example.copus.quotemood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MoodActivity extends AppCompatActivity {

    TextView outputTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood);
    }

    @Override
    protected void onStart() {
        super.onStart();
        populateQuoteOfTheDay();
    }

    public void handleLogoutButton(View v) {
        Intent logoutIntent = new Intent(MoodActivity.this, LoginActivity.class);
        startActivity(logoutIntent);
    }

    public void populateQuoteOfTheDay() {
        final TextView quoteTextView = (TextView) findViewById(R.id.quoteTextView);

        String getURL = "http://quotes.rest/qod.json"; //The API service URL
        OkHttpClient client = new OkHttpClient();
        try {
            Request request = new Request.Builder().url(getURL).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println(e.getMessage());
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final JSONObject jsonResult;
                    final String result = response.body().string();
                    try {
                        jsonResult = new JSONObject(result);
                        final String qod = (String)(jsonResult.getJSONObject("contents")
                                .getJSONArray("quotes").getJSONObject(0).get("quote")) ;
                        final String author = (String)(jsonResult.getJSONObject("contents")
                                .getJSONArray("quotes").getJSONObject(0).get("author")) ;
                        Log.d("okHttp", jsonResult.toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                quoteTextView.setText(qod + " -- " + author);
                            }
                        });
                        populateEmotions(qod);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }//onResponse()
            });
        } catch (Exception ex) {
            outputTextView.setText(ex.getMessage());
        }
    }//populateQuoteOfTheDay()

    public void populateEmotions(String quote) {
      //
        final TextView emotionsTextView = (TextView) findViewById(R.id.emotionsTextView);

        String getURL = "https://watson-api-explorer.mybluemix.net/alchemy-api/calls/text/TextGetEmotion?"
                + "apikey=d0e7bf68cdda677938e6c186eaf2b755ef737cd8&text="
                + quote + "&outputMode=json&showSourceText=0";
        OkHttpClient client = new OkHttpClient();
        try {
            Request request = new Request.Builder().url(getURL).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println(e.getMessage());
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final JSONObject jsonResult;
                    final String result = response.body().string();
                    try {
                        jsonResult = new JSONObject(result);
                        Iterator<String> keys = jsonResult.getJSONObject("docEmotions").keys();
                        String emotions = "";
                        while (keys.hasNext()) {
                            String key = keys.next();
                            double val = jsonResult.getJSONObject("docEmotions").getDouble(key);
                            emotions = emotions + key + " : " + val + "\n";
                        }
                        final String emot = emotions;

                        Log.d("okHttp", jsonResult.toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                emotionsTextView.setText(emot);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }//onResponse()
            });
        } catch (Exception ex) {
            outputTextView.setText(ex.getMessage());
        }

    }//populateEmotions()
} //MoodActivity
