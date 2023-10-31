package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SmmryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = request.getParameter("url");

        try {
            String apiKey = "BBC8883E57"; // Replace with your SMMRY API key
            int charCount = 1 * 5;
            String contentOption = "sm_api_content_reduced";
            String allContent = "sm_api_content";

            String apiUrl = "https://api.smmry.com/?SM_API_KEY=" + apiKey +
                    "&SM_URL=" + url +
                    "&SM_LENGTH=" + charCount +
                    "&SM_CONTENT=" + contentOption +
                    "&SM_CONTENT=" + allContent;

            JSONObject summaryJson = makeSmmryRequest(apiUrl);

            String title = (String) summaryJson.get("sm_api_title");
            StringBuilder formattedOutput = new StringBuilder();
            formattedOutput.append("Title: ").append("<br>");
            formattedOutput.append(title).append("<br><br>");

            String summaryContent = (String) summaryJson.get("sm_api_content");
            formattedOutput.append("Summary:").append("<br>");
            formattedOutput.append(summaryContent).append("<br><br>");

            String contentReduced = (String) summaryJson.get("sm_api_content_reduced");
            formattedOutput.append("Content Reduced :").append("<br>");
            formattedOutput.append(contentReduced).append("<br>");

            // Set the summary as a request attribute
            request.setAttribute("summary", formattedOutput.toString());

            // Forward the request to the result.jsp for displaying the summary
            request.getRequestDispatcher("/result.jsp").forward(request, response);
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
