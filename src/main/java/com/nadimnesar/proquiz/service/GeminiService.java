package com.nadimnesar.proquiz.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nadimnesar.proquiz.model.Question;
import com.nadimnesar.proquiz.model.QuestionForm;
import com.nadimnesar.proquiz.model.RequestBody;
import com.nadimnesar.proquiz.model.ResponseBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GeminiService {

    @Value("${api.gemini.url}")
    private String apiUrl;

    @Value("${api.gemini.key}")
    private String apiKey;

    public QuestionForm createQuiz(String topic) {
        String fullApiUrl = String.format(apiUrl, apiKey);

        String prompt = String.format("""
                Generate 10 MCQ question on %s using following json format.
                {
                    "questionList":
                    [
                        {
                          "question": "String",
                          "optionA": "String",
                          "optionB": "String",
                          "optionC": "String",
                          "optionD": "String",
                          "answer": "a | b | c | d"
                        }
                    ]
                }
                Skip this type of question: What is the output/answer of the following code/image?
                And don't repeat same question.
                """, topic);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        RequestBody requestBody = new RequestBody();
        requestBody.setPrompt(prompt);

        HttpEntity<RequestBody> httpEntity = new HttpEntity<>(requestBody, headers);
        ObjectMapper objectMapper = new ObjectMapper();
        QuestionForm questionForm = new QuestionForm();
        ResponseBody responseBody = restTemplate.postForObject(fullApiUrl, httpEntity, ResponseBody.class);

        try {
            JsonNode rootNode = objectMapper.readTree(responseBody.getCandidates().get(0)
                    .getContent().getParts().get(0).getText());
            questionForm = objectMapper.treeToValue(rootNode, QuestionForm.class);
        } catch (Exception e) {
            e.getMessage();
        }

        return questionForm;
    }
}