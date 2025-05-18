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
    private static final String OPENAI_API_RESPONSE_URL = "https://api.openai.com/v1/chat/completions";
    private static final String OPENAI_API_TRANSCRIPTION_URL = "https://api.openai.com/v1/audio/transcriptions"; // The OpenAI API URL


    private final RestTemplate restTemplate;

    public OpenAIConnector() {
        this.restTemplate = new RestTemplate();
    }

    public String askFeedbackSentiment(String instructions, String input) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            // Create the request body as per OpenAI chat completion API
            String body = """
			{
				"model": "gpt-3.5-turbo",
				"messages": [
					{ "role": "system", "content": "%s" },
					{ "role": "user", "content": "%s" }
				]
			}
			""".formatted(instructions, input.replace("\"", "\\\""));

            HttpEntity<String> entity = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.exchange(
                    OPENAI_API_RESPONSE_URL,
                    HttpMethod.POST,
                    entity,
                    String.class
            );
            return response.getBody();
        } catch (Exception e) {
            System.err.println("OpenAI API error: " + e.getMessage());
            return null;
        }
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
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonResponse);
            return root
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText(null);
        } catch (Exception e) {
            System.err.println("Failed to parse sentiment: " + e.getMessage());
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
