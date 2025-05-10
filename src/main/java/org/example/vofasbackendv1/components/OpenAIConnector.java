package org.example.vofasbackendv1.components;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;

@Component
public class OpenAIConnector {

    @Value("${vofas.openai.key}")
    private String apiKey; // Retrieve the API key from application properties
    private static final String OPENAI_API_RESPONSE_URL = "https://api.openai.com/v1/responses";
    private static final String OPENAI_API_TRANSCRIPTION_URL = "https://api.openai.com/v1/audio/transcriptions"; // The OpenAI API URL


    private final RestTemplate restTemplate;

    public OpenAIConnector() {
        this.restTemplate = new RestTemplate();
    }

    public String askFeedbackSentiment(String instructions, String input) {
        // Prepare headers for the OpenAI API request
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        // Build the JSON body for the request, including the model parameter
        String jsonBody = String.format("{" +
                "\"model\": \"gpt-3.5-turbo\", " +
                "\"instructions\": \"%s\", " +  // Add comma here
                "\"input\": \"%s\" }", instructions, input);

        // Wrap the JSON body in HttpEntity
        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

        // Make the API call and get the response
        ResponseEntity<String> response = restTemplate.exchange(OPENAI_API_RESPONSE_URL, HttpMethod.POST, entity, String.class);

        // Return the response text
        return response.getBody();
    }

    public String transcribeVoiceFeedback(File audioFile) throws FileNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(audioFile));
        body.add("model", "gpt-4o-transcribe");
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                OPENAI_API_TRANSCRIPTION_URL,
                HttpMethod.POST,
                entity,
                String.class
        );
        return response.getBody();
    }

    public String extractSentiment(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            // Navigate through the JSON structure to get the text from the output
            JsonNode outputNode = rootNode.path("output").get(0); // Get the first element in the "output" array
            JsonNode contentNode = outputNode.path("content").get(0); // Get the first element in the "content" array
            // Get the text value

            return contentNode.path("text").asText();  // Return the sentiment (POSITIVE, NEURAL, or NEGATIVE)
        } catch (Exception e) {
            return null;
        }
    }

    public String extractTranscription(String jsonResponse) {
        try {
            // Using Jackson to parse the JSON response
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            return rootNode.path("text").asText();  // Extract the "text" field containing the transcription
        } catch (Exception e) {
            return null;  // If something goes wrong, return null
        }
    }
}
