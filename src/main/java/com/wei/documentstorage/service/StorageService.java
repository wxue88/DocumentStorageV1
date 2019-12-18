package com.wei.documentstorage.service;

import org.springframework.web.multipart.MultipartFile;
import com.wei.documentstorage.model.Document;
import com.wei.documentstorage.exception.DocumentNotFoundException;

public interface StorageService {
	
	/**
	 * Load a document based on documentId
	 * @param docId document ID
	 * @return a document model includes both metadata and resource
	 * @throws DocumentNotFoundException
	 */
	
	public Document load(String docId) throws DocumentNotFoundException;
	
	/**
	 * Store an uploaded document and save metadata
	 * @param document uploaded document
	 * @return document ID
	 */
	public String store(MultipartFile document);
	
	/**
	 * Update an existing document with document ID with a new document
	 * @param docId document ID of an existing document
	 * @param document new document
	 * @throws DocumentNotFoundException
	 */
	public void update(String docId, MultipartFile document) throws DocumentNotFoundException;
	
	/**
	 * Delete an existing document with document ID
	 * @param docId document ID of an existing document
	 * @throws DocumentNotFoundException
	 */
	public void delete(String docId) throws DocumentNotFoundException;
	
}
