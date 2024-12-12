package com.docs1.ggldocs.controllers;

import com.docs1.ggldocs.services.OpenAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private OpenAiService openAiService;

    @PostMapping("/response")
    public ResponseEntity<String> getChatResponse(@RequestBody Map<String, String> request) {
        String prompt = request.get("prompt");

        // Check if prompt is not null or empty
        if (prompt == null || prompt.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Prompt cannot be empty.");
        }

        try {
            // Get the chat response from OpenAiService
            String response = openAiService.getChatResponse(prompt);
            return ResponseEntity.ok(response);  // Return the response from OpenAiService
        } catch (Exception e) {
            // Return a structured error message if an exception occurs
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while processing the request: " + e.getMessage());
        }
    }
}
