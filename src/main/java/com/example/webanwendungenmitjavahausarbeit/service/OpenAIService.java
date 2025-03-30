package com.example.webanwendungenmitjavahausarbeit.service;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class OpenAIService {
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = ""; // Replace with your actual API key

    public static String getRecommendation(String prompt) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(API_URL);
            request.setHeader("Authorization", "Bearer " + API_KEY);
            request.setHeader("Content-Type", "application/json");

            JSONObject requestBody = new JSONObject();
            requestBody.put("model", "gpt-4");
            requestBody.put("messages", new org.json.JSONArray()
                    .put(new JSONObject().put("role", "system").put("content", "You are an event planning assistant."))
                    .put(new JSONObject().put("role", "user").put("content", prompt))
            );
            requestBody.put("temperature", 0.7);

            StringEntity entity = new StringEntity(requestBody.toString());
            request.setEntity(entity);

            try (CloseableHttpResponse response = httpClient.execute(request);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {

                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                JSONObject jsonResponse = new JSONObject(result.toString());
                System.out.println("OpenAI Response: " + jsonResponse.toString());

                if (jsonResponse.has("choices") && jsonResponse.getJSONArray("choices").length() > 0) {
                    return jsonResponse.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
                } else {
                    return "{\"error\": \"Invalid response from OpenAI\"}";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\": \"Error fetching recommendation.\"}";
        }
    }
}