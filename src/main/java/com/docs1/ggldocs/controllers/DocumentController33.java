package com.docs1.ggldocs.controllers;

import com.docs1.ggldocs.models.Document33;
import com.docs1.ggldocs.services.DocumentService33; // Keep this if you are still using DocumentService33
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/documents")
public class DocumentController33 {

    @Autowired
    private DocumentService33 documentService33; // This can be changed to a different service if needed

    // Endpoint to save a document
    @PostMapping("/save")
    public ResponseEntity<Document33> saveDocument(@RequestBody Document33 document) {
        Document33 savedDocument = documentService33.saveDocument(document);
        return ResponseEntity.ok(savedDocument);
    }

    // Endpoint to load a document by ID
    @GetMapping("/load/{id}")
    public ResponseEntity<Document33> loadDocument(@PathVariable Long id) {
        Optional<Document33> document = documentService33.getDocument(id);
        return document.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}