package com.docs1.ggldocs.services;

import com.docs1.ggldocs.models.Document33;
import com.docs1.ggldocs.repositories.DocumentRepository33;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class DocumentService33 {

    @Autowired
    private DocumentRepository33 documentRepository33;

    @Value("${openai.api.key}") // Read the OpenAI API key from application.properties
    private String openaiApiKey;

    private final String openaiUrl = "https://api.openai.com/v1/completions"; // OpenAI endpoint for completions

    private final RestTemplate restTemplate = new RestTemplate();

    // Save a document
    public Document33 saveDocument(Document33 document) {
        return documentRepository33.save(document);
    }

    // Get a document by ID
    public Optional<Document33> getDocument(Long id) {
        return documentRepository33.findById(id);
    }

    // Delete a document by ID
    public void deleteDocument(Long id) {
        documentRepository33.deleteById(id);
    }

    // Call OpenAI API with document content
    public String processDocumentWithOpenAI(Document33 document) {
        String documentText = document.getText(); // Assuming the document has a 'text' field

        // Construct the request body for OpenAI API
        String requestBody = "{ \"model\": \"gpt-3.5-turbo\", \"messages\": [{ \"role\": \"user\", \"content\": \"" + documentText + "\" }], \"max_tokens\": 150 }";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + openaiApiKey);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // Send the request to OpenAI API
        ResponseEntity<String> response = restTemplate.exchange(openaiUrl, HttpMethod.POST, entity, String.class);

        // Process the response (this will depend on the format OpenAI returns)
        if (response.getStatusCode() == HttpStatus.OK) {
            // Assuming the body contains the generated text from OpenAI
            String responseBody = response.getBody();
            // Extract the text from the response body (you can adjust parsing based on the actual response structure)
            return responseBody; // You may need to parse the response if it has nested fields
        } else {
            return "Error: " + response.getStatusCode(); // Handle errors accordingly
        }
    }
}
