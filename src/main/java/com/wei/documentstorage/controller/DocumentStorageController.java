package com.wei.documentstorage.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wei.documentstorage.model.Document;
import com.wei.documentstorage.model.DocumentMetadata;
import com.wei.documentstorage.exception.DocumentNotFoundException;
import com.wei.documentstorage.service.DocumentStorageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/storage/documents")
@Api(value="Document Storage")
public class DocumentStorageController {
	@Autowired
	private DocumentStorageService storageService;
	
	@ApiOperation(value="Create a document")
	@PostMapping
	public ResponseEntity<String> create(
			@RequestParam MultipartFile document) {
		String docId = storageService.store(document);
		return new ResponseEntity<>(docId, HttpStatus.CREATED);
	}
	
	@ApiOperation(value="Query a document")
	@GetMapping("/{docId}")
	public ResponseEntity<Resource> get(@PathVariable String docId) 
			throws DocumentNotFoundException {

		Document document = storageService.load(docId);
		
		Resource resource = document.getResource();
		
		DocumentMetadata metadata  = document.getMetadata();

		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(metadata.getContentType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + metadata.getName() + "\"")
				.body(resource);		
	}
	
	@ApiOperation(value="Update a document")
	@PutMapping("/{docId}")
	public ResponseEntity<?> update(
			@PathVariable String docId, 
			@RequestParam MultipartFile document)
			throws DocumentNotFoundException {
		storageService.update(docId, document);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value="Delete a  document")
	@DeleteMapping("/{docId}")
	public ResponseEntity<?> delete(@PathVariable String docId)
			throws DocumentNotFoundException {
		storageService.delete(docId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}								
}	

