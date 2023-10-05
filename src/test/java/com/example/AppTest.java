package com.example;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AppTest {

    public static void main(String[] args) {
        try {
            String apiKey = "BBC8883E57";
            String urlToSummarize = "https://indianexpress.com/article/explained/explained-sci-tech/chandrayaan-3-landing-moon-explained-8904598/";

            int charCount = 1 * 5;
            String contentOption = "sm_api_content_reduced";
            String allContent = "sm_api_content";

            String apiUrl = "https://api.smmry.com/?SM_API_KEY=" + apiKey +
                    "&SM_URL=" + urlToSummarize +
                    "&SM_LENGTH=" + charCount +
                    "&SM_CONTENT=" + contentOption +
                    "&SM_CONTENT=" + allContent;

            JSONObject summaryJson = makeSmmryRequest(apiUrl);

            String title = (String) summaryJson.get("sm_api_title");
            StringBuilder formattedOutput = new StringBuilder();
            formattedOutput.append("Title: ").append(title).append("\n\n");

            String summaryContent = (String) summaryJson.get("sm_api_content");
            formattedOutput.append("Summary:\n").append(summaryContent).append("\n\n");

            String contentReduced = (String) summaryJson.get("sm_api_content_reduced");
            formattedOutput.append("Content Reduced:\n").append(contentReduced).append("\n");

            System.out.println(formattedOutput.toString());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private static JSONObject makeSmmryRequest(String apiUrl) throws IOException, ParseException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            String response = responseBuilder.toString();

            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(response);
        } finally {
            connection.disconnect();
        }
    }
}
